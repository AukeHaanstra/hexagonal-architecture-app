package nl.pancompany.hexagonal.architecture.application;

import lombok.Getter;
import nl.pancompany.hexagonal.architecture.common.annotation.architecture.Application;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyDisplayPort;
import nl.pancompany.hexagonal.architecture.application.usecase.SaveDummyInteractor;
import nl.pancompany.hexagonal.architecture.application.usecase.DisplayDummyInteractor;
import nl.pancompany.hexagonal.architecture.application.port.in.SaveDummyUsecase;
import nl.pancompany.hexagonal.architecture.application.port.in.DisplayDummyUsecase;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;

@Application
@Getter
public class ApplicationContext {

    private final SaveDummyUsecase saveDummyUsecase;
    private final DisplayDummyUsecase displayDummyUsecase;

    public ApplicationContext(DummyRepositoryPort dummyRepositoryPort, DummyDisplayPort dummyDisplayPort) {
        this.saveDummyUsecase = new SaveDummyInteractor(dummyRepositoryPort);
        this.displayDummyUsecase = new DisplayDummyInteractor(dummyRepositoryPort, dummyDisplayPort);
    }
}
