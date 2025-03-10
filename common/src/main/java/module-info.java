module nl.pancompany.hexagonal.architecture.common {

    exports nl.pancompany.hexagonal.architecture.common.annotation.architecture;

    requires static lombok;
    requires static spring.core;
    requires static spring.context;

    requires org.apache.commons.lang3;
}