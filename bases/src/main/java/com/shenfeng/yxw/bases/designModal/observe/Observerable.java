package com.shenfeng.yxw.bases.designModal.observe;

/**
 * @Author yangxw
 * @Date 2020-11-22 下午5:31
 * @Description
 * @Version 1.0
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 */
public interface Observerable {

    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObserver();

}
