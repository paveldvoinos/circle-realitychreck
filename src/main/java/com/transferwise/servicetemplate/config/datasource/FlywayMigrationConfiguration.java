package com.transferwise.servicetemplate.config.datasource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayMigrationConfiguration {

    @Bean
    public FlywayMigrationInitializer postgresFlywayInitializer(@Qualifier("postgresFlyway") Flyway postgresFlyway) {
        return new FlywayMigrationInitializer(postgresFlyway, flywayMigrationStrategy());
    }

    @Bean
    public FlywayMigrationInitializer mariaFlywayInitializer(@Qualifier("mariaFlyway") Flyway mariaFlyway) {
        return new FlywayMigrationInitializer(mariaFlyway, flywayMigrationStrategy());
    }

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return Flyway::migrate;
    }

}
