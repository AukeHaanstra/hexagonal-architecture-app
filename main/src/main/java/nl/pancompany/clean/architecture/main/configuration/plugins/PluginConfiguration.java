package nl.pancompany.clean.architecture.main.configuration.plugins;

import lombok.Data;
import nl.pancompany.clean.architecture.common.annotation.architecture.PluggableComponent;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@Data
public class PluginConfiguration {

    private final Environment env;

    private Map<String, List<String>> configuration;

    public boolean isBeanActivatedInPluginConfiguration(final String beanName, final String activeProfile) {
        return configuration.keySet().contains(activeProfile) && configuration.get(activeProfile).contains(beanName);
    }

    public Optional<List<String>> getActivatedPluggableComponents(final String activeProfile) {
        return ofNullable(configuration.get(activeProfile));
    }

    public boolean isActivatedPluggableComponent(final Class<?> beanClass) {
        final var componentName = findMergedAnnotation(beanClass, PluggableComponent.class).value();
        return getAllActivatedComponents().stream()
                .anyMatch(componentName::equals);
    }

    public Set<String> getAllActivatedComponents() {
        return stream(env.getActiveProfiles())
                .map(this::getActivatedPluggableComponents)
                .flatMap(Optional::stream)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

}