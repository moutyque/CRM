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
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = "com.luv2code.springdemo")
@PropertySource("classpath:security-persistence-mysql.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment env;
	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private UserDetailsService userService;

	@Bean(name = "securitySessioFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(securityDataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setPackagesToScan("com.luv2code.springdemo");

		return sessionFactory;
	}

	@Bean(name = "securityDataSource")
	public DataSource securityDataSource() {

		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setJdbcUrl(env.getProperty("jdbc.security.url"));
		logger.info("URL security config : " + env.getProperty("jdbc.url"));

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

	private int getPropertyAsInt(String key) {
		return Integer.parseInt(env.getProperty(key));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {

		auth.jdbcAuthentication().dataSource(securityDataSource());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/home", "/createUser")
				.permitAll().anyRequest().authenticated()
				.antMatchers("/customer/**").hasRole("EMPLOYE")
				.antMatchers("/leaders/**").hasRole("MANAGER") // Peut acc�der �
																// /leaders si
																// role MANAGER
				.antMatchers("/systems/**").hasRole("ADMIN") // Peut acc�der �
																// /systems si
																// role
																// ADMIN.and()
				.and().formLogin().loginPage("/showMyLoginPage") // Acces � la
																	// page de
																	// login
				.loginProcessingUrl("/authentificateTheUser") // URL � appeler
																// par la page
																// de login si
																// la connexion
																// est ok
				.permitAll().and().logout().permitAll()// Tout le monde peut se
														// deconnecter
				.and().exceptionHandling().accessDeniedPage("/access-denied");// Si
																				// une
																				// erruer
																				// survient
																				// car
																				// pas
																				// d'acces
																				// �
																				// la
																				// page
																				// renvoy�
																				// vers
																				// la
																				// page
																				// sp�cifique
																				// }
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

	@Bean(name = "securtiyTransactionManager")
	@Qualifier("securitySessioFactory")
	public HibernateTransactionManager transactionManager(
			SessionFactory sessionFactory) {

		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

}
