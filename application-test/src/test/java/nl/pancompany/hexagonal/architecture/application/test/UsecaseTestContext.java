package nl.pancompany.hexagonal.architecture.application.test;

import lombok.Getter;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyDisplayPort;
import nl.pancompany.hexagonal.architecture.test.common.fakeadapters.DummyRepositoryTestAdapter;
import nl.pancompany.hexagonal.architecture.application.ApplicationContext;
import nl.pancompany.hexagonal.architecture.application.port.in.SaveDummyUsecase;
import nl.pancompany.hexagonal.architecture.application.port.in.DisplayDummyUsecase;

import static org.mockito.Mockito.mock;

@Getter
public class UsecaseTestContext {

    private final DummyRepositoryTestAdapter dummyRepositoryTestAdapter;
    private final DummyDisplayPort dummyDisplayPort;

    private final SaveDummyUsecase saveDummyUsecase;
    private final DisplayDummyUsecase displayDummyUsecase;

    public UsecaseTestContext() {
        dummyRepositoryTestAdapter = new DummyRepositoryTestAdapter();
        dummyDisplayPort = mock(DummyDisplayPort.class);

        final var usecaseContext = new ApplicationContext(dummyRepositoryTestAdapter, dummyDisplayPort);

        this.saveDummyUsecase = usecaseContext.getSaveDummyUsecase();
        this.displayDummyUsecase = usecaseContext.getDisplayDummyUsecase();
    }
}
