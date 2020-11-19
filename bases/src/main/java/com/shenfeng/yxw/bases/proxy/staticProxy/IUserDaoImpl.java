package com.shenfeng.yxw.bases.proxy.staticProxy;

/**
 * @Author yangxw
 * @Date 2020-11-19 上午10:57
 * @Description
 * @Version 1.0
 */
// 目标对象
public class IUserDaoImpl implements IUserDao {
    @Override
    public void save() {

        System.out.println("----已经保存数据!----");
    }
}
