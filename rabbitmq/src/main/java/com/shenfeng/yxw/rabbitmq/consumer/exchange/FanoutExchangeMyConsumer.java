package com.shenfeng.yxw.rabbitmq.consumer.exchange;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author yangxw
 * @Date 2020-10-13 下午10:53
 * @Description
 * @Version 1.0
 */
public class FanoutExchangeMyConsumer {
    public static void main(String[] args) {
        try {
            // 1、获取连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.1.122");
            factory.setPort(5672);
            factory.setVirtualHost("/");
            factory.setUsername("admin");
            factory.setPassword("admin");
            // 自动重连
            factory.setAutomaticRecoveryEnabled(true);
            factory.setNetworkRecoveryInterval(3000);


            // 2、通过连接工厂创建连接
            Connection connection = factory.newConnection();

            // 3、通过connection创建一个Channel
            Channel channel = connection.createChannel();

            // 4、声明(创建)一个队列
            // queueDeclare(队列名字，持久化，是否独占-顺序，脱离删除)
            String exchangeName = "test_fanout_exchange";
            String exchangeType = "fanout";
            String queueName = "test_fanout_queue";
            String routingKey = "";
            // 声明交换机
            channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

            // 声明队列
            channel.queueDeclare(queueName, false, false, false, null);

            // 建立绑定关系
            channel.queueBind(queueName, exchangeName, routingKey);


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
