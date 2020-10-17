package com.shenfeng.yxw.rabbitmqspringcloudstreamconsumer.stream;


import com.rabbitmq.client.Channel;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;


@EnableBinding(Barista.class)
@Service
public class RabbitmqReceiver {

    @StreamListener(Barista.INPUT_CHANNEL)
    public void receiver(Message message) throws Exception {
        Channel channel = (com.rabbitmq.client.Channel) message.getMessageProperties().getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getMessageProperties().getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        System.out.println("Input Stream 1 接受数据：" + message);
        System.out.println("消费完毕------------");
        channel.basicAck(deliveryTag, false);
    }
}  
