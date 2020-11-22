package com.shenfeng.yxw.bases.designModal.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:32
 * @Description
 * @Version 1.0
 */
public class WechatServer implements Observerable {

    // 注意到这个List集合的泛型参数为Observer接口，设计原则：面向接口编程而不是面向实现编程
    private List<Observer> list;
    private String message;

    public WechatServer() {
        list = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (!list.isEmpty())
            list.remove(o);
    }


    @Override
    public void notifyObserver() {
        for (int i = 0; i < list.size(); i++) {
            Observer observer = list.get(i);
            observer.update(message);
        }
    }

    public void setInfomation(String s) {
        this.message = s;
        System.out.println("微信服务更新消息： " + s);
        // 消息更新，通知所有观察者
        notifyObserver();
    }

}
