package nl.pancompany.clean.architecture.application.port.in;

import nl.pancompany.clean.architecture.application.domain.model.dummy.Dummy;

public interface SaveDummyUsecase {

    void saveDummy(Dummy dummy);

}
