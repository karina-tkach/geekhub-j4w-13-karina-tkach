package org.geekhub.encryption.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:/application-dev.properties")
    static class Development {
    }

    @Configuration
    @Profile("prod")
    @PropertySource("classpath:/application-prod.properties")
    static class Production {
    }

    @Bean
    public DataSource datasource(@Value("${spring.datasource.url}") String jdbcUrl,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${spring.datasource.driver-class-name}") String driverClassName) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource,
                         @Value("${spring.flyway.baselineOnMigrate}") boolean baselineOnMigrate,
                         @Value("${spring.flyway.locations}") String[] locations) {
        return Flyway.configure()
            .dataSource(dataSource)
            .baselineOnMigrate(baselineOnMigrate)
            .locations(locations)
            .load();
    }


    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
