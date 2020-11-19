package com.shenfeng.yxw.bases.proxy.dynamicProxy;



/**
 * @Author yangxw
 * @Date 2020-11-19 上午10:58
 * @Description
 * @Version 1.0
 */
public class AppTest {

    public static void main(String[] args) {
        IUserDaoImpl iUserDao = new IUserDaoImpl();
        IUserDao proxyInstance = (IUserDao) new ProxyFactory(iUserDao).getProxyInstance();
        System.out.println(proxyInstance.getClass());

        proxyInstance.save();
    }

}
