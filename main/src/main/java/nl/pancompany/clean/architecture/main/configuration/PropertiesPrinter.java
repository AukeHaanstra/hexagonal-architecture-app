package nl.pancompany.clean.architecture.main.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.clean.architecture.common.annotation.architecture.PluggableComponent;
import nl.pancompany.clean.architecture.main.configuration.plugins.PluginConfigurationLoader;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("activate-properties-printer")
public class PropertiesPrinter {

    private static final String CONTAINS_APPLICATION_YAML_FILENAME_REGEX = "^.*application[\\-a-zA-Z]*.yaml.*$";
    private static final Set<String> FILTERED_PROPS_PREFIXES = Set.of("plug-in.configuration", "spring.profiles", "component-profiles.matchers",
            "activate-profiles.matchers", "enable-profiles.matchers", "functional-profiles.matchers", "run-profiles.matchers");
    private static final Set<String> SECRETS_KEYWORDS = Set.of("credentials", "password", "token");
    private static final String UNICODE_CHECKMARK = "\u2713";
    private static final String UNICODE_HEAVY_BALLOT = "\u2718";

    private final ComponentProfilesConfiguration componentProfilesConfiguration;
    private final ActivateProfilesConfiguration activateProfilesConfiguration;
    private final EnableProfilesConfiguration enableProfilesConfiguration;
    private final FunctionalProfilesConfiguration functionalProfilesConfiguration;
    private final RunProfilesConfiguration runProfilesConfiguration;

    @EventListener
    public void handleContextRefreshed(final ContextRefreshedEvent event) {
        final var applicationContext = event.getApplicationContext();
        final var env = (ConfigurableEnvironment) applicationContext.getEnvironment();
        printProperties(env);
        printActivatedPluggableComponents(applicationContext, env);
        printActiveProfiles(env, componentProfilesConfiguration);
        printActiveProfiles(env, activateProfilesConfiguration);
        printActiveProfiles(env, enableProfilesConfiguration);
        printActiveProfiles(env, functionalProfilesConfiguration);
        printActiveProfiles(env, runProfilesConfiguration);
    }

    private static void printProperties(final ConfigurableEnvironment env) {
        final var isNoSecret =
                matchIfNoneMatch(SECRETS_KEYWORDS
                        .stream()
                        .map(filteredKeyWord -> compile(format("^.*%s.*$", filteredKeyWord), CASE_INSENSITIVE))
                        .map(Pattern::asPredicate));
        final var shouldPrintPropsSection = matchIfNoneMatch(FILTERED_PROPS_PREFIXES.stream()
                .map(prefix -> key -> key.startsWith(prefix)));
        final Predicate<OriginTrackedMapPropertySource> isFromPropertiesYaml = ps -> ps.getName()
                .matches(CONTAINS_APPLICATION_YAML_FILENAME_REGEX);

        final var properties = env.getPropertySources()
                .stream()
                .filter(OriginTrackedMapPropertySource.class::isInstance)
                .map(ps -> (OriginTrackedMapPropertySource) ps)
                .filter(isFromPropertiesYaml)
                .map(ps -> ps.getSource().keySet())
                .flatMap(Collection::stream)
                .filter(shouldPrintPropsSection)
                .filter(isNoSecret)
                .distinct()
                .sorted()
                .map(key -> format("  %-60s = %s", key, env.getProperty(key)))
                .collect(joining(lineSeparator()));
        log.info("The following properties have been set:{}{}{}", lineSeparator(), properties, lineSeparator());
    }

    private static Predicate<String> matchIfNoneMatch(final Stream<Predicate<String>> predicates) {
        return predicates
                .reduce(Predicate::or)
                .map(Predicate::negate)
                .get();
    }

