package nl.pancompany.clean.architecture.usecase.interactor;

import lombok.RequiredArgsConstructor;
import nl.pancompany.clean.architecture.domain.model.dummy.DummyId;
import nl.pancompany.clean.architecture.domain.port.out.DummyDisplayPort;
import nl.pancompany.clean.architecture.domain.port.out.DummyRepositoryPort;
import nl.pancompany.clean.architecture.usecase.port.in.DisplayDummyUsecase;

@RequiredArgsConstructor
public class DisplayDummyInteractor implements DisplayDummyUsecase {

    private final DummyRepositoryPort dummyRepositoryPort;
    private final DummyDisplayPort dummyDisplayPort;

    public void display(DummyId dummyId) {
        var dummy = dummyRepositoryPort.get(dummyId);
        dummy.display(dummyDisplayPort);
    }
}