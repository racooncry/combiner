package com.shenfeng.yxw.rabbitmqspring;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class RabbitmqSpringApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void testAdmin() {
        rabbitAdmin.declareExchange(new DirectExchange("spring.direct", false, false));
        rabbitAdmin.declareExchange(new TopicExchange("spring.topic", false, false));
        rabbitAdmin.declareExchange(new FanoutExchange("spring.fanout", false, false));


        rabbitAdmin.declareQueue(new Queue("spring.direct.queue", false));
        rabbitAdmin.declareQueue(new Queue("spring.topic.queue", false));
        rabbitAdmin.declareQueue(new Queue("spring.fanout.queue", false));

        rabbitAdmin.declareBinding(new Binding("spring.direct.queue", Binding.DestinationType.QUEUE,
                "spring.direct", "direct", new HashMap<>()));

//        rabbitAdmin.declareBinding(new Binding("spring.topic.queue", Binding.DestinationType.QUEUE,
//                "spring.topic", "topic.*", new HashMap<>()));


        rabbitAdmin.declareBinding(new Binding("spring.fanout.queue", Binding.DestinationType.QUEUE,
                "spring.fanout", "fanout", new HashMap<>()));


        rabbitAdmin.declareBinding(BindingBuilder
                .bind(new Queue("spring.topic.queue", false))
                .to(new TopicExchange("spring.topic", false, false))
                .with("user.#")
        );


        // 清除队列里面的消息
        rabbitAdmin.purgeQueue("spring.topic.queue", false);

    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "信息描述");
        messageProperties.getHeaders().put("type", "自定义消息类型");
        Message message = new Message("Hello Rabbit".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("----添加额外设置------");
                message.getMessageProperties().getHeaders().put("desc", "额外修改信息描述");
                message.getMessageProperties().getHeaders().put("attr", "额外新添加的属性");
                return message;
            }
        });

    }


    @Test
    public void testSendMessage2() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        Message message = new Message("mq 12345".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("topic001", "spring.abc", message);


        rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello test");
        rabbitTemplate.convertAndSend("topic002", "rabbit.abc", "hello ttt");




    }

}
