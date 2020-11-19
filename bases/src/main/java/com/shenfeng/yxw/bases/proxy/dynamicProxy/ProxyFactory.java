package com.shenfeng.yxw.bases.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author yangxw
 * @Date 2020-11-19 下午1:33
 * @Description
 * @Version 1.0
 */
public class ProxyFactory {

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("开始事务2");
                        Object invoke = method.invoke(target, args);
                        System.out.println("提交事务2");
                        return invoke;
                    }
                });
    }

}
