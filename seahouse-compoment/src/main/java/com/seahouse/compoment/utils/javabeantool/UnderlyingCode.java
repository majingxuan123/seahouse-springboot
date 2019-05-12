package com.seahouse.compoment.utils.javabeantool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 用于生成实体类
 */
public class UnderlyingCode {

    private static Logger log = LogManager.getLogger(UnderlyingCode.class.getName());

    private static String _driverClass;//驱动名
    private static String _url;//连接地址
    private static String _user;//用户名
    private static String _password;//密码

    /**
     * 使用静态块加载配置文件设置参数
     */
    static {
        //加载jdbc配置文件
        InputStream _is = Object.class.getResourceAsStream("/c3p0.properties");
        Properties _pro = new Properties();
        try {
            //properties加载输入流
            _pro.load(_is);
        } catch (IOException e) {
            log.info("加载jdbc.properties失败！" + e.getMessage());
        }
        _driverClass = _pro.getProperty("c3p0.driverClass");
        _url = _pro.getProperty("c3p0.jdbcUrl");
        _user = _pro.getProperty("c3p0.user");
        _password = _pro.getProperty("c3p0.password");
    }

    /**
     * 获取SQL连接
     *
     * @return 数据库连接
     * @throws ClassNotFoundException 类未找到异常
     * @throws SQLException           SQL异常
     */
    public static Connection getConnection() {
        Connection _conn = null;
        try {
            Class.forName(_driverClass);
            _conn = DriverManager.getConnection(_url, _user, _password);
        } catch (ClassNotFoundException e) {
            log.info("类未找到异常！" + e.getMessage());
        } catch (SQLException e) {
            log.info("SQL异常！" + e.getMessage());
        }
        return _conn;
    }

    /**
     * 关闭SQL连接
     *
     * @param _conn 要关闭的连接
     * @throws SQLException SQL异常
     */
    public static void closeConnection(Connection _conn) throws SQLException {
        if (_conn != null) {
            _conn.close();
        }
        ;
    }

    /**
     * 关闭所有连接
     *
     * @param _conn 数据库连接
     * @param _stat stat对象
     * @param _rs   结果集对象
     */
    public static void closeAll(Connection _conn, Statement _stat, ResultSet _rs) {
        try {
            if (_conn != null) {
                _conn.close();
            }
            if (_stat != null) {
                _stat.close();
            }
            if (_rs != null) {
                _rs.close();
            }
        } catch (SQLException e) {
            log.info("SQL异常！" + e.getMessage());
        }
    }

    public static <T> List<T> queryForListBean(String sql, Class<T> clazz) {
        Connection _conn = null;
        PreparedStatement _ps = null;
        ResultSet _rs = null;
        ResultSetMetaData _rsmd = null;
        _conn = getConnection();
        List<T> _list = new ArrayList<T>();
        try {
            _ps = _conn.prepareStatement(sql);
            _rs = _ps.executeQuery();
            _rsmd = _rs.getMetaData();
            int columnCount = _rsmd.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = _rsmd.getColumnName(i);
            }
            while (_rs.next()) {
                T t = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field e : fields) {
                    for (String s : columnNames) {
                        if (s != null && s.toLowerCase().equals(e.getName().toLowerCase())) {
                            setProperties(t, s.toLowerCase(), _rs.getObject(s.toLowerCase()));
                        }
                    }
                }
                _list.add(t);
            }
            return _list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            closeAll(_conn, _ps, _rs);
        }
        return null;
    }

    public static <T> void setProperties(T t, String propertyName, Object value) {
        try {
            PropertyDescriptor _pd = new PropertyDescriptor(propertyName, t.getClass());
            Method _m = _pd.getWriteMethod();
            if (_m.getParameterTypes()[0].equals(Byte.class) || _m.getParameterTypes()[0].equals(byte.class)) {
                if (value != null && value.toString().length() > 0) {
                    value = Byte.parseByte(value.toString());
                }
            } else if (_m.getParameterTypes()[0].equals(Integer.class) || _m.getParameterTypes()[0].equals(int.class)) {
                if (value != null && value.toString().length() > 0) {
                    value = Integer.parseInt(value.toString());
                }
            } else if (_m.getParameterTypes()[0].equals(Short.class) || _m.getParameterTypes()[0].equals(short.class)) {
                if (value != null && value.toString().length() > 0) {
                    value = Short.parseShort(value.toString());
                }
            } else if (_m.getParameterTypes()[0].equals(Long.class) || _m.getParameterTypes()[0].equals(long.class)) {
                if (value != null && value.toString().length() > 0) {
                    value = Long.parseLong(value.toString());
                }
            } else if (_m.getParameterTypes()[0].equals(Float.class) || _m.getParameterTypes()[0].equals(float.class)) {
                if (value != null && value.toString().length() > 0) {
                    value = Float.parseFloat(value.toString());
                }
            } else if (_m.getParameterTypes()[0].equals(Double.class) || _m.getParameterTypes()[0].equals(double.class)) {
                if (value != null && value.toString().length() > 0) {
                    value = Double.parseDouble(value.toString());
                }
            } else if (_m.getParameterTypes()[0].equals(Boolean.class) || _m.getParameterTypes()[0].equals(boolean.class)) {
                if (value != null && value.toString().length() > 0) {
                    value = Boolean.parseBoolean(value.toString());
                }
            } else {
                if (value != null && value.toString().length() > 0) {
                    value = value.toString();
                }
            }
            _m.invoke(t, value);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
