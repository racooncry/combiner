package com.shenfeng.yxw.rabbitmq.rateLimit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-14 上午11:37
 * @Description
 * @Version 1.0
 */
public class Porducer {
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


        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";
        String body = "Hello RabbitMQ & Message Confirm";

        // 5、发送消息
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey, null, body.getBytes());
        }
        channel.close();
        connection.close();


    }
}
