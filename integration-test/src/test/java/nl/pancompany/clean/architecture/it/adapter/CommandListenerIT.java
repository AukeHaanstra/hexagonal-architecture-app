package nl.pancompany.clean.architecture.it.adapter;

import nl.pancompany.clean.architecture.adapter.controller.commandlistener.invocable.InvocableCommandBerichtListenerAdapter;
import nl.pancompany.clean.architecture.domain.port.out.DummyRepositoryPort;
import nl.pancompany.clean.architecture.it.support.annotations.DummyNoPersistenceIT;
import nl.pancompany.clean.architecture.usecase.port.in.DisplayDummyUsecase;
import nl.pancompany.clean.architecture.usecase.port.in.SaveDummyUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static nl.pancompany.clean.architecture.test.common.TestBuilders.dummy;
import static nl.pancompany.clean.architecture.test.common.TestData.*;
import static nl.pancompany.clean.architecture.test.common.extractors.DummyExtractor.getDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@DummyNoPersistenceIT
class CommandListenerIT {

    @Autowired
    InvocableCommandBerichtListenerAdapter invocableCommandBerichtListenerAdapter;

    @MockBean
    SaveDummyUsecase saveDummyUsecase;

    @MockBean
    DisplayDummyUsecase displayDummyUsecase;

    @MockBean
    DummyRepositoryPort dummyRepositoryPort;

    @Test
    public void executesSaveDummyDataUsecase() {
        var dummy = dummy().forSave().build();
        var dummyDto = getDto(dummy);

        invocableCommandBerichtListenerAdapter.receiveMessage(SAVE_COMMAND + COMMAND_SEPARATOR + dummyDto.dummyId()
                .name() + COMMAND_SEPARATOR + dummyDto.dummyData());

        verify(saveDummyUsecase).saveDummy(dummy);
    }

    @Test
    public void executesDisplayDummyDataUsecase() {
        var dummyDto = getDto(dummy().forDisplay().build());

        invocableCommandBerichtListenerAdapter.receiveMessage(DISPLAY_COMMAND + COMMAND_SEPARATOR + dummyDto.dummyId()
                .name());

        verify(displayDummyUsecase).display(dummyDto.dummyId());
    }

    @Test
    public void throwsExceptionOnInvalidCommand() {
        assertThatThrownBy(() -> invocableCommandBerichtListenerAdapter.receiveMessage("nonsense nonsense nonsense")).isInstanceOf(Exception.class);
    }

    // etc. testing more logical branches
}