package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestController {


    @ResponseBody
    @RequestMapping("test-request")
    public String test() {
        return this.toString();
    }
}
