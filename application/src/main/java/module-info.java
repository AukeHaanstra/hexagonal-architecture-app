module nl.pancompany.clean.architecture.application {
    exports nl.pancompany.clean.architecture.application;
    exports nl.pancompany.clean.architecture.application.domain.model.dummy;
    exports nl.pancompany.clean.architecture.application.port.out;
    exports nl.pancompany.clean.architecture.application.port.in;

    requires static lombok;

    requires nl.pancompany.clean.architecture.common;

    requires org.slf4j;
}