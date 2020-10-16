package com.shenfeng.yxw.rabbitmq.message.returnPattern;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author yangxw
 * @Date 2020-10-15 上午9:20
 * @Description
 * @Version 1.0
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.122");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");


        Connection connection = factory.newConnection();


        Channel channel = connection.createChannel();


        String exchange = "test_return_exchange";
        String routingKey = "return.save";

        String routingErro = "abc.save";
        String body = "Hello RabbitMQ & Return Message";


        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("----- handle return ------");
                System.err.println("replyCode:" + replyCode);
                System.err.println("replyText:" + replyText);
                System.err.println("exchange:" + exchange);
                System.err.println("routingKey:" + routingKey);
                System.err.println("properties:" + properties);
                System.err.println("body:" + new String(body));
            }
        });


       // channel.basicPublish(exchange, routingKey, true, null, body.getBytes());
       // channel.basicPublish(exchange, routingErro, false, null, body.getBytes());
        channel.basicPublish(exchange, routingErro, true, null, body.getBytes());

    }
}
