package com.shenfeng.yxw.bases.lock.muke.sale2;

/**
 * @Author: yangxiaowei37
 * @Date: 13/10/2021 上午11:18
 * @Version: 1.0
 * @Description:
 */
public class Producer implements Runnable {
    private ProductFactory productFactory; //关联工厂类，调用 produce 方法

    public Producer(ProductFactory productFactory) {
        this.productFactory = productFactory;
    }


    @Override
    public void run() {
        int i = 0; // 根据需求，对产品进行编号
        while (true) {
            productFactory.produce(String.valueOf(i)); //根据需求 ，调用 productFactory 的 produce 方法
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
