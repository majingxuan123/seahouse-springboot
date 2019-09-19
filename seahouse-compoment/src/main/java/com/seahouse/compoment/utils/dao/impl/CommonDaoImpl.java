package com.seahouse.compoment.utils.dao.impl;

import com.seahouse.compoment.config.jdbcconfig.DataSourceConfig;
import com.seahouse.compoment.config.jdbcconfig.hibernate.HibernateTemplateConfig;
import com.seahouse.compoment.utils.dao.CommonDao;
import com.seahouse.compoment.utils.dao.po.StoredFunctionPO;
import com.seahouse.compoment.utils.dao.po.StoredProcedurePO;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.ParameterMode;
import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

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
public class CommonDaoImpl extends HibernateDaoSupport implements CommonDao {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource(
            name = "jdbcTemplate"
    )
    private JdbcTemplate jdbcTemplate;

    public CommonDaoImpl() {
    }

    @Resource(
            name = "sessionFactory"
    )
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public void saveEntity(Serializable entity) {
        this.getHibernateTemplate().save(entity);
    }
    @Override
    public void saveOrUpdateEntity(Serializable entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }
    @Override
    public void deleteEntity(Serializable entity) {
        this.getHibernateTemplate().delete(entity);
    }
    @Override
    public void deleteEntity(Serializable entity, LockMode lockMode) {
        this.getHibernateTemplate().delete(entity, lockMode);
    }
    @Override
    public void deleteEntityAll(Collection<? extends Serializable> entities) {
        this.getHibernateTemplate().deleteAll(entities);
    }
    @Override
    public void updateEntity(Serializable entity) {
        this.getHibernateTemplate().update(entity);
    }
    @Override
    public void updateEntity(Serializable entity, LockMode lockMode) {
        this.getHibernateTemplate().update(entity, lockMode);
    }
    @Override
    public <T> T loadEntity(Class<T> entityClass, Serializable id) {
        return this.getHibernateTemplate().load(entityClass, id);
    }
    @Override
    public <T> T loadEntity(Class<T> entityClass, Serializable id, LockMode lockMode) {
        return this.getHibernateTemplate().load(entityClass, id, lockMode);
    }
    @Override
    public <T> T getEntity(Class<T> entityClass, Serializable id) {
        return this.getHibernateTemplate().get(entityClass, id);
    }
    @Override
    public <T> T getEntity(Class<T> entityClass, Serializable id, LockMode lockMode) {
        return this.getHibernateTemplate().get(entityClass, id, lockMode);
    }
    @Override
    public <T> T getEntityBySQL(String sql, Class<T> clazz) {
        List<T> list = this.queryForList(sql, clazz);
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                throw new RuntimeException("单条查询返回多条记录");
            } else {
                return list.get(0);
            }
        } else {
            return null;
        }
    }
    @Override
    public <T> T getEntityBySQL(String sql, Class<T> clazz, Object... objs) {
        List<T> list = this.queryForList(sql, clazz, objs);
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                throw new RuntimeException("单条查询返回多条记录");
            } else {
                return list.get(0);
            }
        } else {
            return null;
        }
    }
