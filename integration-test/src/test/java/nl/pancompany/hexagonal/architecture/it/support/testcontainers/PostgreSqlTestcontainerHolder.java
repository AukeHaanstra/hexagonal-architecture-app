package nl.pancompany.hexagonal.architecture.it.support.testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSqlTestcontainerHolder {

    private PostgreSqlTestcontainerHolder() {
    }

    static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    static String[] getDatasourceProperties() {
        return new String[]{
                "spring.datasource.url=" + POSTGRES_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + POSTGRES_CONTAINER.getUsername(),
                "spring.datasource.password=" + POSTGRES_CONTAINER.getPassword()
        };
    }

}