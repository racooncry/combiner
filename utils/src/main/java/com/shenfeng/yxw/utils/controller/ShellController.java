package com.shenfeng.yxw.utils.controller;

import com.shenfeng.yxw.utils.service.shell.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: yangxiaowei37
 * @Date: 5/11/2021 下午4:00
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping(value = "/shell")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShellController {
    private final CommandService commandService;

    @GetMapping(value = "/")
    public Object crop(@RequestParam(value = "cmd") String cmd) {
        return commandService.executeCmd(cmd);
    }
}
