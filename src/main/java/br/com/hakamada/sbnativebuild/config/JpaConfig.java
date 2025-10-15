package br.com.hakamada.sbnativebuild.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = JpaConfig.BASE_PACKAGES_REPOSITORY)
@EntityScan(basePackages = JpaConfig.BASE_PACKAGES_ENTITY)
public class JpaConfig {

    public static final String BASE_PACKAGES_REPOSITORY = "br.com.hakamada.domain.repository";
    public static final String BASE_PACKAGES_ENTITY = "br.com.hakamada.domain.entity";


    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(getEnv("JDBC_URL"));
        config.setUsername(getEnv("DB_USERNAME"));
        config.setPassword(getEnv("DB_PASSWORD"));
        config.setConnectionTestQuery("SELECT 1");

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(2000); // 2 segundos timeout para abrir conexões
        config.setIdleTimeout(60000); // 60 segundos idle timeout
        config.setMaxLifetime(1800000);
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(BASE_PACKAGES_ENTITY);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private String getEnv(String key) {
        String value = System.getenv(key);
        if (value == null) {
            throw new RuntimeException("Environment variable '" + key + "' is not set");
        }
        return value;
    }
}