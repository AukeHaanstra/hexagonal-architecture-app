package nl.pancompany.clean.architecture.domain.port.out;

import nl.pancompany.clean.architecture.domain.model.dummy.Dummy;
import nl.pancompany.clean.architecture.domain.model.dummy.DummyDto;
import nl.pancompany.clean.architecture.domain.model.dummy.DummyId;

public interface DummyRepositoryPort {

    void save(DummyDto dummyDto);
    Dummy get(DummyId dummyId);
}
