package com.seahouse.compoment.utils.javabeantool;

import com.seahouse.compoment.utils.javabeantool.createentitybean.CommonColumns;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称: JavabeanTool</li>
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
public class JavabeanTool {

    public static final String[] IGNORE_PROPS = new String[]{"", ""};

    private static Logger log = LogManager.getLogger(UnderlyingCode.class.getName());

    /**
     * 传入两个实体类
     * 输出 两个类中有或没有的字段
     * <p>
     * 在使用copyproperties 之前  可以用这个方法 判断还有那些属性需要自己手动插入
     *
     * @param T1
     * @param T2
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void checkTableField(Class T1, Class T2) throws IllegalAccessException, InstantiationException {

        Map<String, String> map1 = new HashMap<String, String>();

        Map<String, String> map2 = new HashMap<String, String>();
        //表1字段
        Field[] field = T1.newInstance().getClass().getDeclaredFields();
        //表2字段
        Field[] field2 = T2.newInstance().getClass().getDeclaredFields();
        for (Field field1 : field) {
            map1.put(field1.getName(), "1");
        }
        for (Field field1 : field2) {
            if (StringUtils.isEmpty(map1.get(field1.getName()))) {
                map2.put(field1.getName(), "1");
            }
        }
        //这里是表2有而表1没有
        System.out.println("---------这里是" + T2.getSimpleName() + "有而" + T1.getSimpleName() + "没有的字段  保存数据库时候要自己加入这两个字段-------------");
        for (String s : map2.keySet()) {
            System.out.println("key : " + s + " value : " + map2.get(s));
        }

        Map<String, String> map3 = new HashMap<String, String>();

        Map<String, String> map4 = new HashMap<String, String>();

        for (Field field1 : field2) {
            map3.put(field1.getName(), "1");
        }

        for (Field field1 : field) {
            if (StringUtils.isEmpty(map3.get(field1.getName()))) {
                map4.put(field1.getName(), "1");
            }

        }

        System.out.println("--------------over----------------");
        //这里是表2有而表1没有
        System.out.println("---------这里是" + T1.getSimpleName() + "有而" + T2.getSimpleName() + "没有    保存数据库时要加入这些字段-------------");
        for (String s : map4.keySet()) {
            System.out.println("key : " + s + " value : " + map4.get(s));
        }

    }


    /*
        还有一个三个入参的 cpyproperties

        第三个参数为想要忽略的属性名
        String [] ignorePerams = {"aac006"};
        BeanTools.copyProperties(yyAc01DTO, zrHis21InDTO, ignorePerams);
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }


    /**
     * 生成注解实体类
     * <p>
     * 如果发现类型错误
     * <p>
     * 数据库中主键是number的时候  实体类  应该为int
     * <p>
     * 数据库中为long   实体类也应该为long
     *
     * @param tableName 表名
     * @param path      生成路径
     * @throws SQLException SQL异常
     */
    public static void createAnnotationBeanFile(String tableName, String path) throws SQLException {
        log.info("开始执行创建方法");
        // 对表名进行处理
        tableName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1).toLowerCase();
        log.info("表名处理");
        // 对于有下划线的表名，去掉下划线并将下划线之后第一个字母大写
        char[] c = null;
        String newTableName = tableName;
        char[] tag = new char[]{'_', '-', '—'};
        for (char e : tag) {
            if (newTableName.indexOf(e) != -1) {
                c = newTableName.toCharArray();
                for (int i = 0; i < c.length; i++) {
                    if (i != 0 && c[i - 1] == e) {
                        if (c[i] == e){
                            continue;
                        }
                        newTableName = newTableName + ("" + c[i]).toUpperCase();
                    } else {
                        if (c[i] == e){
                            continue;
                        }
                        if (i == 0) {
                            newTableName = ("" + c[i]).toUpperCase();
                        } else {
                            newTableName += c[i];
                        }
                    }
                }
            }
        }
        log.info("获取所有的列及其其他属性");
        // 获取所有的列及其其他属性
        StringBuffer _sb1 = new StringBuffer();
        _sb1.append("SELECT A.COLUMN_NAME COLUMNNAME,");
        _sb1.append("B.DATA_TYPE DATATYPE,");
        _sb1.append("B.DATA_LENGTH DATALENGTH,");
        _sb1.append("B.NULLABLE NULLABLE,");
        _sb1.append("B.DATA_PRECISION DATAPRECISION,");
        _sb1.append("B.DATA_SCALE DATASCALE,");
        _sb1.append("A.COMMENTS COMMENTS ");
        _sb1.append("FROM USER_COL_COMMENTS A, USER_TAB_COLUMNS B ");
        _sb1.append("WHERE A.TABLE_NAME = B.TABLE_NAME ");
        _sb1.append("AND A.COLUMN_NAME = B.COLUMN_NAME ");
        _sb1.append("AND A.TABLE_NAME = '" + tableName.toUpperCase() + "' ");
        _sb1.append("ORDER BY B.COLUMN_ID ASC ");
        List<CommonColumns> _list = UnderlyingCode.queryForListBean(_sb1.toString(), CommonColumns.class);
        if (_list.size() < 1) {
            throw new SQLException("表或视图" + tableName + "不存在");
        }
        log.info("获取主键及其其他属性");
        // 获取主键及其其他属性
        StringBuffer _sb2 = new StringBuffer();
        _sb2.append("SELECT A.COLUMN_NAME COLUMNNAME,");
        _sb2.append("A.DATA_LENGTH DATALENGTH,");
        _sb2.append("A.DATA_TYPE DATATYPE,");
        _sb2.append("A.DATA_PRECISION DATAPRECISION,");
        _sb2.append("A.DATA_SCALE DATASCALE,");
        _sb2.append("A.NULLABLE NULLABLE,");
        _sb2.append("B.COMMENTS COMMENTS ");
        _sb2.append("FROM USER_TAB_COLUMNS  A, ");
        _sb2.append("USER_COL_COMMENTS B, ");
        _sb2.append("USER_CONS_COLUMNS C, ");
        _sb2.append("USER_CONSTRAINTS  D ");
        _sb2.append("WHERE A.TABLE_NAME = B.TABLE_NAME ");
        _sb2.append("AND B.TABLE_NAME = C.TABLE_NAME ");
        _sb2.append("AND C.TABLE_NAME = D.TABLE_NAME ");
        _sb2.append("AND A.COLUMN_NAME = B.COLUMN_NAME ");
        _sb2.append("AND B.COLUMN_NAME = C.COLUMN_NAME ");
        _sb2.append("AND C.CONSTRAINT_NAME = D.CONSTRAINT_NAME ");
        _sb2.append("AND D.CONSTRAINT_TYPE = 'P' ");
        _sb2.append("AND A.TABLE_NAME = '" + tableName.toUpperCase() + "' ");
        List<CommonColumns> _list2 = UnderlyingCode.queryForListBean(_sb2.toString(), CommonColumns.class);
        CommonColumns columnKey = null;// 单一主键
        if (_list2.size() == 1) {
            columnKey = UnderlyingCode.queryForListBean(_sb2.toString(), CommonColumns.class).get(0);
        }
        /**********************************************************************************************/
        log.info("导入需要的包");
        StringBuffer sb = new StringBuffer();
        sb.append("package " + path + ";\n\n");

