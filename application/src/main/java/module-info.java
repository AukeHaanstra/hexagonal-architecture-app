module nl.pancompany.hexagonal.architecture.application {
    exports nl.pancompany.hexagonal.architecture.application;
    exports nl.pancompany.hexagonal.architecture.application.domain.model.dummy;
    exports nl.pancompany.hexagonal.architecture.application.port.out;
    exports nl.pancompany.hexagonal.architecture.application.port.in;

    requires static lombok;

    requires nl.pancompany.hexagonal.architecture.common;

    requires org.slf4j;
}