package com.shenfeng.yxw.ssh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangxiaowei37
 * @Date: 2/11/2021 下午3:32
 * @Version: 1.0
 * @Description:
 */
@RestController
public class WebSSHController {
    @GetMapping("/web-ssh-page")
    public String websshpage() {
        return "web-ssh";
    }
}