        boolean date = false;
        boolean blob = false;
        // boolean clob=false;
        // 判断是否有日期格式
        for (CommonColumns e : _list) {
            if ("date".equalsIgnoreCase(e.getDatatype())) {
                if (date){
                    continue;
                }
                sb.append("import java.util.Date;\n");
                sb.append("import javax.persistence.Temporal;\n");
                sb.append("import javax.persistence.TemporalType;\n");
                date = true;
            }
            if ("blob".equalsIgnoreCase(e.getDatatype())) {
                if (blob){
                    continue;
                }
                sb.append("import java.sql.Blob;\n");
                blob = true;
            }
            // if("clob".equalsIgnoreCase(e.getDatatype())){
            // if(clob) continue;
            // sb.append("import java.sql.Clob;\n");
            // clob=true;
            // }
        }
        // 注解必须的导包
        sb.append("import javax.persistence.Column;\n");
        sb.append("import javax.persistence.Entity;\n");
        if (columnKey != null || _list2.size() > 1) {
            sb.append("import javax.persistence.Id;\n");
        }
        if (_list2.size() > 1) {
            sb.append("import javax.persistence.IdClass;\n");
        }
        sb.append("import javax.persistence.Table;\n");
        // 判断表是否有主键，表有主键才加这些注解
        if (columnKey != null) {
            sb.append("import javax.persistence.GeneratedValue;\n");
            sb.append("import javax.persistence.GenerationType;\n");
            // 判断主键类型，导入相应的包
            if ("number".equalsIgnoreCase(columnKey.getDatatype())) {
                sb.append("import javax.persistence.SequenceGenerator;\n");
            }
            if ("varchar2".equalsIgnoreCase(columnKey.getDatatype())) {
                sb.append("import org.hibernate.annotations.GenericGenerator;\n");
                sb.append("import org.hibernate.annotations.Parameter;\n");
            }
        }
        sb.append("\n");
        // 格式化日期
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        sb.append("/**\n * " + tableName + ".实体类\n * 创建时间：" + df.format(new Date()) + "\n */\n\n");
        // 类必须的注解
        sb.append("@SuppressWarnings(\"serial\")\n");
        sb.append("@Entity\n");
        sb.append("@Table(name = \"" + tableName.toUpperCase() + "\")\n");
        if (_list2.size() > 1) {
            sb.append("@IdClass(value=" + newTableName + ".class)\n");
        }
        sb.append("public class " + newTableName + " implements java.io.Serializable {\n\n");
        log.info("添加字段");
        // 添加所有字段
        for (CommonColumns row : _list) {
            String typeName = "String";
            if ("number".equalsIgnoreCase(row.getDatatype())) {
                if (row.getDatascale() != null && row.getDatascale() > 0) {
                    typeName = "Double";
                } else {
                    typeName = "Long";
                }
            }
            if ("date".equalsIgnoreCase(row.getDatatype())) {
                typeName = "Date";
            }
            if ("blob".equalsIgnoreCase(row.getDatatype())) {
                typeName = "Blob";
            }
            // if("clob".equalsIgnoreCase(row.getDatatype())){
            // typeName="Clob";
            // }
            // 字段名全部小写
            sb.append("	private " + typeName + " " + row.getColumnname().toLowerCase() + ";");
            if (row.getComments() != null) {
                sb.append(" //" + row.getComments() + "\n");
            }
        }
        sb.append("\n");
        log.info("生成get/set方法");
        // 生成get/set方法
        // 主键优先处理
        if (columnKey != null) {
            String keyName = columnKey.getColumnname().substring(0, 1).toUpperCase() + columnKey.getColumnname().substring(1).toLowerCase();
            // 判断主键类型，生成不同的ID注解，这边只判断主键类型为number和varchar2
            if (columnKey.getDatatype() != null) {
                if ("number".equalsIgnoreCase(columnKey.getDatatype())) {
                    sb.append("	@Id\n");
                    sb.append("	@SequenceGenerator(name = \"SEQ_" + keyName.toUpperCase() + "\",sequenceName=\"SEQ_" + keyName.toUpperCase() + "\",allocationSize=1)\n");
                    sb.append("	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"SEQ_" + keyName.toUpperCase() + "\")\n");
                    sb.append("	@Column(name = \"" + keyName.toUpperCase() + "\", unique = true");
                }
                if ("varchar2".equalsIgnoreCase(columnKey.getDatatype())) {
                    sb.append("	@Id\n");
                    sb.append("	@GenericGenerator(name = \"SEQ_" + keyName.toUpperCase() + "\", strategy =\"sequence\", parameters = {\n");
                    sb.append("	@Parameter(name = \"sequence\", value = \"SEQ_" + keyName.toUpperCase() + "\")})\n");
                    sb.append("	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"SEQ_" + keyName.toUpperCase() + "\")\n");
                    sb.append("	@Column(name = \"" + keyName.toUpperCase() + "\", unique = true");
                }
            }
            String typeName = "String";
            // 判断字段是否允许值为空
            if (columnKey.getNullable() != null) {
                // 可以为空
                if ("y".equalsIgnoreCase(columnKey.getNullable())) {
                    sb.append(", nullable = true");
                    // 不能为空
                } else {
                    sb.append(", nullable = false");
                }
            }
            // 判断主键类型
            // number类型有double和long类型，double类型有精度和小数，long没有小数
            if ("number".equalsIgnoreCase(columnKey.getDatatype())) {
                // 判断数据精度
                if (columnKey.getDataprecision() != null) {
                    sb.append(", precision = " + columnKey.getDataprecision());
                    sb.append(", scale = " + columnKey.getDatascale());
                }
                // 判断小数位数
                if (columnKey.getDatascale() != null && columnKey.getDatascale() > 0) {
                    typeName = "Double";
                } else {
                    typeName = "Long";
                }
            }
            // varchar2类型只有length没有precision和scale
            if ("varchar2".equalsIgnoreCase(columnKey.getDatatype())) {
                if (columnKey.getDatalength() != null) {
                    sb.append(", length = " + columnKey.getDatalength());
                }
            }
            sb.append(")\n");
            // 生成主键的get/set
            sb.append("	public " + typeName + " get" + keyName + "() {\n").append("		return this." + keyName.toLowerCase() + ";\n").append("	}\n\n");
            ;
            sb.append("	public void set" + keyName + "(" + typeName + " " + keyName.toLowerCase() + ") {\n").append("		this." + keyName.toLowerCase() + " = " + keyName.toLowerCase() + ";\n").append("	}\n\n");
        }
        if (_list2.size() > 1) {
            for (CommonColumns e : _list2) {
                String keyName = e.getColumnname().substring(0, 1).toUpperCase() + e.getColumnname().substring(1).toLowerCase();
                // 判断主键类型，生成不同的ID注解，这边只判断主键类型为number和varchar2
                if (e.getDatatype() != null) {
                    sb.append("	@Id\n");
                    sb.append("	@Column(name = \"" + keyName.toUpperCase() + "\", unique = true");
                }
                String typeName = "String";
                // 判断字段是否允许值为空
                if (e.getNullable() != null) {
                    // 可以为空
                    if ("Y".equals(e.getNullable().toUpperCase())) {
                        sb.append(", nullable = true");
                        // 不能为空
                    } else {
                        sb.append(", nullable = false");
                    }
                }
                // 判断主键类型
                // number类型有double和long类型，double类型有精度和小数，long没有小数
                if ("number".equalsIgnoreCase(e.getDatatype())) {
                    // 判断数据精度
                    if (e.getDataprecision() != null) {
                        sb.append(", precision = " + e.getDataprecision());
                        sb.append(", scale = " + e.getDatascale());
                    }
                    // 判断小数位数
                    if (e.getDatascale() != null && e.getDatascale() > 0) {
                        typeName = "Double";
                    } else {
                        typeName = "Long";
                    }
                }
                // varchar2类型只有length没有precision和scale
                if ("varchar2".equalsIgnoreCase(e.getDatatype())) {
                    if (e.getDatalength() != null) {
                        sb.append(", length = " + e.getDatalength());
                    }
                }
                sb.append(")\n");
                // 生成主键的get/set
                sb.append("	public " + typeName + " get" + keyName + "() {\n").append("		return this." + keyName.toLowerCase() + ";\n").append("	}\n\n");
                ;
                sb.append("	public void set" + keyName + "(" + typeName + " " + keyName.toLowerCase() + ") {\n").append("		this." + keyName.toLowerCase() + " = " + keyName.toLowerCase() + ";\n").append("	}\n\n");
            }
        }

