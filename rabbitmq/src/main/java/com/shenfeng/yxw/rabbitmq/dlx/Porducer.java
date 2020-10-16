package com.shenfeng.yxw.rabbitmq.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;

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


        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";


        // 5、发送消息
        for (int i = 0; i < 5; i++) {

            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    // 持久化
                    .deliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue())
                    .contentEncoding("UTF-8")
                    // 15秒过期
                    .expiration("10000")
                    .build();

            String body = "Hello RabbitMQ & DLX Ack " + i;
            HashMap<String, Object> map = new HashMap<>();
            map.put("num", i);
            channel.basicPublish(exchangeName, routingKey, properties, body.getBytes());
        }
        channel.close();
        connection.close();


    }
}
