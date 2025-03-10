package nl.pancompany.hexagonal.architecture.application.port.in;

import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyId;

public interface DisplayDummyUsecase {

    void display(DummyId dummyId);

}
