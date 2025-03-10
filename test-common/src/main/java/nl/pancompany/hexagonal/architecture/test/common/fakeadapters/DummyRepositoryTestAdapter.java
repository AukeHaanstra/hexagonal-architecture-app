package nl.pancompany.hexagonal.architecture.test.common.fakeadapters;

import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.Dummy;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyDto;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyId;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;

import java.util.HashMap;
import java.util.Map;

public class DummyRepositoryTestAdapter implements DummyRepositoryPort {

    private final Map<DummyId, DummyDto> dummies = new HashMap<>();

    @Override
    public void save(DummyDto dummy) {
        dummies.put(dummy.dummyId(), dummy);
    }

    @Override
    public Dummy get(DummyId dummyId) {
        return new Dummy(dummies.get(dummyId));
    }
}