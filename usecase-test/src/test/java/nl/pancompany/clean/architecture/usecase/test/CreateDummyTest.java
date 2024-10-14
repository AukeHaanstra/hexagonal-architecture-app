package nl.pancompany.clean.architecture.usecase.test;

import nl.pancompany.clean.architecture.domain.port.out.DummyRepositoryPort;
import nl.pancompany.clean.architecture.usecase.port.in.SaveDummyUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.pancompany.clean.architecture.test.common.TestBuilders.dummy;
import static nl.pancompany.clean.architecture.test.common.extractors.DummyExtractor.getDto;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateDummyTest {

    private SaveDummyUsecase saveDummyUsecase;
    private DummyRepositoryPort dummyRepositoryPort;

    @BeforeEach
    public void setUp() {
        var testContext = new UsecaseTestContext();
        saveDummyUsecase = testContext.getSaveDummyUsecase();
        dummyRepositoryPort = testContext.getDummyRepositoryTestAdapter();
    }

    @Test
    public void createsDummy() {
        var dummy = dummy().forSave().build();
        var dummyDto = getDto(dummy);

        saveDummyUsecase.saveDummy(dummy);

        var createdDummy = dummyRepositoryPort.get(dummyDto.dummyId());
        assertThat(getDto(createdDummy)).isEqualTo(dummyDto);
    }
}