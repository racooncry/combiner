package com.shenfeng.yxw.bases.defination;

import org.springframework.web.bind.annotation.GetMapping;

public class TestController {

    /**
     * curl localhost:8080/v4/api/user
     * @return
     */
    @GetMapping(value = "/api/user")
    @APIVersion("v4")
    public int right4() {
        return 4;
    }
}
