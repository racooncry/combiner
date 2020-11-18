package ioc.annotationLazy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午12:23
 * @Description
 * @Version 1.0
 */
@Configuration
@ComponentScan("ioc.annotationLazy")
@Lazy
public class TestConfiguration {


    @Bean("testBean2")
    public TestBean testBean() {
        return new TestBean();
    }
}
