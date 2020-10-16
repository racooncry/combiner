package com.shenfeng.yxw.rabbitmq.ack;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-14 上午11:37
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
        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.#";
        String queueName = "test_ack_queue";
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 5、创建消费者
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("receive:" + message);

                Integer num = (Integer) properties.getHeaders().get("num");
                // 消息处理完了，可以给下一条
                long deliveryTag = envelope.getDeliveryTag();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (num == 0) {
                    // 重回队列，消息重新回到队列的尾部
                    channel.basicNack(deliveryTag, false, true);
                } else {
                    channel.basicAck(deliveryTag, false);
                }

                // channel.basicAck(deliveryTag, false);
            }
        };


        // 限流方式, 消息确认机制,ack设置为false
        channel.basicConsume(queueName, false, consumer);


    }
}
