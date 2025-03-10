package nl.pancompany.hexagonal.architecture.application.port.in;

import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.Dummy;

public interface SaveDummyUsecase {

    void saveDummy(Dummy dummy);

}
