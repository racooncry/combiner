package com.shenfeng.yxw.bases.designModal.observe;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:33
 * @Description
 * @Version 1.0
 */
public class User implements Observer {
    private String name;
    private String message;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        this.message = message;
        read();
    }

    public void read() {
        System.out.println(name + " 收到推送消息： " + message);
    }
}
