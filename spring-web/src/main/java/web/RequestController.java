package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestController {


    @ResponseBody
    @RequestMapping("test-request")
    public String test() {
        new ModelAndView();
        return this.toString();
    }
}
