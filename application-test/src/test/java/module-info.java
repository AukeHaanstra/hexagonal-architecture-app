open module nl.pancompany.hexagonal.architecture.application.test {

	requires nl.pancompany.hexagonal.architecture.application;
	requires nl.pancompany.hexagonal.architecture.common.test;

    requires static lombok;

	requires org.junit.jupiter.api;
	requires org.assertj.core;

	requires org.mockito;
	requires net.bytebuddy;
	requires net.bytebuddy.agent;
}