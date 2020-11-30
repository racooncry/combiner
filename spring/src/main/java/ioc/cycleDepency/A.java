package ioc.cycleDepency;

import org.springframework.stereotype.Component;

/**
 * @Author yangxw
 * @Date 2020-11-23 上午10:55
 * @Description
 * @Version 1.0
 */
@Component
public class A {
    private  B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public A(B b) {
        this.b = b;
    }

    public A() {
    }
}
