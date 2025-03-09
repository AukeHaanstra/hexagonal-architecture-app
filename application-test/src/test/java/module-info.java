open module nl.pancompany.clean.architecture.application.test {

	requires nl.pancompany.clean.architecture.application;
	requires nl.pancompany.clean.architecture.common.test;

    requires static lombok;

	requires org.junit.jupiter.api;
	requires org.assertj.core;

	requires org.mockito;
	requires net.bytebuddy;
	requires net.bytebuddy.agent;
}