module nl.pancompany.clean.architecture.common {

    exports nl.pancompany.clean.architecture.common.annotation.architecture;

    requires static lombok;
    requires static spring.core;
    requires static spring.context;

    requires org.apache.commons.lang3;
}