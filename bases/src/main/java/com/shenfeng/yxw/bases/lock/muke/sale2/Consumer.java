package com.shenfeng.yxw.bases.lock.muke.sale2;

/**
 * @Author: yangxiaowei37
 * @Date: 13/10/2021 上午11:19
 * @Version: 1.0
 * @Description:
 */
public class Consumer implements Runnable {
    private ProductFactory productFactory;

    public Consumer(ProductFactory productFactory) {
        this.productFactory = productFactory;
    }

    public void run() {
        while (true) {
            productFactory.consume();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
