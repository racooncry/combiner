package com.shenfeng.yxw.bases.lock.muke.sale;

/**
 * @Author: yangxiaowei37
 * @Date: 9/10/2021 下午2:55
 * @Version: 1.0
 * @Description:
 */
public class SaleRollback implements Runnable {
    private TicketCenter ticketCenter;

    public SaleRollback(TicketCenter ticketCenter) {
        this.ticketCenter = ticketCenter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ticketCenter.saleRollBack();
        }
    }

}