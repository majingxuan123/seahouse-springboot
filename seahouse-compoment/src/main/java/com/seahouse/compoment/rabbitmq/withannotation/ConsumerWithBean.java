package com.seahouse.compoment.rabbitmq.withannotation;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.seahouse.compoment.rabbitmq.bean.Order_RabbitMQ;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <ul>
 * <li>文件名称：ConsumerWithBean</li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2019</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/1/13 21:02</li>
 * </ul>
 *
 * <ul>
 * <li>修改记录：</li>
 * <li>版 本 号：</li>
 * <li>修改日期：</li>
 * <li>修 改 人：</li>
 * <li>修改内容：</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
//@Component
public class ConsumerWithBean {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "annotation-bean-queue"),
            exchange = @Exchange(value = "annotation-bean-exchange",type = "topic"),
            key = "annotation-bean-key"))
    @RabbitHandler
    public void onMessage(@Payload Order_RabbitMQ order_rabbitMQ, Channel channel,
                          @Headers Map<String,Object> headers) throws IOException {

        System.err.println("消费者接收信息-----desc:" + order_rabbitMQ.getDesc());
        System.err.println("消费者接收信息-----id:" + order_rabbitMQ.getId());
        Long delivery_tag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //ack:manul确认成功收到消息
        channel.basicAck(delivery_tag, false);
    }

}
