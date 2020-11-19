package com.shenfeng.yxw.bases.proxy.staticProxy;

/**
 * @Author yangxw
 * @Date 2020-11-19 上午10:58
 * @Description
 * @Version 1.0
 */
public class UserDaoProxy implements IUserDao {

    private IUserDao iUserDao;

    public UserDaoProxy(IUserDao iUserDao) {
        this.iUserDao = iUserDao;
    }

    @Override
    public void save() {
        System.out.println("开始事务");
        iUserDao.save();
        System.out.println("结束事务");
    }
}
