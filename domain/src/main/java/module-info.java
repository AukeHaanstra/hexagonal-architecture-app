module nl.pancompany.clean.architecture.domain {

    exports nl.pancompany.clean.architecture.domain.model.dummy;
    exports nl.pancompany.clean.architecture.domain.service;
    exports nl.pancompany.clean.architecture.domain.port.out;
    exports nl.pancompany.clean.architecture.domain.port;
    exports nl.pancompany.clean.architecture.domain.model;

    requires static lombok;
    requires nl.pancompany.clean.architecture.common;
}