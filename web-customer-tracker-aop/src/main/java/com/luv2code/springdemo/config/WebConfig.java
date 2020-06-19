package com.luv2code.springdemo.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.luv2code.springdemo")
@PropertySource("classpath:persistence-mysql.properties")
public class WebConfig extends WebMvcConfigurationSupport {

	@Autowired
	private Environment env;

	private Logger logger = Logger.getLogger(getClass().getName());
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver view = new InternalResourceViewResolver();
		view.setPrefix("/WEB-INF/view/");
		view.setSuffix(".jsp");
		return view;
	}

	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setPackagesToScan("com.luv2code.springdemo");

		return sessionFactory;
	}

	@Bean("dataSource")
	public DataSource dataSource() {

		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		logger.info("URL web config : " + env.getProperty("jdbc.url"));
		dataSource.setInitialPoolSize(
				getPropertyAsInt("connection.pool.initialPoolSize"));
		dataSource.setMinPoolSize(
				getPropertyAsInt("connection.pool.minPoolSize"));
		dataSource.setMaxPoolSize(
				getPropertyAsInt("connection.pool.maxPoolSize"));
		dataSource.setMaxIdleTime(
				getPropertyAsInt("connection.pool.maxIdleTime"));

		return dataSource;
	}

	private Properties hibernateProperties() {

		Properties hibernatePpt = new Properties();
		hibernatePpt.setProperty("hibernate.dialect",
				env.getProperty("hibernate.dialect"));
		hibernatePpt.setProperty("hibernate.show_sql",
				env.getProperty("hibernate.show_sql"));
		hibernatePpt.setProperty("hibernate.packagesToScan",
				env.getProperty("hibernate.packagesToScan"));

		return hibernatePpt;
	}

	private int getPropertyAsInt(String key) {
		return Integer.parseInt(env.getProperty(key));
	}

	@Bean(name = "transactionManager")
	@Qualifier("sessionFactory")
	public HibernateTransactionManager transactionManager(
			SessionFactory sessionFactory) {

		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
	}

}
