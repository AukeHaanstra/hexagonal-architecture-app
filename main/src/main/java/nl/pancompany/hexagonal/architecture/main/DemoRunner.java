package nl.pancompany.hexagonal.architecture.main;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.Dummy;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyId;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyDisplayPort;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoRunner {

    private final DummyRepositoryPort dummyRepository;
    private final DummyDisplayPort dummyDisplay;

    @PostConstruct
    public void runDemo() {
        var dummy = Dummy.builder()
                .dummyId(DummyId.of("dummy"))
                .dummyData("dummy-data")
                .buildEntity();
        dummy.persistTo(dummyRepository);
        dummy.display(dummyDisplay);
    }
}