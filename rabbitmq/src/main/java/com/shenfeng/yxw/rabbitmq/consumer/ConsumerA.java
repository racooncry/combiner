package com.shenfeng.yxw.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author yangxw
 * @Date 2020-10-13 上午8:58
 * @Description
 * @Version 1.0
 */
public class ConsumerA {

    public static void main(String[] args) {
        try {
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

            // 4、声明(创建)一个队列
            // queueDeclare(队列名字，持久化，是否独占-顺序，脱离删除)
            String queueName = "test001";
            channel.queueDeclare(queueName, true, false, false, null);

            // 5、创建消费者
            // 创建队列消费者
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("receive:" + message);
                }
            };
            // 消息确认机制,ack自动签收
            channel.basicConsume(queueName, true, consumer);




            // 6、关闭连接
//            channel.close();
//            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
