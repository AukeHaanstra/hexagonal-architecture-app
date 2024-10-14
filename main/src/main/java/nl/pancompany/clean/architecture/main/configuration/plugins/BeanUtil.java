package nl.pancompany.clean.architecture.main.configuration.plugins;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.clean.architecture.common.annotation.architecture.PluggableComponent;
import nl.pancompany.clean.architecture.common.annotation.architecture.PluggableRootComponent;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.Optional;
import java.util.Set;

import static java.lang.Class.forName;
import static java.lang.String.format;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.core.annotation.AnnotationUtils.getValue;

@Slf4j
public class BeanUtil {

    private BeanUtil() {
    }

    public static Class<?> getBeanClass(final BeanDefinition beanDefinition) {
        final var beanClassName = getBeanClassName(beanDefinition);
        return getClass(beanClassName);
    }

    public static Class<?> getClass(final String beanClassName) {
        try {
            return forName(beanClassName);
        } catch (ClassNotFoundException e) {
            log.error("Could not find class.", e);
            throw new BeanConfigurationException(e);
        }
    }

    public static String getBeanClassName(final BeanDefinition beanDefinition) {
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            final var metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
            return metadata.getClassName();
        } else {
            throw new IllegalStateException("XML bean configuration is not supported.");
        }
    }

    public static String findPluggableRootComponentBeanName(final Class<?> beanClass) {
        return findMergedAnnotation(beanClass, PluggableRootComponent.class) == null ? null : (String) getValue(findMergedAnnotation(beanClass, PluggableRootComponent.class), "value");
    }

    public static boolean isRegularSpringComponent(final Class<?> beanClass) {
        return isNotPluggable(beanClass);
    }

    public static boolean isNotPluggable(final Class<?> beanClass) {
        return findMergedAnnotation(beanClass, PluggableComponent.class) == null;
    }

    public static class BeanConfigurationException extends RuntimeException {

        public BeanConfigurationException(final Throwable cause) {
            super(cause);
        }
    }

    public static boolean basePackageHasBeenProcessedBefore(final String packageName, final Set<String> scannedBasePackages) {
        final var optionalPackagePair = findPackageScannedBefore(packageName, scannedBasePackages);
        if (optionalPackagePair.isPresent()) {
            final var packagePair = optionalPackagePair.get();
            if (packagePair.areEqual()) {
                return true;
            } else {
                throw new IllegalStateException(format("""
                                Illegal configuration: a @PluggableRootComponent is activated in package %s, but another @PluggableRootComponent is also \
                                activated in its base package %s. If package %s is the root package of the corresponding application layer, all @Adapter \
                                and @Orchestration annotated beans should reside in this package. One or multiple @PluggableRootComponents are allowed to \
                                only to reside in the root package of their corresponding application layer.\
                                """,
                        packagePair.getSubpackage(), packagePair.getBasepackage(), packagePair.getBasepackage()));
            }
        }
        return false;
    }

    private static Optional<PackagePair> findPackageScannedBefore(final String packageName, final Set<String> scannedBasePackages) {
        final var optionalPackageScannedBefore =
                scannedBasePackages.stream()
                        .filter(scannedBasePackage -> packageName.startsWith(scannedBasePackage) || scannedBasePackage.startsWith(packageName))
                        .findAny();
        return optionalPackageScannedBefore.map(packageScannedBefore -> new PackagePair(packageName, packageScannedBefore));
    }

    @Getter
    static class PackagePair {
        final String basepackage;
        final String subpackage;

        public PackagePair(final String package1, final String package2) {
            if (package1.startsWith(package2)) {
                basepackage = package2;
                subpackage = package1;
            } else {
                basepackage = package1;
                subpackage = package2;
            }
        }

        public boolean areEqual() {
            return basepackage.equals(subpackage);
        }
    }
}