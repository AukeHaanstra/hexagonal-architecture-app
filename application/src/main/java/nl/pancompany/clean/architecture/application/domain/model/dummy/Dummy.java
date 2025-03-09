package nl.pancompany.clean.architecture.application.domain.model.dummy;

import nl.pancompany.clean.architecture.application.port.out.DummyDisplayPort;
import nl.pancompany.clean.architecture.application.port.out.DummyRepositoryPort;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Dummy {

    private final DummyId dummyId;
    private String dummyData;

    public Dummy(DummyDto dummyDto) {
        this.dummyId = requireNonNull(dummyDto.dummyId());
        this.dummyData = requireNonNull(dummyDto.dummyData());
    }

    public static DummyDto.DummyDtoBuilder builder() {
        return new DummyDto.DummyDtoBuilder();
    }

    private DummyDto toDto() {
        return new DummyDto(dummyId, dummyData);
    }

    public void display(DummyDisplayPort dummyDisplayPort) {
        dummyDisplayPort.display(dummyData);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Dummy dummy)) return false;
        return Objects.equals(dummyId, dummy.dummyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dummyId);
    }

    public void persistTo(DummyRepositoryPort dummyRepository) {
        dummyRepository.save(toDto());
    }

    @Override
    public String toString() {
        return "Dummy{" +
                "dummyId=" + dummyId +
                '}';
    }

}