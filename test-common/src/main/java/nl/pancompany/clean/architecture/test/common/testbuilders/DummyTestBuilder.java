package nl.pancompany.clean.architecture.test.common.testbuilders;

import nl.pancompany.clean.architecture.domain.model.dummy.Dummy;
import nl.pancompany.clean.architecture.domain.model.dummy.DummyId;
import nl.pancompany.clean.architecture.test.common.TestData;

public class DummyTestBuilder {

    private DummyId dummyId;
    private String dummyData;

    private DummyTestBuilder() {}

    public static ScenarioPicker withDefaults() {
        var dummyTestBuilder = new DummyTestBuilder();
        dummyTestBuilder.dummyId = new DummyId(TestData.DEFAULT_DUMMY_NAME);
        dummyTestBuilder.dummyData = TestData.DEFAULT_DUMMY_DATA;
        return dummyTestBuilder.new ScenarioPicker();
    }

    public class ScenarioPicker {

        public DummyTestBuilder forSave() {
            dummyId = new DummyId(TestData.DUMMY_FOR_CREATION_NAME);
            dummyData = TestData.DUMMY_FOR_CREATION_DATA;
            return DummyTestBuilder.this;
        }

        public DummyTestBuilder forDisplay() {
            dummyId = new DummyId(TestData.DUMMY_FOR_DISPLAY_NAME);
            dummyData = TestData.DUMMY_FOR_DISPLAY_DATA;
            return DummyTestBuilder.this;
        }
    }

    public DummyTestBuilder withDummyId(DummyId dummyId) {
        this.dummyId = dummyId;
        return this;
    }

    public DummyTestBuilder withDummyData(String dummyData) {
        this.dummyData = dummyData;
        return this;
    }

    public Dummy build() {
        return Dummy.builder()
                .dummyId(this.dummyId)
                .dummyData(this.dummyData)
                .buildEntity();
    }

}