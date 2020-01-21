package com.seahouse;

import com.seahouse.compoment.utils.dao.CommonDao;
import com.seahouse.compoment.utils.javabeantool.JavabeanTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeahouseCompomentApplicationTests {

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
