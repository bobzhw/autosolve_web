package edu.uestc.web.admin;


import edu.uestc.po.Question;
import edu.uestc.po.User;
import edu.uestc.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("admin")
public class QueryController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/singlequery")
    public String singleQuery(HttpSession session,Model model){
        User user =  (User)session.getAttribute("user");
        if(user!=null) {
            model.addAttribute("username", user.getUsername());
            return "admin/singlequery";
        }
        return "admin/login";
    }
    @GetMapping("/query")
    public String query(Model model,HttpSession session){
        User user =  (User)session.getAttribute("user");
        if(user!=null)
        {
            model.addAttribute("username",user.getUsername());
            return "admin/query";
        }
        return "admin/login";

    }

    @GetMapping("/test")
    public String test(Model model,HttpSession session){
        User user =  (User)session.getAttribute("user");
        if(user!=null) {
            model.addAttribute("username", user.getUsername());
            return "admin/test";
        }
        return "admin/login";
    }



}
