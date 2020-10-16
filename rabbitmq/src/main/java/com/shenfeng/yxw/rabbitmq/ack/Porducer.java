package com.shenfeng.yxw.rabbitmq.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
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


        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";


        // 5、发送消息
        for (int i = 0; i < 5; i++) {
            String body = "Hello RabbitMQ & Message Ack " + i;
            HashMap<String, Object> map = new HashMap<>();
            map.put("num", i);
            AMQP.BasicProperties properties = new AMQP.BasicProperties()
                    .builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(map)
                    .build();
            channel.basicPublish(exchangeName, routingKey, properties, body.getBytes());
        }
        channel.close();
        connection.close();


    }
}
