package com.shenfeng.yxw.rabbitmq.consumer.customConsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-16 上午8:35
 * @Description
 * @Version 1.0
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {


        // 1、创建connectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.122");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 2、创建connection
        Connection connection = factory.newConnection();

        // 3、创建channel
        Channel channel = connection.createChannel();


        // 4、声明
        String exchangeName = "test_custom_consumer_exchange";
        String routingKey = "consumer.#";
        String queueName = "test_custom_consumer_queue";
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);


        // 5、创建消费者
        com.rabbitmq.client.Consumer consumer = new MyConsumer(channel);
        // 消息确认机制,ack自动签收
        channel.basicConsume(queueName, true, consumer);


    }

}
