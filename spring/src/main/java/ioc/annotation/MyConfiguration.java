package ioc.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yangxw
 * @Date 2020-11-18 上午11:09
 * @Description
 * @Version 1.0
 */
@Configuration
public class MyConfiguration {
    @Bean(value = "bean1")
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean(value = {"bean3", "bean2"})
    public Bean1 bean2() {
        return new Bean1();
    }
}
