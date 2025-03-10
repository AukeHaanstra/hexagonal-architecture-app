package nl.pancompany.hexagonal.architecture.it.support.fakeadapters;

import lombok.RequiredArgsConstructor;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyDisplayPort;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Wire-in test adapter beans into the test configuration by declaring them here as beans.
 * However, only do so for ports that do not yet have a real implementation
 * Override wirings by using @Primary @Bean annotations.
 */
@TestConfiguration
@RequiredArgsConstructor
public class FakeAdapterConfiguration {

    @Bean
    public DummyDisplayPort dummyDisplayPort() {
        return new DummyDataSink();
    }

}