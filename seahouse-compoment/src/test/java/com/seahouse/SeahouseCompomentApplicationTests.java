package com.seahouse;

import com.seahouse.compoment.utils.dao.CommonDao;
import com.seahouse.compoment.utils.javabeantool.JavabeanTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeahouseCompomentApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Resource
    private CommonDao commonDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 生成hibernate实体类
     *
     * @throws SQLException
     */
    @Test
    public void createAnnotationBeanFile() throws SQLException {
        //数据库中表名字
        //生成实体类位置
        JavabeanTool.createAnnotationBeanFile("T_USER", "com.seahouse.domain.entity");
    }


}
