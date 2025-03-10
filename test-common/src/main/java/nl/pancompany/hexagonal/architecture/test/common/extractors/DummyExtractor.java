package nl.pancompany.hexagonal.architecture.test.common.extractors;

import lombok.Getter;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.Dummy;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyDto;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyId;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;

public class DummyExtractor implements DummyRepositoryPort {

    @Getter
    private DummyDto dummyDto;

    public static DummyDto getDto(Dummy dummy) {
        var extractor = new DummyExtractor();
        dummy.persistTo(extractor);
        return extractor.getDummyDto();
    }

    @Override
    public void save(DummyDto dummyDto) {
        this.dummyDto = dummyDto;
    }

    @Override
    public Dummy get(DummyId dummyId) {
        throw new UnsupportedOperationException();
    }
}