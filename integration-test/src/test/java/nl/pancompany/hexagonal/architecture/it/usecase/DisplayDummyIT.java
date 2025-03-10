package nl.pancompany.hexagonal.architecture.it.usecase;

import nl.pancompany.hexagonal.architecture.adapter.controller.commandlistener.invocable.InvocableCommandBerichtListenerAdapter;
import nl.pancompany.hexagonal.architecture.it.support.annotations.DummyIT;
import nl.pancompany.hexagonal.architecture.it.support.fakeadapters.DummyDataSink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static nl.pancompany.hexagonal.architecture.test.common.TestBuilders.dummy;
import static nl.pancompany.hexagonal.architecture.test.common.TestData.*;
import static nl.pancompany.hexagonal.architecture.test.common.extractors.DummyExtractor.getDto;
import static org.assertj.core.api.Assertions.assertThat;

@DummyIT
class DisplayDummyIT {

    @Autowired
    InvocableCommandBerichtListenerAdapter invocableCommandBerichtListenerAdapter;

    @Autowired
    DummyDataSink dummyDataSink;

    @Test
    public void displaysDummyData() {
        var dummyDto = getDto(dummy().forDisplay().build());
        invocableCommandBerichtListenerAdapter.receiveMessage(SAVE_COMMAND + COMMAND_SEPARATOR + dummyDto.dummyId()
                .name() + COMMAND_SEPARATOR + dummyDto.dummyData());

        invocableCommandBerichtListenerAdapter.receiveMessage(DISPLAY_COMMAND + COMMAND_SEPARATOR + dummyDto.dummyId()
                .name());

        assertThat(dummyDataSink.getData()).isEqualTo(dummyDto.dummyData());
    }
}