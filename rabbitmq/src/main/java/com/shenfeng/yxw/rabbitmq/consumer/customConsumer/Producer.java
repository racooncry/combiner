package com.shenfeng.yxw.rabbitmq.consumer.customConsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-13 上午8:54
 * @Description
 * @Version 1.0
 */
public class Producer {
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


        String exchangeName = "test_custom_consumer_exchange";
        String routingKey = "consumer.save";
        String body = "Hello RabbitMQ & Message CustomConsumer";

        // 5、发送消息
        channel.basicPublish(exchangeName, routingKey, null, body.getBytes());

        channel.close();
        connection.close();


    }
}
