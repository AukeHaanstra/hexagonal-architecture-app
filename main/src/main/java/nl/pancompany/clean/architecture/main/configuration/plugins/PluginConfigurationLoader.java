package nl.pancompany.clean.architecture.main.configuration.plugins;

import lombok.RequiredArgsConstructor;
import nl.pancompany.clean.architecture.main.ApplicationConstants;
import org.springframework.core.env.Environment;

import static nl.pancompany.clean.architecture.main.ApplicationConstants.ComponentScanConstants.PLUGIN_CONFIGURATION_PROPERTIES_PREFIX;
import static org.springframework.boot.context.properties.bind.Bindable.ofInstance;
import static org.springframework.boot.context.properties.bind.Binder.get;

@RequiredArgsConstructor
public class PluginConfigurationLoader {

    private final Environment env;

    public PluginConfiguration load() {
        final var binder = get(env);
        final var bindableConfiguration = ofInstance(new PluginConfiguration(env));
        binder.bind(PLUGIN_CONFIGURATION_PROPERTIES_PREFIX, bindableConfiguration);
        return bindableConfiguration.getValue().get();
    }

}