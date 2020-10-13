package com.shenfeng.yxw.rabbitmq.producer;

import com.rabbitmq.client.Channel;
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

        // 4、通过Channel发送数据
        String message = "Hello RabbitMQ";
        for (int i = 0; i < 5; i++) {
            // The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
            // It is not possible to explicitly bind to,
            // or unbind from the default exchange. It also cannot be deleted.
            channel.basicPublish("", "test001", null, message.getBytes());
        }

        // 5、关闭连接
        channel.close();
        connection.close();

    }
}
