package com.shengfeng.yxw.rabbitmqspringcloudstreamproducer;

import com.shengfeng.yxw.rabbitmqspringcloudstreamproducer.stream.Barista;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(Barista.class)
@SpringBootApplication
public class RabbitmqSpringcloudstreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSpringcloudstreamProducerApplication.class, args);
    }

}
