package com.seahouse.compoment.utils.dao;

import com.seahouse.compoment.utils.dao.po.StoredFunctionPO;
import com.seahouse.compoment.utils.dao.po.StoredProcedurePO;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    HibernateTemplate getHibernateTemplate();

    void saveEntity(Serializable var1);

    void saveOrUpdateEntity(Serializable var1);

    void deleteEntity(Serializable var1);

    void deleteEntity(Serializable var1, LockMode var2);

    void deleteEntityAll(Collection<? extends Serializable> var1);

    void updateEntity(Serializable var1);

    void updateEntity(Serializable var1, LockMode var2);

    <T> T loadEntity(Class<T> var1, Serializable var2);

    <T> T loadEntity(Class<T> var1, Serializable var2, LockMode var3);

    <T> T getEntity(Class<T> var1, Serializable var2);

    <T> T getEntity(Class<T> var1, Serializable var2, LockMode var3);

    <T> T getEntityBySQL(String var1, Class<T> var2);

    <T> T getEntityBySQL(String var1, Class<T> var2, Object... var3);

//    <T> List<T> getDataList(DetachedCriteria var1);

    void flush();

    <T> List<T> queryForList(String var1, Class<T> var2);

    <T> List<T> queryForList(String var1, Class<T> var2, Object... var3);

    List<Map<String, Object>> queryForList(String var1);

    List<Map<String, Object>> queryForList(String var1, Object... var2);

    int queryForInt(String var1);

    int queryForInt(String var1, Object... var2);

    SqlRowSet queryForRowSet(String var1);

    SqlRowSet queryForRowSet(String var1, Object... var2);

    <T extends Serializable> void lock(T var1, LockMode var2);

    Map<String, Object> queryForMap(String var1);

    Map<String, Object> queryForMap(String var1, Object... var2);

    int[] batchUpdate(String... var1);

    int update(String var1, Object... var2);

    void callProcedure(StoredProcedurePO var1);

    void callFunction(StoredFunctionPO var1);

    String getSequence(String var1);

    String getLSequence(String var1, int var2);

    String getRSequence(String var1, int var2);

    String getValueFromTab(String var1, String var2, String var3);

    Session getCurrentSession();


}
