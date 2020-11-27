package com.shengfeng.yxw.baomidou.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shengfeng.yxw.baomidou.entity.User;
import com.shengfeng.yxw.baomidou.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yang xw
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @GetMapping("list")
    public Object list() {
        return iUserService.queryUserList();
    }

    @GetMapping("page")
    public Object page() {
        return iUserService.page();
    }

    @PostMapping("save")
    public Object save(@RequestBody User user) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
//                .like(User::getName, "")
                .eq(User::getAge, user.getAge());
        iUserService.saveOrUpdate(user, queryWrapper);
        return "ok";
    }


}
