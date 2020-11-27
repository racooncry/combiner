package com.shengfeng.yxw.baomidou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shengfeng.yxw.baomidou.entity.User;
import com.shengfeng.yxw.baomidou.mapper.UserMapper;
import com.shengfeng.yxw.baomidou.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yang xw
 * @since 2020-11-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @Override
    public IPage<User> page() {
        Page<User> page = new Page<>(0, 2);
        IPage<User> userIPage = userMapper.selectPageVo(page, 21);
        return userIPage;
    }

    @Override
    public int mySave(User user) {





        return 0;
    }


}
