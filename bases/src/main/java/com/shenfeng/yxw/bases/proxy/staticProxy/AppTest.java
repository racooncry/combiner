package com.shenfeng.yxw.bases.proxy.staticProxy;

/**
 * @Author yangxw
 * @Date 2020-11-19 上午10:58
 * @Description
 * @Version 1.0
 */
public class AppTest {

    public static void main(String[] args) {
        IUserDaoImpl iUserDao = new IUserDaoImpl();
        UserDaoProxy userDaoProxy = new UserDaoProxy(iUserDao);
        userDaoProxy.save();
    }

}
