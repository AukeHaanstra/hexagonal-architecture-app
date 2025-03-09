package nl.pancompany.clean.architecture.application.port.in;

import nl.pancompany.clean.architecture.application.domain.model.dummy.DummyId;

public interface DisplayDummyUsecase {

    void display(DummyId dummyId);

}
