package nl.pancompany.clean.architecture.usecase;

import lombok.Getter;
import nl.pancompany.clean.architecture.common.annotation.architecture.Usecase;
import nl.pancompany.clean.architecture.domain.port.out.DummyDisplayPort;
import nl.pancompany.clean.architecture.usecase.interactor.SaveDummyInteractor;
import nl.pancompany.clean.architecture.usecase.interactor.DisplayDummyInteractor;
import nl.pancompany.clean.architecture.usecase.port.in.SaveDummyUsecase;
import nl.pancompany.clean.architecture.usecase.port.in.DisplayDummyUsecase;
import nl.pancompany.clean.architecture.domain.port.out.DummyRepositoryPort;

@Usecase
@Getter
public class UsecaseContext {

    private final SaveDummyUsecase saveDummyUsecase;
    private final DisplayDummyUsecase displayDummyUsecase;

    public UsecaseContext(DummyRepositoryPort dummyRepositoryPort, DummyDisplayPort dummyDisplayPort) {
        this.saveDummyUsecase = new SaveDummyInteractor(dummyRepositoryPort);
        this.displayDummyUsecase = new DisplayDummyInteractor(dummyRepositoryPort, dummyDisplayPort);
    }
}
