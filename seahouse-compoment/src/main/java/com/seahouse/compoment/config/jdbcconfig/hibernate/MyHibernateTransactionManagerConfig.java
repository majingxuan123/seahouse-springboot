package com.seahouse.compoment.config.jdbcconfig.hibernate;

import com.seahouse.compoment.config.jdbcconfig.DataSourceConfig;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * <ul>

 当需要使用事务的时候  在类或方法上加入@Transactional
 *
 * @author majx
 * @version 1.0.0
 */
//@Configuration
//@ConditionalOnClass({DataSource.class,DataSourceConfig.class, HibernateSessionFactoryConfig.class})
//@ConditionalOnBean({DataSource.class,SessionFactory.class})
//@AutoConfigureAfter(HibernateSessionFactoryConfig.class)
public class MyHibernateTransactionManagerConfig {

//    @Bean
//    @ConditionalOnMissingBean
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory,DataSource dataSource) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        hibernateTransactionManager.setDataSource(dataSource);
        return hibernateTransactionManager;
    }


}
