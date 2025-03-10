package nl.pancompany.hexagonal.architecture.it.adapter;

import nl.pancompany.hexagonal.architecture.adapter.controller.commandlistener.invocable.InvocableCommandBerichtListenerAdapter;
import nl.pancompany.hexagonal.architecture.application.port.in.DisplayDummyUsecase;
import nl.pancompany.hexagonal.architecture.application.port.in.SaveDummyUsecase;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;
import nl.pancompany.hexagonal.architecture.it.support.annotations.DummyNoPersistenceIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static nl.pancompany.hexagonal.architecture.test.common.TestBuilders.dummy;
import static nl.pancompany.hexagonal.architecture.test.common.TestData.*;
import static nl.pancompany.hexagonal.architecture.test.common.extractors.DummyExtractor.getDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@DummyNoPersistenceIT
class CommandListenerIT {

    @Autowired
    InvocableCommandBerichtListenerAdapter invocableCommandBerichtListenerAdapter;

    @MockitoBean
    SaveDummyUsecase saveDummyUsecase;

    @MockitoBean
    DisplayDummyUsecase displayDummyUsecase;

    @MockitoBean
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