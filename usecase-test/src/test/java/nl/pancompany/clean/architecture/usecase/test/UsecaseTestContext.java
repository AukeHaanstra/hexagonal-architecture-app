package nl.pancompany.clean.architecture.usecase.test;

import lombok.Getter;
import nl.pancompany.clean.architecture.domain.port.out.DummyDisplayPort;
import nl.pancompany.clean.architecture.test.common.fakeadapters.DummyRepositoryTestAdapter;
import nl.pancompany.clean.architecture.usecase.UsecaseContext;
import nl.pancompany.clean.architecture.usecase.port.in.SaveDummyUsecase;
import nl.pancompany.clean.architecture.usecase.port.in.DisplayDummyUsecase;

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

        final var usecaseContext = new UsecaseContext(dummyRepositoryTestAdapter, dummyDisplayPort);

        this.saveDummyUsecase = usecaseContext.getSaveDummyUsecase();
        this.displayDummyUsecase = usecaseContext.getDisplayDummyUsecase();
    }
}
