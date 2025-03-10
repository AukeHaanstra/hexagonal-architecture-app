package nl.pancompany.hexagonal.architecture.application.domain.model.dummy;

public record DummyDto(DummyId dummyId, String dummyData) {
    public static DummyDtoBuilder builder() {
        return new DummyDtoBuilder();
    }

    public static class DummyDtoBuilder {
        private DummyId dummyId;
        private String dummyData;

        DummyDtoBuilder() {
        }

        public DummyDtoBuilder dummyId(DummyId dummyId) {
            this.dummyId = dummyId;
            return this;
        }

        public DummyDtoBuilder dummyData(String dummyData) {
            this.dummyData = dummyData;
            return this;
        }

        public Dummy buildEntity() {
            return new Dummy(new DummyDto(this.dummyId, this.dummyData));
        }

        public DummyDto buildDto() {
            return new DummyDto(this.dummyId, this.dummyData);
        }

        public String toString() {
            return "Dummy.DummyDto.DummyDtoBuilder(dummyId=" + this.dummyId + ", dummyData=" + this.dummyData + ")";
        }
    }
}