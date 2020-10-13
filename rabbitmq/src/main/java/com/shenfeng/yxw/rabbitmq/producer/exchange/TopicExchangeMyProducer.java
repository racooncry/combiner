package com.shenfeng.yxw.rabbitmq.producer.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-13 下午10:42
 * @Description
 * @Version 1.0
 */
public class TopicExchangeMyProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 1、获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.122");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 2、通过连接工厂创建连接
        Connection connection = factory.newConnection();

        // 3、通过connection创建一个Channel
        Channel channel = connection.createChannel();

        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";
        String message = "Hello World RabbitMQ & Topic Exchange Message..";
        channel.basicPublish(exchangeName, routingKey1, null, message.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, message.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, message.getBytes());


        channel.close();
        connection.close();


    }
}
