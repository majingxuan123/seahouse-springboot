package com.seahouse.compoment.config.jdbcconfig;

import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * <ul>
 * <li>文件名称: MyHibernateTemplateConfig</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/5/12</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
@Configuration
public class SessionFactoryConfig {

    @Resource
    private DataSource dataSource;

    @Bean("sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("com.seahouse.domain.entity");
        Properties hibernateProperties = new Properties();
        //数据库的方言
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        //hibernate默认采用JDBC连接，如果用连接池这里要设置为FALSE
        hibernateProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        return localSessionFactoryBean;
    }


}
