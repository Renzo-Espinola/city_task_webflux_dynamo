package com.hiperyum.city.task.api.infraestructure.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.r2dbc.core.DatabaseClient;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class R2dbcConfig {
    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        return new R2dbcEntityTemplate(databaseClient, PostgresDialect.INSTANCE);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost") // Dirección del servidor
                        .port(5432) // Puerto de la base de datos
                        .username("postgres") // Usuario de la base de datos
                        .password("postgres") // Contraseña del usuario
                        .database("webflux") // Nombre de la base de datos
                        .build()
        );
    }
}
