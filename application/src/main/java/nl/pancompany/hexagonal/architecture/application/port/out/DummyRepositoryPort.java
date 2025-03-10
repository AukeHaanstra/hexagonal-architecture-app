package nl.pancompany.hexagonal.architecture.application.port.out;

import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.Dummy;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyDto;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyId;

public interface DummyRepositoryPort {

    void save(DummyDto dummyDto);
    Dummy get(DummyId dummyId);
}
