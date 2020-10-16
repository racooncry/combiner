package com.shenfeng.yxw.rabbitmq.message.returnPattern;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-15 上午9:20
 * @Description
 * @Version 1.0
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.122");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");


        Connection connection = factory.newConnection();


        Channel channel = connection.createChannel();


        String exchangeName = "test_return_exchange";
        String routingKey = "return.#";

        String queueName = "test_return_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);


        // 5、创建消费者
        // 创建队列消费者
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("receive:" + message);

            }
        };

        // 消息确认机制,ack自动签收
        channel.basicConsume(queueName, true, consumer);

    }
}
