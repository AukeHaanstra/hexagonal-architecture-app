package nl.pancompany.clean.architecture.main;

import nl.pancompany.clean.architecture.common.annotation.architecture.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static nl.pancompany.clean.architecture.main.ApplicationConstants.ComponentScanConstants.ADAPTER_PACKAGE_REGEX;
import static nl.pancompany.clean.architecture.main.ApplicationConstants.ComponentScanConstants.COMPONENT_SCAN_BASE_PACKAGE;

@Main
@SpringBootApplication
@ComponentScan(basePackages = { COMPONENT_SCAN_BASE_PACKAGE },
        // See https://docs.spring.io/spring-boot/api/java/org/springframework/boot/test/context/TestComponent.html
        excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ADAPTER_PACKAGE_REGEX)})
public class DummyApp {

    public static void main(final String[] args) {
        SpringApplication.run(DummyApp.class, args);
    }

    @Profile({"enable-persistence"})
    @EnableJpaAuditing
    public static class PersistenceConfiguration {
    }

    @Profile({"!enable-persistence"})
    @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class})
    public static class NoPersistenceConfiguration {
    }

}
