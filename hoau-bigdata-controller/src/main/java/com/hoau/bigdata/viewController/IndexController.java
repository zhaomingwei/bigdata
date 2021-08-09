package com.hoau.bigdata.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String login() {
        return "index";
    }


    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }
}
