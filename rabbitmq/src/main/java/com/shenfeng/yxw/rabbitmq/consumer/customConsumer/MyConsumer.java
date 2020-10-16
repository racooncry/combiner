package com.shenfeng.yxw.rabbitmq.consumer.customConsumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Author yangxw
 * @Date 2020-10-16 上午8:36
 * @Description
 * @Version 1.0
 */
public class MyConsumer extends DefaultConsumer {


    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

        System.out.println("message:" + new String(body));
    }
}
