package ioc.annotationLazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午12:24
 * @Description
 * @Version 1.0
 */
@Component("testBean1")
@Lazy
public class TestBean {
    public TestBean() {
        System.out.println("TestBean init");
    }
}
