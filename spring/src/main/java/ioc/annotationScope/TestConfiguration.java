package ioc.annotationScope;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午12:09
 * @Description
 * @Version 1.0
 */
@Configuration
@ComponentScan("ioc.annotationScope")
public class TestConfiguration {

//    @Bean("testBean1")
//    @Scope("myScope")
//    public TestBean testBean() {
//        return new TestBean();
//    }


    @Bean
    public MyScope myScope() {
        return new MyScope();
    }


    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope("myScope", myScope());
        return customScopeConfigurer;
    }


}
