package nl.pancompany.clean.architecture.usecase.interactor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.clean.architecture.domain.model.dummy.Dummy;
import nl.pancompany.clean.architecture.domain.port.out.DummyRepositoryPort;
import nl.pancompany.clean.architecture.usecase.port.in.SaveDummyUsecase;

@RequiredArgsConstructor
@Slf4j
public class SaveDummyInteractor implements SaveDummyUsecase {

    private final DummyRepositoryPort dummyRepository;

    @Override
    public void saveDummy(Dummy dummy) {
        dummy.persistTo(dummyRepository);
        log.debug("Created {}", dummy);
    }
}