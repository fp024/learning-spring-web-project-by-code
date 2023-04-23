package org.fp024.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(
    basePackages = {"org.fp024.service", "org.fp024.task", "org.fp024.repository.querydsl"})
@EnableJpaRepositories(basePackages = "org.fp024.repository.jpa")
@PropertySource({"classpath:database.properties"})
@EnableScheduling
@EnableTransactionManagement
@Import(QuerydslConfig.class)
public class RootConfig {

  @Bean(destroyMethod = "close")
  public HikariDataSource dataSource(
      @Value("${jdbc.driver}") String driverClassName,
      @Value("${jdbc.url}") String url,
      @Value("${jdbc.username}") String userName,
      @Value("${jdbc.password}") String password) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(driverClassName);
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setUsername(userName);
    hikariConfig.setPassword(password);

    return new HikariDataSource(hikariConfig);
  }
  // 메서드 이름을 getObject로 생성될 타입이름으로 정의하는 것이 깔끔하겠다.
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

    LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();

    emfBean.setPackagesToScan("org.fp024.domain", "org.fp024.enumconverter");
    emfBean.setDataSource(dataSource);
    emfBean.setPersistenceUnitName("my_board");
    // emfBean에다 프로퍼티 설정도 할 수 있는데, 이 부분은 persistence.xml 의 프로퍼티 설정을 XML에다 두는게 편한 것 같다.

    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

    hibernateJpaVendorAdapter.setDatabase(Database.HSQL);
    // 하이버네이트의 Dialect 클래스를 사용하고 싶으면 아래 클래스로 사용.
    // hibernateJpaVendorAdapter.setDatabasePlatform(org.hibernate.dialect.HSQLDialect.class.getCanonicalName());
    hibernateJpaVendorAdapter.setShowSql(true);
    emfBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

    Properties jpaProps = new Properties();

    jpaProps.put("hibernate.format_sql", "true");
    jpaProps.put("hibernate.hbm2ddl.charset_name", "UTF-8");

    jpaProps.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");

    jpaProps.put("jakarta.persistence.schema-generation.drop-source", "script");
    jpaProps.put(
        "jakarta.persistence.schema-generation.drop-script-source", "sql/hsqldb/init-drop-ddl.sql");

    jpaProps.put("jakarta.persistence.schema-generation.create-source", "script");
    jpaProps.put(
        "jakarta.persistence.schema-generation.create-script-source",
        "sql/hsqldb/init-create-ddl.sql");

    jpaProps.put("jakarta.persistence.sql-load-script-source", "sql/hsqldb/init-data-insert.sql");

    jpaProps.put(
        "hibernate.hbm2ddl.import_files_sql_extractor",
        "org.hibernate.tool.schema.internal.script.MultiLineSqlScriptExtractor");

    jpaProps.put(
        "hibernate.physical_naming_strategy",
        "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

    emfBean.setJpaProperties(jpaProps);
    return emfBean;
  }

  // entityManagerFactory 파라미터에 대해 IntelliJ 경고가 노출되는데..
  // `자동 주입을 할 수 없습니다. 'EntityManagerFactory' 타입의 bean을 찾을 수 없습니다.`라고 나온다.
  // entityManagerFactoryBean()에서 entityManagerFactory에 대한 팩토리 빈을 정의했기 때문에.. 문제가 없는 부분 일 텐데,
  // IntelliJ IDE 에서 이 점이 파악이 안되어서 경고가 노출되는 것 같다.
  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }
}
