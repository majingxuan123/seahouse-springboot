package com.seahouse.compoment.utils.dao;

import java.io.Serializable;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: CommonDao</li>
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
public interface CommonDao {


    /**
     * 新增实体类
     *
     * @param t
     * @return
     */
    public <T extends Serializable> T addEntity(T t);

    /**
     * 修改实体类
     *
     * @param entity
     * @return
     */
    public boolean updateEntity(Serializable entity);


    /**
     * 根据主键获取实体类
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    public <T> T getEntityByid(Class<T> clazz, Serializable id);
    /**
     * 删除实体类
     *
     * @param entity
     * @return
     */
    public boolean delEntity(Serializable entity);

    /**
     * @param seq
     * @return
     */
    public String getSequence(String seq);

    /**
     * @param sql
     * @param className
     * @param <T>
     * @return
     */
    public <T> T getEntityBySql(String sql, Class<T> className);

    /**
     * @param sql
     * @param classType
     * @param <T>
     * @return
     */
    public <T> List<T> queryForListBySql(String sql, Class<T> classType);

    /**
     * 通过SQL   update操作
     *
     * @param sql
     * @return
     */
    public int updateBySql(String sql);

    /**
     * 获取DOT
     * <p>
     * 定义一个DTO   有A表和B表的字段
     * <p>
     * 直接通过这个来查询可以
     *
     * @param sql
     * @param className
     * @param <T>
     * @return
     */
    public <T> T getDtoEntityBySql(String sql, Class<T> className);


}
