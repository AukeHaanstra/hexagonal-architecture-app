package nl.pancompany.hexagonal.architecture.it.adapter;

import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;
import nl.pancompany.hexagonal.architecture.it.support.annotations.DummyIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static nl.pancompany.hexagonal.architecture.test.common.TestBuilders.dummy;
import static nl.pancompany.hexagonal.architecture.test.common.extractors.DummyExtractor.getDto;
import static org.assertj.core.api.Assertions.assertThat;

@DummyIT
class DummyRepositoryIT {

    @Autowired
    DummyRepositoryPort dummyRepository;

    @Test
    public void savesAndRetrievesDummy() {
        var dummy = dummy().forSave().build();
        var dummyDto = getDto(dummy);

        dummy.persistTo(dummyRepository);
        var savedDummy = dummyRepository.get(dummyDto.dummyId());

        assertThat(getDto(savedDummy)).isEqualTo(dummyDto);
    }
}