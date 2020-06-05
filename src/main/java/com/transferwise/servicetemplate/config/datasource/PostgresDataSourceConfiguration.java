package com.transferwise.servicetemplate.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class PostgresDataSourceConfiguration {

    private final Environment environment;

    @Bean
    @Qualifier("postgresDataSource")
    public DataSource postgresDataSource() {
        return new HikariDataSource(postgresHikariConfig());
    }

    @Bean
    @Qualifier("postgresHikariConfig")
    @ConfigurationProperties("postgres-db.datasource.hikari")
    public HikariConfig postgresHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(environment.getProperty("postgres-db.datasource.username"));
        hikariConfig.setPassword(environment.getProperty("postgres-db.datasource.password"));
        hikariConfig.setJdbcUrl(environment.getProperty("postgres-db.datasource.url"));
        return hikariConfig;
    }

    @Bean
    @Qualifier("postgresFlyway")
    @ConfigurationProperties(prefix = "postgres-db.flyway")
    public Flyway postgresFlyway(@Value("${postgres-db.flyway.url}") String url,
                                 @Value("${postgres-db.flyway.user}") String user,
                                 @Value("${postgres-db.flyway.password}") String password,
                                 @Value("${postgres-db.flyway.locations}") String locations,
                                 @Value("${postgres-db.flyway.baseline-on-migrate}") Boolean baselineOnMigrate) {
        return Flyway.configure()
            .dataSource(url, user, password)
            .locations(locations)
            .baselineOnMigrate(baselineOnMigrate)
            .load();
    }

}

