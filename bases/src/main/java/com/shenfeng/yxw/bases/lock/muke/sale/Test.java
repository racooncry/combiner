package com.shenfeng.yxw.bases.lock.muke.sale;

/**
 * @Author: yangxiaowei37
 * @Date: 9/10/2021 下午2:56
 * @Version: 1.0
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        TicketCenter ticketCenter = new TicketCenter();
        new Thread(new Consumer(ticketCenter)).start();
        new Thread(new SaleRollback(ticketCenter)).start();
    }
}
