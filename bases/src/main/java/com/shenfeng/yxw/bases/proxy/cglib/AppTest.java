package com.shenfeng.yxw.bases.proxy.cglib;


import com.shenfeng.yxw.bases.proxy.dynamicProxy.IUserDao;
import com.shenfeng.yxw.bases.proxy.dynamicProxy.IUserDaoImpl;

/**
 * @Author yangxw
 * @Date 2020-11-19 上午10:58
 * @Description
 * @Version 1.0
 */
public class AppTest {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        UserDao proxyInstance = (UserDao) new ProxyFactory(userDao).getProxyInstance();
        proxyInstance.save();
    }

}
