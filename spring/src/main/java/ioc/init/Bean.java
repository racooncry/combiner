package ioc.init;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Author yangxw
 * @Date 2020-11-18 上午10:46
 * @Description
 * @Version 1.0
 */
public class Bean implements InitializingBean, DisposableBean {
    public void onInit() {
        System.out.println("Bean.onInit");
    }

    public void onDestory() {
        System.out.println("Bean.onDestory");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean.onDestory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean.onInit");
    }
}
