package ioc.annotationScope;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午12:10
 * @Description
 * @Version 1.0
 */
@Component("testBean2")
@Scope("singleton")
public abstract class TestBean {

    @Lookup
    public abstract AnotherBean anotherBean();

    public void printerAnotherBean() {
        System.out.println("anotherBean()=" + anotherBean());
    }
}
