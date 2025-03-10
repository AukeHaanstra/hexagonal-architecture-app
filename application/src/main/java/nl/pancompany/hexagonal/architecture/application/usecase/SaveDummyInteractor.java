package nl.pancompany.hexagonal.architecture.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.Dummy;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;
import nl.pancompany.hexagonal.architecture.application.port.in.SaveDummyUsecase;

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