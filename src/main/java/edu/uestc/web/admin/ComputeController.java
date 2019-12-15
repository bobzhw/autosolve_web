package edu.uestc.web.admin;

import edu.uestc.po.Question;
import edu.uestc.po.User;
import edu.uestc.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by zw on 2019/12/14.
 */

@Controller
@RequestMapping("/admin")
public class ComputeController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/querySearch")
    public String querySearch(@RequestParam String area,
                              @RequestParam String year,
                              @RequestParam String wl,
                              HttpSession session,
                              Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            List<Question> questionList = questionService.queryTaoti(area,year,wl);
            model.addAttribute("questionList",questionList);
            return "admin/queryResult";
        }
        return "admin/login";

    }

    @PostMapping("/search")
    public String search(@RequestParam String id,HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            Question question = questionService.queryByQuestionId(id);
            model.addAttribute("question",question);
            return "admin/singlequeryResult";
        }
        return "admin/login";
    }



}
