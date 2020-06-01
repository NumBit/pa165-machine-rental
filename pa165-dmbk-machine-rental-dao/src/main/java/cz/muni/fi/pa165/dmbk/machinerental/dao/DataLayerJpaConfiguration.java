package cz.muni.fi.pa165.dmbk.machinerental.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
@Sql(value = "classpath:data.sql")
@EnableJpaRepositories(basePackages = {"cz.muni.fi.pa165.dmbk.machinerental.dao"},
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        transactionManagerRef = "jpaTransactionManager")
public class DataLayerJpaConfiguration {

    @Primary
    @Bean(name = "jpaDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "jpaDataSource")
    @ConfigurationProperties("spring.datasource.configuration")
    public DataSource dataSource(@Qualifier("jpaDataSourceProperties")
                                         DataSourceProperties jpaDataSourceProperties) {
        return jpaDataSourceProperties.initializeDataSourceBuilder()
                .driverClassName(jpaDataSourceProperties.getDriverClassName())
                .username(jpaDataSourceProperties.getUsername())
                .password(jpaDataSourceProperties.getPassword())
                .url(jpaDataSourceProperties.getUrl())
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "jpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("jpaDataSource") DataSource jpaDataSource) {
        return builder
                .dataSource(jpaDataSource)
                .packages("cz.muni.fi.pa165.dmbk.machinerental.dao")
                .persistenceUnit("jpaPersistenceUnit")
                .properties(additionalProperties())
                .build();
    }

    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("jpaEntityManagerFactory")
                    EntityManagerFactory jpaEntityManagerFactory) {
        return new JpaTransactionManager(jpaEntityManagerFactory);
    }

    @Bean(name = "jpaDataInit")
    @DependsOn("jpaTransactionManager")
    public Object jpaDataInit(@Qualifier("jpaDataSource") DataSource dataSource) {
        LoggerFactory.getLogger(this.getClass()).info("loading initial DB data");
        Resource initData = new ClassPathResource("init-data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        LoggerFactory.getLogger(this.getClass()).info("initial DB data loaded");
        return new Object();
    }

    Map<String, Object> additionalProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }
}
