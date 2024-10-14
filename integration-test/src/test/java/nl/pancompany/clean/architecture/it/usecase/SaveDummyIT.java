package nl.pancompany.clean.architecture.it.usecase;

import nl.pancompany.clean.architecture.adapter.controller.commandlistener.invocable.InvocableCommandBerichtListenerAdapter;
import nl.pancompany.clean.architecture.domain.model.dummy.DummyId;
import nl.pancompany.clean.architecture.domain.port.out.DummyRepositoryPort;
import nl.pancompany.clean.architecture.it.support.annotations.DummyIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static nl.pancompany.clean.architecture.test.common.TestBuilders.dummy;
import static nl.pancompany.clean.architecture.test.common.TestData.*;
import static nl.pancompany.clean.architecture.test.common.extractors.DummyExtractor.getDto;
import static org.assertj.core.api.Assertions.assertThat;

@DummyIT
class SaveDummyIT {

    @Autowired
    InvocableCommandBerichtListenerAdapter invocableCommandBerichtListenerAdapter;

    @Autowired
    DummyRepositoryPort dummyRepositoryPort;

    @Test
    public void savesDummy() {
        var dummyDto = getDto(dummy().forSave().build());

        invocableCommandBerichtListenerAdapter.receiveMessage(SAVE_COMMAND + COMMAND_SEPARATOR + dummyDto.dummyId()
                .name() + COMMAND_SEPARATOR + dummyDto.dummyData());

        assertThat(getDto(dummyRepositoryPort.get(dummyDto.dummyId()))).isEqualTo(dummyDto);
    }
}