package nl.pancompany.hexagonal.architecture.main.configuration.plugins;

import lombok.extern.slf4j.Slf4j;
import nl.pancompany.hexagonal.architecture.common.annotation.architecture.PluggableRootComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static nl.pancompany.hexagonal.architecture.main.ApplicationConstants.ComponentScanConstants.ADAPTER_BASE_PACKAGE;
import static nl.pancompany.hexagonal.architecture.main.configuration.plugins.BeanUtil.*;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Slf4j
@Configuration
public class LayerBasedBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String[] PLUGIN_BASE_PACKAGES = { ADAPTER_BASE_PACKAGE };
    private final ClassPathScanningCandidateComponentProvider pluggableRootComponentScanner = new ClassPathScanningCandidateComponentProvider(false);
    private final ClassPathScanningCandidateComponentProvider springComponentScanner = new ClassPathScanningCandidateComponentProvider(true);

    private final Environment env;
    private final PluginConfiguration pluginConfiguration;
    private final TypeExcludeFilter typeExcludeFilter;

    @Bean
    public static LayerBasedBeanDefinitionRegistryPostProcessor layerBasedBeanDefinitionRegistryPostProcessor(final Environment env,
                                                                                                              @Autowired(required = false) final TypeExcludeFilter typeExcludeFilter) {
        return new LayerBasedBeanDefinitionRegistryPostProcessor(env, typeExcludeFilter);
    }

    public LayerBasedBeanDefinitionRegistryPostProcessor(final Environment env, final TypeExcludeFilter typeExcludeFilter) {
        this.env = env;
        this.pluginConfiguration = new PluginConfigurationLoader(env).load();
        this.typeExcludeFilter = typeExcludeFilter;
        configureComponentScanners();
    }

    private void configureComponentScanners() {
        pluggableRootComponentScanner.addIncludeFilter(new AnnotationTypeFilter(PluggableRootComponent.class));
        excludeTestComponents();
    }

    private void excludeTestComponents() {
        if (typeExcludeFilter != null) {
            pluggableRootComponentScanner.addExcludeFilter(typeExcludeFilter);
            springComponentScanner.addExcludeFilter(typeExcludeFilter);
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry registry) {
        new ApplicationLayerComponentsRegisterer().registerComponentsFrom(PLUGIN_BASE_PACKAGES, registry);
        postProcessConfigurationClasses(registry);
    }

    private class ApplicationLayerComponentsRegisterer {

        private final Set<String> scannedBasePackages = new HashSet<>();

        private void registerComponentsFrom(final String[] basePackages, final BeanDefinitionRegistry registry) {
            for (final var basePackage : basePackages) {
                pluggableRootComponentScanner
                        .findCandidateComponents(basePackage)
                        .forEach(beanDefinition -> processCandidateRootComponent(beanDefinition, registry));
            }
        }

        private void processCandidateRootComponent(final BeanDefinition beanDefinition, final BeanDefinitionRegistry registry) {
            final var beanClass = getBeanClass(beanDefinition);
            final var beanName = findPluggableRootComponentBeanName(beanClass);
            final var basePackage = beanClass.getPackageName();

            for (final var activeProfile : env.getActiveProfiles()) {
                if (pluginConfiguration.isBeanActivatedInPluginConfiguration(beanName, activeProfile)
                        && !basePackageHasBeenProcessedBefore(basePackage, scannedBasePackages)) {
                    registerBeansInLayer(basePackage, registry);
                    scannedBasePackages.add(basePackage);
                }

            }
        }
    }

    private void registerBeansInLayer(final String basePackage, final BeanDefinitionRegistry registry) {
        springComponentScanner.findCandidateComponents(basePackage)
                .forEach(beanDefinition -> processCandidateComponent(beanDefinition, registry));
    }

    private void processCandidateComponent(final BeanDefinition beanDefinition, final BeanDefinitionRegistry registry) {
        final var beanClassName = getBeanClassName(beanDefinition);
        final var beanClass = BeanUtil.getClass(beanClassName);

        if (isRegularSpringComponent(beanClass) || pluginConfiguration.isActivatedPluggableComponent(beanClass)) {
            final var componentBeanName = findMergedAnnotation(beanClass, Component.class).value();
            registry.registerBeanDefinition(componentBeanName.isBlank() ? beanClassName : componentBeanName, beanDefinition);
        }
    }

    public void postProcessConfigurationClasses(final BeanDefinitionRegistry registry) {
        final var configurationClassPostProcessor = new ConfigurationClassPostProcessor();
        configurationClassPostProcessor.setEnvironment(env);
        configurationClassPostProcessor.postProcessBeanDefinitionRegistry(registry);
    }

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
    }
}