package nl.pancompany.clean.architecture.it.support.testcontainers;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.lifecycle.Startables;

import static org.apache.commons.lang3.ArrayUtils.addAll;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static {
        Startables.deepStart(PostgreSqlTestcontainerHolder.POSTGRES_CONTAINER /*, <other containers> */).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        TestPropertyValues.of(ArrayUtils.addAll(PostgreSqlTestcontainerHolder.getDatasourceProperties() /*, <other container properties> */)).applyTo(ctx.getEnvironment());
    }

}