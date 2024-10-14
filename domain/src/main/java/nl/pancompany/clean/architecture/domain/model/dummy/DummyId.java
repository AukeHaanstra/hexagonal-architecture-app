package nl.pancompany.clean.architecture.domain.model.dummy;

public record DummyId(String name) {

    public static DummyId of(String name) {
        return new DummyId(name);
    }
}