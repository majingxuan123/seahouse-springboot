package com.seahouse.compoment.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

/**
 *
 *
 */
public class MyMessageConverter implements MessagePropertiesConverter {

    @Override
    public MessageProperties toMessageProperties(AMQP.BasicProperties source, Envelope envelope, String charset) {
        return null;
    }

    @Override
    public AMQP.BasicProperties fromMessageProperties(MessageProperties source, String charset) {
        return null;
    }
}
