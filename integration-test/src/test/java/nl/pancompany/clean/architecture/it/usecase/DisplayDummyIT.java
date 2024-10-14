package nl.pancompany.clean.architecture.it.usecase;

import nl.pancompany.clean.architecture.adapter.controller.commandlistener.invocable.InvocableCommandBerichtListenerAdapter;
import nl.pancompany.clean.architecture.it.support.annotations.DummyIT;
import nl.pancompany.clean.architecture.it.support.fakeadapters.DummyDataSink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static nl.pancompany.clean.architecture.test.common.extractors.DummyExtractor.getDto;
import static nl.pancompany.clean.architecture.test.common.TestBuilders.dummy;
import static nl.pancompany.clean.architecture.test.common.TestData.*;
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