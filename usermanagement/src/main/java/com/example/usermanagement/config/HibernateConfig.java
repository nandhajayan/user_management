package com.example.usermanagement.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class HibernateConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Bean
	protected JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	protected LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
			CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {

		Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
		jpaPropertiesMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaPropertiesMap.put("hibernate.connection.maxPoolSize", 500);
		jpaPropertiesMap.put("hibernate.connection.minPoolSize", 100);
		jpaPropertiesMap.put("hibernate.jdbc.batch_size", 300);
		jpaPropertiesMap.put("hibernate.order_inserts", "true");
		jpaPropertiesMap.put("hibernate.order_updates", "true");
		jpaPropertiesMap.put("hibernate.show_sql", false);
		jpaPropertiesMap.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
		jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
		jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSource);
		localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.usermanagement*");
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(this.jpaVendorAdapter());
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaPropertiesMap);

		return localContainerEntityManagerFactoryBean;

	}
}
