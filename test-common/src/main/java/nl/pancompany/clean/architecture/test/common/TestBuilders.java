package nl.pancompany.clean.architecture.test.common;

import nl.pancompany.clean.architecture.test.common.testbuilders.DummyTestBuilder;
import nl.pancompany.clean.architecture.test.common.testbuilders.DummyTestBuilder.ScenarioPicker;

public class TestBuilders {

    private TestBuilders() {}

    public static ScenarioPicker dummy() {
        return DummyTestBuilder.withDefaults();
    }

}