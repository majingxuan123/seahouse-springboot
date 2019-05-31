package com.seahouse.compoment.config.jdbcconfig.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.annotation.Resource;

/**
 * <ul>
 * <li>文件名称: HibernateTemplateConfig</li>
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
@AutoConfigureAfter(SessionFactory.class)
public class HibernateTemplateConfig {

    @ConditionalOnMissingBean
    @Bean("hibernateTemplate")
    public HibernateTemplate hibernateTemplate (SessionFactory sessionFactory){
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        hibernateTemplate.setSessionFactory(sessionFactory);
        return hibernateTemplate;
    }



}
