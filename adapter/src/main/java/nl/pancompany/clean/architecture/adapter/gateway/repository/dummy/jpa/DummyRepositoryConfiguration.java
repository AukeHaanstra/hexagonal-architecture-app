package nl.pancompany.clean.architecture.adapter.gateway.repository.dummy.jpa;

import nl.pancompany.clean.architecture.adapter.gateway.repository.dummy.jpa.model.DummyJpaEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {DummyJpaEntity.class})
@EnableJpaRepositories(basePackageClasses = { JpaDummyRepository.class })
public class DummyRepositoryConfiguration {
}
