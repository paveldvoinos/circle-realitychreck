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
public class MariaDataSourceConfiguration {

    private final Environment environment;

    @Bean
    @Qualifier("mariaDataSource")
    public DataSource mariaDataSource() {
        return new HikariDataSource(mariaHikariConfig());
    }

    @Bean
    @Qualifier("mariaHikariConfig")
    @ConfigurationProperties("maria-db.datasource.hikari")
    public HikariConfig mariaHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(environment.getProperty("maria-db.datasource.username"));
        hikariConfig.setPassword(environment.getProperty("maria-db.datasource.password"));
        hikariConfig.setJdbcUrl(environment.getProperty("maria-db.datasource.url"));
        return hikariConfig;
    }

    @Bean
    @Qualifier("mariaFlyway")
    @ConfigurationProperties(prefix = "maria-db.flyway")
    public Flyway mariaFlyway(@Value("${maria-db.flyway.url}") String url,
                              @Value("${maria-db.flyway.user}") String user,
                              @Value("${maria-db.flyway.password}") String password,
                              @Value("${maria-db.flyway.locations}") String locations,
                              @Value("${maria-db.flyway.baseline-on-migrate}") Boolean baselineOnMigrate) {
        return Flyway.configure()
            .dataSource(url, user, password)
            .locations(locations)
            .baselineOnMigrate(baselineOnMigrate)
            .load();
    }
}

