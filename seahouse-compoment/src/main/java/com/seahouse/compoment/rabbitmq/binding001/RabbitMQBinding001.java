package com.seahouse.compoment.rabbitmq.binding001;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <ul>
 * <li>文件名称：RabbitMQBinding001</li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2019</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/1/12 14:50</li>
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
//@Configuration
public class RabbitMQBinding001 {

    /**
     * 声明交换机001
     * @return
     */
    @Bean
    public TopicExchange exchange001(){
        return new TopicExchange("springboot-topic001",true,true);
    }

    /**
     * 声明队列001
     * @return
     */
    @Bean
    public Queue queue001(){
        return new Queue("queue001",true);
    }
    /**
     * 声明队列002
     * @return
     */
    @Bean
    public Queue queue002(){
        return new Queue("queue002",true);
    }
    /**
     * 声明绑定001
     * 如果是rabbit.study.* 就路由到队列001
     * @return
     */
    @Bean
    public Binding binding001(){
        return BindingBuilder.bind(queue001()).to(exchange001()).with("rabbit.study.*");
    }


    /**
     * 声明绑定002
     * 如果是rabbit.test.# 就路由到队列002
     *
     * @return
     */
    @Bean
    public Binding binding002(){
        return BindingBuilder.bind(queue001()).to(exchange001()).with("rabbit.test.#");
    }



}


