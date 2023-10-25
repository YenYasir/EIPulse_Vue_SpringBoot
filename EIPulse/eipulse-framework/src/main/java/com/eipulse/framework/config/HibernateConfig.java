package com.eipulse.framework.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * 配置LocalSessionFactoryBean
 * 
 * @author eipulse
 */
@Configuration
public class HibernateConfig {

	@Resource
	private Environment environment;

	@Resource
	private DataSource dataSource;

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean() throws SQLException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean
				.setPackagesToScan(new String[] { "com.eipulse.*.domain", "com.eipulse.common.core.domain.entity" });// dao和entity的公共包
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		return sessionFactoryBean;
	}

	// 獲取hibernate配置
	private Properties hibernateProperties() throws SQLException {
		System.out.println("dataSource=" + dataSource.getConnection().getMetaData().getDatabaseProductName());
		Properties properties = new Properties();
		properties.setProperty("hibernate.current_session_context_class",
				environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
		properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.show-sql",
				environment.getProperty("spring.jpa.properties.hibernate.show-sql"));
		properties.setProperty("hibernate.cache.use_second_level_cache",
				environment.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
		properties.setProperty("hibernate.cache.use_query_cache",
				environment.getProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));
		return properties;
	}
	/**
	 * @Bean public HibernateTransactionManager transactionManager() throws
	 *       SQLException { HibernateTransactionManager transactionManager = new
	 *       HibernateTransactionManager();
	 *       transactionManager.setDataSource(dataSource);
	 *       transactionManager.setSessionFactory(sessionFactoryBean().getObject());
	 *       // 註入sessionFactory
	 *       transactionManager.setHibernateManagedSession(true); // 獲取當前session
	 *       transactionManager.setValidateExistingTransaction(true); // 開啟事務校驗
	 *       transactionManager.setRollbackOnCommitFailure(true);
	 *       transactionManager.setAutodetectDataSource(true);
	 *       transactionManager.setGlobalRollbackOnParticipationFailure(true);
	 * 
	 *       return transactionManager; }
	 * @Bean public TransactionInterceptor transactionInterceptors() throws
	 *       Exception { TransactionInterceptor transInterceptor = new
	 *       TransactionInterceptor();
	 *       transInterceptor.setTransactionManager(transactionManager());
	 *       Properties props = new Properties(); props.setProperty("save*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("update*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("delete*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("find*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("get*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("load*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("add*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("execute*",
	 *       "PROPAGATION_REQUIRED"); props.setProperty("merge*",
	 *       "PROPAGATION_REQUIRED");
	 *       transInterceptor.setTransactionAttributes(props);
	 * 
	 *       return transInterceptor;
	 * 
	 *       }
	 * 
	 * @Bean public BeanNameAutoProxyCreator beanNameAutoProxyCreator() throws
	 *       Exception { BeanNameAutoProxyCreator beanNameAutoProxyCreator = new
	 *       BeanNameAutoProxyCreator();
	 *       beanNameAutoProxyCreator.setBeanNames("*Service");
	 *       beanNameAutoProxyCreator.setInterceptorNames("transactionInterceptor");
	 * 
	 *       return beanNameAutoProxyCreator; }
	 */

}
