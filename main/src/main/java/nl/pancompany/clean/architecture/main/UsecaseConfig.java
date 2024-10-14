package nl.pancompany.clean.architecture.main;

import lombok.RequiredArgsConstructor;
import nl.pancompany.clean.architecture.domain.port.out.DummyDisplayPort;
import nl.pancompany.clean.architecture.domain.port.out.DummyRepositoryPort;
import nl.pancompany.clean.architecture.usecase.UsecaseContext;
import nl.pancompany.clean.architecture.usecase.port.in.DisplayDummyUsecase;
import nl.pancompany.clean.architecture.usecase.port.in.SaveDummyUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UsecaseConfig {

    @Bean
    public UsecaseContext usecaseContext(DummyRepositoryPort dummyRepository, DummyDisplayPort dummyDisplay) {
        return new UsecaseContext(dummyRepository, dummyDisplay);
    }

    @Bean
    public SaveDummyUsecase saveDummyUsecase(final UsecaseContext usecaseContext) {
        return usecaseContext.getSaveDummyUsecase();
    }

    @Bean
    public DisplayDummyUsecase displayDummyUsecase(final UsecaseContext usecaseContext) {
        return usecaseContext.getDisplayDummyUsecase();
    }

}
