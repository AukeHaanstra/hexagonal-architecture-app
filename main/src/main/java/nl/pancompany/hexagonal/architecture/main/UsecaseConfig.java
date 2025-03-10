package nl.pancompany.hexagonal.architecture.main;

import lombok.RequiredArgsConstructor;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyDisplayPort;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;
import nl.pancompany.hexagonal.architecture.application.ApplicationContext;
import nl.pancompany.hexagonal.architecture.application.port.in.DisplayDummyUsecase;
import nl.pancompany.hexagonal.architecture.application.port.in.SaveDummyUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UsecaseConfig {

    @Bean
    public ApplicationContext usecaseContext(DummyRepositoryPort dummyRepository, DummyDisplayPort dummyDisplay) {
        return new ApplicationContext(dummyRepository, dummyDisplay);
    }

    @Bean
    public SaveDummyUsecase saveDummyUsecase(final ApplicationContext applicationContext) {
        return applicationContext.getSaveDummyUsecase();
    }

    @Bean
    public DisplayDummyUsecase displayDummyUsecase(final ApplicationContext applicationContext) {
        return applicationContext.getDisplayDummyUsecase();
    }

}
