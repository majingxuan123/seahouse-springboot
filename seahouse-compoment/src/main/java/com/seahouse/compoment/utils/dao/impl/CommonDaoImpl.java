package com.seahouse.compoment.utils.dao.impl;

import com.seahouse.compoment.config.jdbcconfig.DataSourceConfig;
import com.seahouse.compoment.config.jdbcconfig.hibernate.HibernateTemplateConfig;
import com.seahouse.compoment.utils.dao.CommonDao;
import com.seahouse.compoment.utils.javabeantool.RowMapUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * <ul>
 * <li>文件名称: CommonDaoImpl</li>
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
@Component
@ConditionalOnClass({JdbcTemplate.class,DataSource.class, HibernateTemplateConfig.class, DataSourceConfig.class})
public class CommonDaoImpl implements CommonDao {
    /**
     * hibernate
     */
    @Resource
    private HibernateTemplate hibernateTemplate;
    @Resource
    private JdbcTemplate jdbcTemplate;
    /**
     * 新增实体类
     *
     * @param t
     * @return
     */
    @Override
    public <T extends Serializable> T addEntity(T t) {
        hibernateTemplate.save(t);
        return t;
    }

    /**
     * 修改实体类
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateEntity(Serializable entity) {
        boolean flag = false;
        hibernateTemplate.update(entity);
        flag = true;
        return flag;
    }


    /**
     * 根据主键获取实体类
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    @Override
    public <T> T getEntityByid(Class<T> clazz, Serializable id) {
        return hibernateTemplate.get(clazz, id);
    }


    /**
     * 删除实体类
     *
     * @param entity
     * @return
     */
    @Override
    public boolean delEntity(Serializable entity) {
        try {
            hibernateTemplate.delete(entity);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * @param seq
     * @return
     */
    @Override
    public String getSequence(String seq) {
        Object obj = null;
        List list = this.jdbcTemplate.queryForList("SELECT " + seq + ".NEXTVAL AS SEQ FROM DUAL");
        if (list.size() > 0) {
            LinkedCaseInsensitiveMap linkedCaseInsensitiveMap = (LinkedCaseInsensitiveMap) list.get(0);
            obj = linkedCaseInsensitiveMap.get("SEQ");
            obj = String.valueOf(obj);
        }
        return (String) obj;
    }

    /**
     * @param sql
     * @param className
     * @param <T>
     * @return
     */
    @Override
    public <T> T getEntityBySql(String sql, Class<T> className) {
        List<T> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(className));
        if (list != null && list.size() != 0) {
            if (list.size() == 1) {
                return list.get(0);
            } else {
                throw new RuntimeException("返回多条记录");
            }
        } else {
            return null;
        }
    }

    /**
     * @param sql
     * @param classType
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> queryForListBySql(String sql, Class<T> classType) {
        new ArrayList();
        List<T> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(classType));
        return list;
    }

    /**
     * 通过SQL   update操作
     *
     * @param sql
     * @return
     */
    @Override
    public int updateBySql(String sql) {
        int[] ints = jdbcTemplate.batchUpdate(sql);
        return ints[0];
    }

    public <R> List<R> queryToDTO(String sql, final Class<R> clazz) {
        return this.jdbcTemplate.query(sql.toString(), new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                return RowMapUtil.mapRow(rs, clazz);
            }
        });
    }

    /**
     * 获取DOT
     *
     * 定义一个DTO   有A表和B表的字段
     *
     * 直接通过这个来查询可以
     *
     * @param sql
     * @param className
     * @param <T>
     * @return
     */
    @Override
    public <T> T getDtoEntityBySql(String sql, Class<T> className) {
        List<T> list = this.queryToDTO(sql, className);
        if (list != null && list.size() != 0) {
            if (list.size() == 1) {
                return list.get(0);
            } else {
                throw new RuntimeException("返回多条记录");
            }
        } else {
            return null;
        }
    }


}