        // 生成其他字段的get/set方法
        for (CommonColumns row : _list) {
            // 主键已经处理过了，跳过
            if (columnKey != null && row.getColumnname().equals(columnKey.getColumnname())) {
                continue;
            }
            if (_list2.size() > 1) {
                boolean flag = false;
                for (CommonColumns e : _list2) {
                    if (e.getColumnname().equals(row.getColumnname())) {
                        flag = true;
                    }
                }
                if (flag) {
                    continue;
                }
            }
            // 列名首字母大写，其他字母小写
            String columnName = row.getColumnname().substring(0, 1).toUpperCase() + row.getColumnname().substring(1).toLowerCase();
            // data类型添加timestamp注解
            if ("date".equalsIgnoreCase(row.getDatatype())) {
                sb.append("	@Temporal(TemporalType.TIMESTAMP)\n");
            }
            // 添加列注解
            sb.append("	@Column(name = \"" + columnName.toUpperCase() + "\"");
            if (row.getNullable() != null) {
                sb.append(", nullable = " + ("y".equalsIgnoreCase(row.getNullable()) ? "true" : "false"));
            }
            // 添加列注解其他属性
            if ("Double".equalsIgnoreCase(row.getDatatype())) {
                if (row.getDataprecision() != null) {
                    sb.append(", precision = " + row.getDataprecision());

                }
                if (row.getDatascale() != null) {
                    sb.append(", scale = " + row.getDatascale());
                }
            } else {
                if (row.getDatalength() != null) {
                    sb.append(", length = " + row.getDatalength());
                }
            }
            sb.append(")\n");
            String typeName = "String";
            // 判断字段类型添加对应方法的属性
            if ("number".equalsIgnoreCase(row.getDatatype())) {
                if (row.getDatascale() != null && row.getDatascale() > 0) {
                    typeName = "Double";
                } else {
                    typeName = "Long";
                }
            }
            if ("date".equalsIgnoreCase(row.getDatatype())) {
                typeName = "Date";
            }
            if ("blob".equalsIgnoreCase(row.getDatatype())) {
                typeName = "Blob";
            }
            // if("clob".equalsIgnoreCase(row.getDatatype())){
            // typeName="Clob";
            // }
            sb.append("	public " + typeName + " get" + columnName + "() {\n").append("		return this." + columnName.toLowerCase() + ";\n").append("	}\n\n");
            ;
            sb.append("	public void set" + columnName + "(" + typeName + " " + columnName.toLowerCase() + ") {\n").append("		this." + columnName.toLowerCase() + " = " + columnName.toLowerCase() + ";\n").append("	}\n\n");
        }
        sb.append("}");
        log.info("处理完成，开始生成文件");
        try {
            log.info("处理路径");
            // 处理路径
            String projectPath = System.getProperty("user.dir").replace("\\", "/") + "/src/main/java/" + path.replace(".", "/") + "/" + newTableName + ".java";
            File file = new File(projectPath);
            file.mkdirs();
            // 如果文件存在，则删除
            if (file.exists()) {
                file.delete();
            }
            log.info("创建文件");
            // 创建新的文件
            file.createNewFile();
            log.info("创建文件完成：" + newTableName + ".java");
            OutputStream os = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(sb.toString());
            bw.flush();
            bw.close();
            log.info("执行创建方法结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("生成注解实体类完成！");
    }


}
