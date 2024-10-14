module nl.pancompany.clean.architecture.usecase {
    exports nl.pancompany.clean.architecture.usecase.port.in;
    exports nl.pancompany.clean.architecture.usecase;

    requires static lombok;

    requires nl.pancompany.clean.architecture.domain;
    requires nl.pancompany.clean.architecture.common;

    requires org.slf4j;
}