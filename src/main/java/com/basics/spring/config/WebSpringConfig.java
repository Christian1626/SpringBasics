package com.basics.spring.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.basics.controller.WebController;
import com.basics.controller.WsController;
import com.basics.service.UserService;
import com.basics.service.UserServiceImpl;

@Configuration
@Import({WebSecurityConfig.class})
@EnableTransactionManagement
@EnableWebMvc
public class WebSpringConfig extends WebMvcConfigurerAdapter{

	private static final String DATABASE_DRIVER="com.mysql.jdbc.Driver";

	//========================================
	// Permet d'indiquer où chercher les vues
	//========================================
	@Bean
	public ViewResolver configureViewResolver() {
		InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
		viewResolve.setPrefix("/WEB-INF/views/");
		viewResolve.setSuffix(".jsp");

		return viewResolve;
	}

	//==================================================================================
	// Charge les messages internationalisés dans les fichiers messages_xx_.properties
	//==================================================================================
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();
		msgSource.setBasename("classpath:messages");
		msgSource.setDefaultEncoding("UTF-8");
		return msgSource;
	}
	
	//========================================
	// Controllers
	//========================================
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
		//Spring security: gestion du login
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        //autres
        registry.addViewController("/web/admin").setViewName("administration");
    }

	//===============================================
	// Permet d'indiquer où chercher les ressources
	//===============================================
	//	@Override
	//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
	//	}

	//=================
	// Fichier de conf
	//=================
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setLocation(new ClassPathResource("parameters.properties"));
		return propertySourcesPlaceholderConfigurer;
	}

	@Bean
	public DbConfig dbConfig() {
		return new DbConfig();
	}

	//=============================
	// Base de données MYSQL
	//=============================

	@Bean
	public DataSource dataSource() {
		DbConfig dbConfig = dbConfig();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DATABASE_DRIVER);
		dataSource.setUrl(dbConfig.getDatabaseUrl());
		dataSource.setUsername(dbConfig.getUsername());
		dataSource.setPassword(dbConfig.getPassword());

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(new String[] { "com.basics.model" });
		entityManagerFactoryBean.setJpaProperties(additionalProperties());
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		return entityManagerFactoryBean;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}

	//===============================
	// Declaration des beans normals
	//===============================
	@Bean
	public WebController webController() {
		return new WebController();
	}

	@Bean
	public WsController wsController() {
		return new WsController();
	}

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
}

