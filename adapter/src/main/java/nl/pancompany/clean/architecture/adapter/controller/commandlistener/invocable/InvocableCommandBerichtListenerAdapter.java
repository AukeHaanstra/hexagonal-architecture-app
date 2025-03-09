package nl.pancompany.clean.architecture.adapter.controller.commandlistener.invocable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.clean.architecture.common.annotation.architecture.Adapter;
import nl.pancompany.clean.architecture.application.domain.model.dummy.Dummy;
import nl.pancompany.clean.architecture.application.domain.model.dummy.DummyId;
import nl.pancompany.clean.architecture.application.port.in.DisplayDummyUsecase;
import nl.pancompany.clean.architecture.application.port.in.SaveDummyUsecase;

/**
 * An incoming adapter that might listen to a queue to receive commands from it
 */
@Slf4j
@RequiredArgsConstructor
@Adapter("command-bericht-listener-adapter")
public class InvocableCommandBerichtListenerAdapter {

    private static final String SAVE_COMMAND = "save";
    private static final String DISPLAY_COMMAND = "display";
    private static final String ANY_WHITESPACE_CHAR = "\\s";

    private final SaveDummyUsecase saveDummyUsecase;
    private final DisplayDummyUsecase displayDummyUsecase;

    public void receiveMessage(final String commandMessage) {
        String[] commandParts = commandMessage.split(ANY_WHITESPACE_CHAR);
        if (commandParts.length >= 2) {
            String command = commandParts[0];
            String dummyName = commandParts[1];
            if (command.equals(SAVE_COMMAND) && commandParts.length == 3) {
                String dummyData = commandParts[2];
                saveDummyUsecase.saveDummy(Dummy.builder()
                        .dummyId(DummyId.of(dummyName))
                        .dummyData(dummyData)
                        .buildEntity());
            } else if (command.equals(DISPLAY_COMMAND) && commandParts.length == 2) {
                displayDummyUsecase.display(DummyId.of(dummyName));
            } else {
                throw new UnsupportedOperationException(commandMessage);
            }
        }
    }
}
