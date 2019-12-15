package edu.uestc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by zw on 2019/12/14.
 */
@Controller
public class IndexController {

    @GetMapping()
    public String index1() {
        return "index";
    }

    @GetMapping("index")
    public String index2() {
        return "index";
    }

    @GetMapping("login")
    public String login(){
        return "admin/login";
    }
}