    private void printActivatedPluggableComponents(final ApplicationContext applicationContext, final ConfigurableEnvironment env) {
        final var pluginConfiguration = new PluginConfigurationLoader(env).load();

        /* Differences between toBeActivatedPluggableComponentsList and activatedPluggableComponentsList can arise from using
           @Profile annotations, which are no longer necessary, or simply because there is no bean defined with the corresponding name. */
        final var activatedPluggableComponentsList = asList(applicationContext.getBeanNamesForAnnotation(PluggableComponent.class));
        final var toBeActivatedPluggableComponentsList = pluginConfiguration.getAllActivatedComponents().stream()
                .sorted()
                .map(toBeActivatedComponentName -> format("  %-40s |      %s", toBeActivatedComponentName,
                        activatedPluggableComponentsList.contains(toBeActivatedComponentName) ? UNICODE_CHECKMARK : UNICODE_HEAVY_BALLOT))
                .toList();
        final var header = format("  %-40s |    active%n","pluggable component");
        final var separator = format("--------------------------------------------------------%n");
        log.info("The following {} pluggable components should be active:{}{}{}{}{}{}", toBeActivatedPluggableComponentsList.size(), lineSeparator(),
                lineSeparator(), header, separator, join(lineSeparator(), toBeActivatedPluggableComponentsList), lineSeparator());

        final var activatedRepositoriesList = toSortedUnorderedList(asList(applicationContext.getBeanNamesForAnnotation(Repository.class)));
        log.info("The following {} JPA repositories are active:{}{}{}", activatedRepositoriesList.size(), lineSeparator(), join(lineSeparator(),
                activatedRepositoriesList), lineSeparator());
    }

    private static List<String> toSortedUnorderedList(final Collection<String> unsortedCollection) {
        return unsortedCollection.stream()
                .sorted()
                .map(PropertiesPrinter::toUnorderedList)
                .toList();
    }

    private static String toUnorderedList(final String listItem) {
        return "- " + listItem;
    }

    private void printActiveProfiles(final ConfigurableEnvironment env, final ProfilesConfiguration profilesConfiguration) {
        final var activeProfiles = asList(env.getActiveProfiles());
        final var profileMatchers = profilesConfiguration.getMatchers().stream()
                .map(functionalMatcher -> compile("^" + functionalMatcher.replaceAll("\\*", ".*") + "$"))
                .map(Pattern::asPredicate)
                .toList();

        final var activeProfilesList = activeProfiles.stream()
                .filter(activeProfile -> profileMatchers.stream()
                        .anyMatch(matcher -> matcher.test(activeProfile)))
                .sorted()
                .map(PropertiesPrinter::toUnorderedList)
                .toList();

        log.info("The following {} {} profiles are active:{}{}{}",
                activeProfilesList.size(),
                profilesConfiguration.getProfileClassification(),
                lineSeparator(),
                join(lineSeparator(), activeProfilesList),
                lineSeparator());
    }

    public interface ProfilesConfiguration {
        String getProfileClassification();
        List<String> getMatchers();
    }

    @Component
    @ConfigurationProperties(prefix = "component-profiles")
    @Getter
    @Setter
    public static class ComponentProfilesConfiguration implements ProfilesConfiguration {

        private static final String PROFILE_CLASSIFICATION = "component";

        private List<String> matchers;

        @Override
        public String getProfileClassification() {
            return PROFILE_CLASSIFICATION;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "activate-profiles")
    @Getter
    @Setter
    public static class ActivateProfilesConfiguration implements ProfilesConfiguration {

        private static final String PROFILE_CLASSIFICATION = "activate";

        private List<String> matchers;

        @Override
        public String getProfileClassification() {
            return PROFILE_CLASSIFICATION;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "enable-profiles")
    @Getter
    @Setter
    public static class EnableProfilesConfiguration implements ProfilesConfiguration {

        private static final String PROFILE_CLASSIFICATION = "enable";

        private List<String> matchers;

        @Override
        public String getProfileClassification() {
            return PROFILE_CLASSIFICATION;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "functional-profiles")
    @Getter
    @Setter
    public static class FunctionalProfilesConfiguration implements ProfilesConfiguration {

        private static final String PROFILE_CLASSIFICATION = "functional";

        private List<String> matchers;

        @Override
        public String getProfileClassification() {
            return PROFILE_CLASSIFICATION;
        }
    }

    @Component
    @ConfigurationProperties(prefix = "run-profiles")
    @Getter
    @Setter
    public static class RunProfilesConfiguration implements ProfilesConfiguration {

        private static final String PROFILE_CLASSIFICATION = "run";

        private List<String> matchers;

        @Override
        public String getProfileClassification() {
            return PROFILE_CLASSIFICATION;
        }
    }
}