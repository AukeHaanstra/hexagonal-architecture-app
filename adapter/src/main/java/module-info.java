module nl.pancompany.hexagonal.architecture.adapter {

    exports nl.pancompany.hexagonal.architecture.adapter.controller.commandlistener.invocable;
    requires nl.pancompany.hexagonal.architecture.application;
    requires nl.pancompany.hexagonal.architecture.common;

    requires static lombok;

    requires org.slf4j;
    requires org.mapstruct;

    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.boot;
    requires spring.data.jpa;
    requires spring.data.commons;

    requires jakarta.persistence;
    requires jakarta.annotation;
    requires spring.web;
    requires org.hibernate.orm.core;
    requires spring.tx;

    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;

    requires jakarta.validation;
    requires spring.boot.autoconfigure;
}
