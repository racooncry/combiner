package com.shenfeng.yxw.bases.lock.muke.sale;

/**
 * @Author: yangxiaowei37
 * @Date: 9/10/2021 下午2:53
 * @Version: 1.0
 * @Description:
 */
public class Consumer implements Runnable {
    private TicketCenter ticketCenter;

    public Consumer(TicketCenter ticketCenter) {
        this.ticketCenter = ticketCenter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ticketCenter.sale();
        }
    }

}
