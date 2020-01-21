package com.seahouse;

import com.seahouse.compoment.rabbitmq.bean.Order_RabbitMQ;
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
public class TestRabbitMQ {

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


    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void testProducter() {
        rabbitAdmin.declareExchange(new DirectExchange("springboottest.direct", true
                , true));
        rabbitAdmin.declareExchange(new TopicExchange("springboottest.topic", true
                , true));
        rabbitAdmin.declareExchange(new FanoutExchange("springboottest.fanout", true
                , true));


        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));




        rabbitAdmin.declareBinding(new Binding("test.direct.queue",
                Binding.DestinationType.QUEUE, "springboottest.direct",
                "direct", null));

        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("test.topic.queue", false))//创建队列
                .to(new TopicExchange("springboottest.topic", false, false))//创建路由
                .with("user.#"));//创建routingkey


        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("test.fanout.queue", false))//创建队列
                .to(new FanoutExchange("springboottest.fanout", false, false))//创建路由
        );

    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() {
        MessageProperties messageProperties = new MessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        headers.put("type","自定义消息测试");

        Message message = new Message("传送的body数据".getBytes(),messageProperties);

        rabbitTemplate.convertAndSend("springboot-topic001", "rabbit.study.*", message,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("添加额外设置");
                message.getMessageProperties().getHeaders().put("desc","额外设置");
                return message;
            }
        });

    }

    /**
     *
     */
    @Test
    public void testSendMessage2() {
        //这里的参数在业务中应该是应该全局唯一的代码  用于发送消息成功后确认
        CorrelationData correlationData = new CorrelationData("123");
        rabbitTemplate.convertAndSend("springboottest.direct", "direct", "这是传送的body数据",correlationData);
    }

    /**
     *
     */
    @Test
    public void testSendMessageAnnotation() {
        //这里的参数在业务中应该是应该全局唯一的代码  用于发送消息成功后确认
        CorrelationData correlationData = new CorrelationData("123");
        rabbitTemplate.convertAndSend("annotation-exchange", "annotation-key", "用于测试annotation",correlationData);
    }

    /**
     *
     */
    @Test
    public void testSendMessageBean() {
        Order_RabbitMQ order_rabbitMQ = new Order_RabbitMQ();
        order_rabbitMQ.setDesc("测试传送bean");
        order_rabbitMQ.setId("987456");

        //这里的参数在业务中应该是应该全局唯一的代码  用于发送消息成功后确认
        CorrelationData correlationData = new CorrelationData("123654789");
        rabbitTemplate.convertAndSend("annotation-bean-exchange", "annotation-bean-key", order_rabbitMQ,correlationData);
    }

}
