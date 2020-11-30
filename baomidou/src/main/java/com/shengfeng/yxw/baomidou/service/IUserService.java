package com.shengfeng.yxw.baomidou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shengfeng.yxw.baomidou.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yang xw
 * @since 2020-11-27
 */
public interface IUserService extends IService<User> {


    List<User> queryUserList();

    IPage<User> page();
    int mySave(User user);
}
