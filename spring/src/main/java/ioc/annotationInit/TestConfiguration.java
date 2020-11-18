package ioc.annotationInit;

import ioc.annotationScope.MyScope;
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
@ComponentScan("ioc.annotationInit")
public class TestConfiguration {

    @Bean(initMethod = "onInit1", destroyMethod = "onDestory1",value = "testBean1")
    public TestBean testBean() {
        return new TestBean();
    }


}