//    @Override
//    public <T> List<T> getDataList(DetachedCriteria criteria) {
//        return this.getHibernateTemplate().findByCriteria(criteria);
//    }
    @Override
    public void flush() {
        this.getHibernateTemplate().flush();
    }
    @Override
    public <T> List<T> queryForList(String sql, Class<T> classType) {
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(classType));
    }
    @Override
    public <T> List<T> queryForList(String sql, Class<T> classType, Object... objs) {
        new ArrayList();
        List<T> list = this.jdbcTemplate.query(sql, objs, new BeanPropertyRowMapper(classType));
        return list;
    }
    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        return this.jdbcTemplate.queryForList(sql);
    }
    @Override
    public List<Map<String, Object>> queryForList(String sql, Object... objs) {
        return this.jdbcTemplate.queryForList(sql, objs);
    }
    @Override
    public int queryForInt(String sql) {
        Integer integer = (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class);
        return integer == null ? 0 : integer;
    }
    @Override
    public int queryForInt(String sql, Object... objs) {
        Integer integer = (Integer)this.jdbcTemplate.queryForObject(sql, objs, Integer.class);
        return integer == null ? 0 : integer;
    }
    @Override
    public SqlRowSet queryForRowSet(String sql) {
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        return rs;
    }
    @Override
    public SqlRowSet queryForRowSet(String sql, Object... objs) {
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql, objs);
        return rs;
    }
    @Override
    public <T extends Serializable> void lock(T t, LockMode lockMode) {
        this.getHibernateTemplate().lock(t, lockMode);
    }
    @Override
    public Map<String, Object> queryForMap(String sql) {
        return this.jdbcTemplate.queryForMap(sql);
    }
    @Override
    public Map<String, Object> queryForMap(String sql, Object... objs) {
        return this.jdbcTemplate.queryForMap(sql, objs);
    }
    @Override
    public int[] batchUpdate(String... sqls) {
        int[] successFlags = this.jdbcTemplate.batchUpdate(sqls);
        return successFlags;
    }
    @Override
    public int update(String sql, Object... objs) {
        return this.jdbcTemplate.update(sql, objs);
    }
    @Override
    public void callProcedure(StoredProcedurePO storedProcedurePO) {
        ProcedureCall procedureCall = this.currentSession().createStoredProcedureCall(storedProcedurePO.getProcedureName());
        Map<String, Integer> outputsNamesAndPositions = new LinkedHashMap(4);
        List<StoredProcedurePO.Request> requests = storedProcedurePO.getRequestParams();
        int i = 1;

        for(int length = requests.size(); i <= length; ++i) {
            StoredProcedurePO.Request request = requests.get(i - 1);
            boolean isInOrInOutParameter = request.getParameterMode().equals(ParameterMode.IN) || request.getParameterMode().equals(ParameterMode.INOUT);
            if (isInOrInOutParameter) {
                procedureCall.registerParameter(i, request.getValue().getClass(), request.getParameterMode());
                procedureCall.setParameter(i, request.getValue());
                if (request.getParameterMode().equals(ParameterMode.INOUT)) {
                    outputsNamesAndPositions.put(request.getOutParamName(), i);
                }
            } else {
                procedureCall.registerParameter(i, request.getParamClass(), request.getParameterMode());
                outputsNamesAndPositions.put(request.getOutParamName(), i);
            }
        }

        ProcedureOutputs outputs = procedureCall.getOutputs();
        boolean hasResultList = procedureCall.execute();

        ArrayList list;
        for(list = new ArrayList(16); hasResultList; hasResultList = procedureCall.hasMoreResults()) {
            list.addAll(procedureCall.getResultList());
        }

        Map<String, Object> outputsMap = new HashMap(4);
        Iterator var9 = outputsNamesAndPositions.entrySet().iterator();

        while(var9.hasNext()) {
            Map.Entry<String, Integer> stringIntegerEntry = (Map.Entry)var9.next();
            outputsMap.put(stringIntegerEntry.getKey(), outputs.getOutputParameterValue((Integer)stringIntegerEntry.getValue()));
        }

        storedProcedurePO.setOutPuts(outputsMap);
        storedProcedurePO.setResultList(list);
    }
    @Override
    public void callFunction(StoredFunctionPO storedFunctionPO) {
        this.currentSession().doWork((connection) -> {
            try {
                StringBuilder functionName = new StringBuilder(storedFunctionPO.getFunctionName() + "(");
                int i = 0;

                for(int length = storedFunctionPO.getRequestParams().size(); i < length; ++i) {
                    if (i != length - 1) {
                        functionName.append("?,");
                    } else {
                        functionName.append("?");
                    }
                }

                functionName.append(")");
                String callSql = "{? = call " + functionName.toString() + "}";
                this.log.info("调用存储函数：" + callSql);
                CallableStatement call = connection.prepareCall(callSql);
                call.registerOutParameter(1, storedFunctionPO.getReturnType());
                List<StoredFunctionPO.Request> requestParams = storedFunctionPO.getRequestParams();
                Map<String, Integer> outputsNamesAndPositions = new LinkedHashMap(4);
                int ix = 0;

                int outPosition;
                for(int lengthx = requestParams.size(); ix < lengthx; ++ix) {
                    StoredFunctionPO.Request request = (StoredFunctionPO.Request)requestParams.get(ix);
                    outPosition = ix + 2;
                    if (request.getParamType() == 0) {
                        this.setInParamValue(call, outPosition, request.getValue());
                    } else if (request.getParamType() == 1) {
                        this.setInParamValue(call, outPosition, request.getValue());
                        call.registerOutParameter(outPosition, request.getOracleTypes());
                        outputsNamesAndPositions.put(request.getOutParamName(), ix);
                    } else {
                        if (request.getParamType() != 2) {
                            throw new SQLException("错误的参数类型");
                        }

                        call.registerOutParameter(outPosition, request.getOracleTypes());
                        outputsNamesAndPositions.put(request.getOutParamName(), ix);
                    }
                }

                call.execute();
                if (storedFunctionPO.getReturnType() == -10) {
                    ResultSet rsx = (ResultSet)call.getObject(1);
                    storedFunctionPO.setReturnResult(this.resultSetToListMap(rsx));
                } else {
                    storedFunctionPO.setReturnResult(call.getObject(1));
                }

                Map<String, Object> outputsMap = new HashMap();
                Iterator var21 = outputsNamesAndPositions.entrySet().iterator();

                while(var21.hasNext()) {
                    Map.Entry<String, Integer> stringIntegerEntry = (Map.Entry)var21.next();
                    outPosition = (Integer)stringIntegerEntry.getValue();
                    String outputName = (String)stringIntegerEntry.getKey();
                    int callOutPosition = outPosition + 2;
                    if (((StoredFunctionPO.Request)storedFunctionPO.getRequestParams().get(outPosition)).getOracleTypes() == -10) {
                        ResultSet rs = (ResultSet)call.getObject(callOutPosition);
                        List<Map<String, Object>> maps = this.resultSetToListMap(rs);
                        outputsMap.put(outputName, maps);
                    } else {
                        outputsMap.put(outputName, call.getObject(callOutPosition));
                    }
                }

                storedFunctionPO.setOutPuts(outputsMap);
            } catch (SQLException var16) {
                var16.printStackTrace();
            }

        });
    }

    private List<Map<String, Object>> resultSetToListMap(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        String[] columnNames = new String[rsMetaData.getColumnCount()];

        for(int i = 0; i < columnNames.length; ++i) {
            columnNames[i] = rsMetaData.getColumnName(i + 1);
        }

        ArrayList maps = new ArrayList(10);

        while(rs.next()) {
            Map<String, Object> map = new HashMap(4);
            String[] var6 = columnNames;
            int var7 = columnNames.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String columnName = var6[var8];
                map.put(columnName, rs.getObject(columnName));
            }

            maps.add(map);
        }

        rs.close();
        return maps;
    }

    private void setInParamValue(CallableStatement call, int index, Object value) throws SQLException {
        Class<?> clazz = value.getClass();
        if (String.class.equals(clazz)) {
            call.setString(index, (String)value);
        } else if (!Byte.TYPE.equals(clazz) && !Byte.class.equals(clazz)) {
            if (!Character.TYPE.equals(clazz) && !Character.class.equals(clazz)) {
                if (!Integer.TYPE.equals(clazz) && !Integer.class.equals(clazz)) {
                    if (!Short.TYPE.equals(clazz) && !Short.class.equals(clazz)) {
                        if (!Long.TYPE.equals(clazz) && !Long.class.equals(clazz)) {
                            if (!Float.TYPE.equals(clazz) && !Float.class.equals(clazz)) {
                                if (!Double.TYPE.equals(clazz) && !Double.class.equals(clazz)) {
                                    if (!Boolean.TYPE.equals(clazz) && !Boolean.class.equals(clazz)) {
                                        if (Date.class.equals(clazz)) {
                                            Date date = (Date)value;
                                            call.setDate(index, new java.sql.Date(date.getTime()));
                                        } else if (java.sql.Date.class.equals(clazz)) {
                                            call.setDate(index, (java.sql.Date)value);
                                        } else if (Time.class.equals(clazz)) {
                                            call.setTime(index, (Time)value);
                                        } else if (Timestamp.class.equals(clazz)) {
                                            call.setTimestamp(index, (Timestamp)value);
                                        } else if (Blob.class.equals(clazz)) {
                                            call.setBlob(index, (Blob)value);
                                        } else if (Clob.class.equals(clazz)) {
                                            call.setClob(index, (Clob)value);
                                        } else if (byte[].class.equals(clazz)) {
                                            call.setBytes(index, (byte[])((byte[])value));
                                        } else if (BigDecimal.class.equals(clazz)) {
                                            call.setBigDecimal(index, (BigDecimal)value);
                                        } else if (InputStream.class.equals(clazz)) {
                                            call.setBinaryStream(index, (InputStream)value);
                                        } else {
                                            call.setObject(index, value);
                                        }
                                    } else {
                                        call.setBoolean(index, (Boolean)value);
                                    }
                                } else {
                                    call.setDouble(index, (Double)value);
                                }
                            } else {
                                call.setFloat(index, (Float)value);
                            }
                        } else {
                            call.setLong(index, (Long)value);
                        }
                    } else {
                        call.setShort(index, (Short)value);
                    }
                } else {
                    call.setInt(index, (Integer)value);
                }
            } else {
                call.setString(index, value.toString());
            }
        } else {
            call.setByte(index, (Byte)value);
        }

    }
    @Override
    public String getSequence(String sequenceName) {
        String sql = String.format("SELECT %s.NEXTVAL FROM DUAL", sequenceName);
        return (String)this.jdbcTemplate.queryForObject(sql, String.class);
    }

    @Override
    public String getLSequence(String sequenceName, int length) {
        String sql = String.format("SELECT LPAD(%s.NEXTVAL,%d,0) FROM DUAL", sequenceName, length);
        return (String)this.jdbcTemplate.queryForObject(sql, String.class);
    }
    @Override
    public String getRSequence(String sequenceName, int length) {
        String sql = String.format("SELECT RPAD(%s.NEXTVAL,%d,0) FROM DUAL", sequenceName, length);
        return (String)this.jdbcTemplate.queryForObject(sql, String.class);
    }
    @Override
    public String getValueFromTab(String field, String table, String where) {
        String sql = "select " + field + " from " + table + " where 1=1 ";
        if (!"".equals(where)) {
            sql = sql + " and " + where;
        }

        return (String)this.jdbcTemplate.queryForObject(sql, String.class);
    }
    @Override
    public Session getCurrentSession() {
        return this.currentSession();
    }

}
