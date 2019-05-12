package com.seahouse.compoment.utils.javabeantool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <ul>
 * <li>文件名称: RowMapUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/4/28 0028</li>
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
public class RowMapUtil {


    static Log log = LogFactory.getLog(RowMapUtil.class);


    public RowMapUtil() {
    }
    public static Object mapRow(ResultSet rs, Class className) throws SQLException {
        Object obj = null;

        try {
            obj = className.newInstance();
            invoke(obj, rs);
        } catch (InstantiationException var4) {
            var4.printStackTrace();
        } catch (IllegalAccessException var5) {
            var5.printStackTrace();
        }

        return obj;
    }

    public static Method[] getMethods(Class clazz, String colName) {
        Method[] methods = new Method[2];
        StringBuffer getter = new StringBuffer(colName.length() + 3);
        StringBuffer setter = new StringBuffer(colName.length() + 3);
        getter.append("get");
        setter.append("set");
        getter.append(Character.toUpperCase(colName.charAt(0)));
        setter.append(Character.toUpperCase(colName.charAt(0)));
        getter.append(colName.substring(1));
        setter.append(colName.substring(1));
        Method[] allMyMethods = clazz.getMethods();
        int count = 0;
        Method[] var10 = allMyMethods;
        int var9 = allMyMethods.length;

        for (int var8 = 0; var8 < var9; ++var8) {
            Method method = var10[var8];
            if (Modifier.isPublic(method.getModifiers())) {
                if (method.getName().equals(setter.toString())) {
                    methods[0] = method;
                    ++count;
                } else if (method.getName().equals(getter.toString())) {
                    methods[1] = method;
                    ++count;
                }

                if (count == 2) {
                    break;
                }
            }
        }

        return methods;
    }

    public static void invoke(Object dto, ResultSet rs) {
        Class clazz = dto.getClass();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();

            for (int i = 0; i < colCount; ++i) {
                String colName = rsmd.getColumnName(i + 1).toLowerCase();
                Method[] methods = getMethods(clazz, colName);
                Method setter = methods[0];
                Method getter = methods[1];
                if (setter == null) {
                    if (log.isDebugEnabled()) {
                        log.debug(String.format("not setter of property %s", colName));
                    }
                } else if (getter == null) {
                    if (log.isDebugEnabled()) {
                        log.debug(String.format("not getter of property %s", colName));
                    }
                } else {
                    String type = getter.getReturnType().getName();
                    Object value = null;
                    if (!"short".equalsIgnoreCase(type) && !"java.lang.Short".equalsIgnoreCase(type)) {
                        if (!"int".equalsIgnoreCase(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                            if (!"long".equalsIgnoreCase(type) && !"java.lang.Long".equalsIgnoreCase(type)) {
                                if (!"float".equalsIgnoreCase(type) && !"java.lang.Float".equalsIgnoreCase(type)) {
                                    if (!"double".equalsIgnoreCase(type) && !"java.lang.Double".equalsIgnoreCase(type)) {
                                        if (!"boolean".equalsIgnoreCase(type) && !"java.lang.Boolean".equalsIgnoreCase(type)) {
                                            if ("java.math.BigDecimal".equalsIgnoreCase(type)) {
                                                value = rs.getBigDecimal(colName);
                                                if (rs.wasNull()) {
                                                    value = null;
                                                }
                                            } else if ("java.lang.String".equalsIgnoreCase(type)) {
                                                value = rs.getString(colName);
                                                if (rs.wasNull()) {
                                                    value = null;
                                                }
                                            } else if ("java.sql.Blob".equalsIgnoreCase(type)) {
                                                value = rs.getBlob(colName);
                                            } else if ("java.sql.Clob".equalsIgnoreCase(type)) {
                                                value = rs.getClob(colName);
                                            } else if ("java.sql.Date".equalsIgnoreCase(type)) {
                                                value = rs.getDate(colName);
                                            } else if ("java.sql.Timestamp".equalsIgnoreCase(type)) {
                                                value = rs.getTimestamp(colName);
                                            } else if ("java.sql.Time".equalsIgnoreCase(type)) {
                                                value = rs.getTime(colName);
                                            } else {
                                                Timestamp date;
                                                if ("java.util.Date".equalsIgnoreCase(type)) {
                                                    date = rs.getTimestamp(colName);
                                                    if (date != null) {
                                                        value = new Date(date.getTime());
                                                    }
                                                } else if ("java.util.Timestamp".equalsIgnoreCase(type)) {
                                                    date = rs.getTimestamp(colName);
                                                    if (date != null) {
                                                        value = new Date(date.getTime());
                                                    }
                                                }
                                                //此处抛弃time类型的实体类
                                                    /*else if ("java.util.Time".equalsIgnoreCase(type)) {
                                                         date = (Timestamp) rs.getTime(colName);
                                                        if (date != null) {
                                                            value = new Date(date.getTime());
                                                        }
                                                    }*/
                                                else if ("java.io.InputStream".equalsIgnoreCase(type)) {
                                                    value = null;
                                                } else if ("char".equalsIgnoreCase(type)) {
                                                    String str = rs.getString(colName);
                                                    value = str.charAt(0);
                                                }
                                            }
                                        } else {
                                            value = rs.getBoolean(colName);
                                            if (rs.wasNull()) {
                                                if ("boolean".equalsIgnoreCase(type)) {
                                                    value = rs.getBoolean(colName);
                                                } else {
                                                    value = null;
                                                }
                                            }
                                        }
                                    } else {
                                        value = rs.getDouble(colName);
                                        if (rs.wasNull()) {
                                            if ("double".equalsIgnoreCase(type)) {
                                                value = rs.getDouble(colName);
                                            } else {
                                                value = null;
                                            }
                                        }
                                    }
                                } else {
                                    value = rs.getFloat(colName);
                                    if (rs.wasNull()) {
                                        if ("float".equalsIgnoreCase(type)) {
                                            value = rs.getFloat(colName);
                                        } else {
                                            value = null;
                                        }
                                    }
                                }
                            } else {
                                value = rs.getLong(colName);
                                if (rs.wasNull()) {
                                    if ("long".equalsIgnoreCase(type)) {
                                        value = rs.getLong(colName);
                                    } else {
                                        value = null;
                                    }
                                }
                            }
                        } else {
                            value = rs.getInt(colName);
                            if (rs.wasNull()) {
                                if ("int".equalsIgnoreCase(type)) {
                                    value = rs.getInt(colName);
                                } else {
                                    value = null;
                                }
                            }
                        }
                    } else {
                        value = rs.getShort(colName);
                        if (rs.wasNull()) {
                            if ("short".equalsIgnoreCase(type)) {
                                value = rs.getShort(colName);
                            } else {
                                value = null;
                            }
                        }
                    }

                    try {
                        setter.invoke(dto, value);
                    } catch (Exception var13) {
                        throw new RuntimeException(String.format("invoke error[method:%s]", setter.getName()), var13);
                    }
                }
            }
        } catch (SQLException var14) {
            throw new RuntimeException(String.format("SQL异常:%s", var14.getMessage()), var14);
        }
    }
}


