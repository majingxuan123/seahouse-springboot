package com.seahouse.compoment.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import javax.xml.transform.Source;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <ul>
 * <li>文件名称：MyMessageAdapter</li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2019</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/1/12 22:44</li>
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
public class MyMessageAdapter implements ChannelAwareMessageListener {


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String body = new String(message.getBody());
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();

        if(headers!=null){
            Set<Map.Entry<String, Object>> entries = headers.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                System.out.println("key:"+next.getKey()+"----value:"+next.getValue());
            }
        }
        System.err.println("消费者接收信息：" + body);

        //如果是手工ack 就需要下面的代码

        //消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认成功收到消息
        //ack返回false，并重新回到队列，api里面解释得很清楚
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        //拒绝消息
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    }

    @Override
    public void onMessage(Message message) {
        String body = new String(message.getBody());

        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();

        if(headers!=null){
            Set<Map.Entry<String, Object>> entries = headers.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                System.out.println("key:"+next.getKey()+"----value:"+next.getValue());
            }
        }
        System.err.println("消费者接收信息：" + body);


    }

}
