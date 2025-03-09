module nl.pancompany.clean.architecture.common.test {
    exports nl.pancompany.clean.architecture.test.common;
    exports nl.pancompany.clean.architecture.test.common.testbuilders;
    exports nl.pancompany.clean.architecture.test.common.fakeadapters;
    exports nl.pancompany.clean.architecture.test.common.extractors;

    requires static lombok;
    requires nl.pancompany.clean.architecture.application;
    requires nl.pancompany.clean.architecture.common;
}