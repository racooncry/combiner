package ioc.cycleDepency;

import org.springframework.stereotype.Component;

/**
 * @Author yangxw
 * @Date 2020-11-23 上午10:55
 * @Description
 * @Version 1.0
 */
@Component
public class B {
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B(A a) {
        this.a = a;
    }

    public B() {
    }
}
