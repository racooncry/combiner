package ioc.annotationInit;

import ioc.annotationScope.AnotherBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午12:10
 * @Description
 * @Version 1.0
 */
//@Component("testBean1")
public class TestBean implements InitializingBean, DisposableBean {


    @Override
    public void destroy() throws Exception {
        System.out.println("TestBean.destory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestBean.init");
    }


    @PostConstruct
    public void onInit() {

        System.out.println("init");
    }


    @PreDestroy
    public void onDestory() {
        System.out.println("destory");
    }




    public void onInit1() {

        System.out.println("init1");
    }


    public void onDestory1() {
        System.out.println("destory1");
    }

}
