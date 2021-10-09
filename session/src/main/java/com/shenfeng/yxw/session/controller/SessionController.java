package com.shenfeng.yxw.session.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: yangxiaowei37
 * @Date: 9/10/2021 上午11:11
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    @RequestMapping("/get/{name}")
    public String getSesseion(HttpServletRequest request, @PathVariable("name") String name) {
        HttpSession session = request.getSession();
        String value = (String) session.getAttribute(name);
        return "sessionId:" + session.getId() + " value:" + value;
    }

    @RequestMapping("/add/{name}/{value}")
    public String addSession(HttpServletRequest request,@PathVariable("name") String name,@PathVariable("value") String value){
        HttpSession session = request.getSession();
        session.setAttribute(name,value);
        return "sessionId:"+session.getId()+" name:"+name;
    }
}
