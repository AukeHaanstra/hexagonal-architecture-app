module nl.pancompany.hexagonal.architecture.common.test {
    exports nl.pancompany.hexagonal.architecture.test.common;
    exports nl.pancompany.hexagonal.architecture.test.common.testbuilders;
    exports nl.pancompany.hexagonal.architecture.test.common.fakeadapters;
    exports nl.pancompany.hexagonal.architecture.test.common.extractors;

    requires static lombok;
    requires nl.pancompany.hexagonal.architecture.application;
    requires nl.pancompany.hexagonal.architecture.common;
}