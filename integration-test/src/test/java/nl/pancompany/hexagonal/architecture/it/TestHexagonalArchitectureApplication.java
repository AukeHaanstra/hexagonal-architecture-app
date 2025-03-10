package nl.pancompany.hexagonal.architecture.it;

import nl.pancompany.hexagonal.architecture.it.support.testcontainers.TestcontainersConfiguration;
import nl.pancompany.hexagonal.architecture.main.DummyApp;
import org.springframework.boot.SpringApplication;

import static java.lang.System.setProperty;
import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

public class TestHexagonalArchitectureApplication {

	public static void main(String[] args) {
		setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "demo-stdout-run");
		SpringApplication.from(DummyApp::main).with(TestcontainersConfiguration.class).run(args);
	}

}