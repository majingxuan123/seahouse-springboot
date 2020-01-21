package com.seahouse.compoment.rabbitmq.withannotation;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 使用注解配置消费端
 * <p>
 * 需要在application配置文件中配置
 * <p>
 * <p>
 * listener:
 * simple:
 * #手动签收
 * acknowledge-mode: manual
 * #默认接收大小
 * concurrency: 5
 * max-concurrency: 10
 */

//@PropertySource("classpath:application.yml")
//@Component
public class ConsumerAnnotation {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${spring.rabbitmq.listener.study.queue.name}",
            declare = "${spring.rabbitmq.listener.study.queue.declare}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.study.exchange.name}",
                    declare = "${spring.rabbitmq.listener.study.exchange.declare}",
                    type = "${spring.rabbitmq.listener.study.exchange.type}"),
            key = "${spring.rabbitmq.listener.study.key}"))
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        if (headers != null) {
            Set<Map.Entry<String, Object>> entries = headers.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                System.out.println("key:" + next.getKey() + "----value:" + next.getValue());
            }
        }
        System.err.println("消费者接收信息：" + body);

        //ack:manul确认成功收到消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
