package ioc.cycleDepency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author yangxw
 * @Date 2020-11-23 上午10:57
 * @Description
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ioc.cycleDepency.MyConfiguration.class);
        A a = (A)context.getBean("a");
    }
}
