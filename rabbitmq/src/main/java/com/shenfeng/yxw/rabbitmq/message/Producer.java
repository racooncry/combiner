package com.shenfeng.yxw.rabbitmq.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-13 上午8:54
 * @Description
 * @Version 1.0
 */
public class Producer {
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

        Map headers = new HashMap<>();
        headers.put("my1", 11111);
        headers.put("my2", 22222);
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                // 持久化
                .deliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue())
                .contentEncoding("UTF-8")
                // 15秒过期
                .expiration("15000")
                .headers(headers)
                .build();


        // 4、通过Channel发送数据
        String message = "Hello RabbitMQ";
        for (int i = 0; i < 5; i++) {
            // The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
            // It is not possible to explicitly bind to,
            // or unbind from the default exchange. It also cannot be deleted.
            channel.basicPublish("", "test001", properties, message.getBytes());
        }

        // 5、关闭连接
        channel.close();
        connection.close();

    }
}
