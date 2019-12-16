package edu.uestc.web.admin;

import edu.uestc.Utils.DataBase;
import edu.uestc.Utils.KafkaProducerr;
import edu.uestc.po.Question;
import edu.uestc.po.Request;
import edu.uestc.po.User;
import edu.uestc.service.QuestionService;
import edu.uestc.service.RequestService;
import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.List;


/**
 * Created by zw on 2019/12/14.
 */

@Controller
@RequestMapping("/admin")
public class ComputeController {

    @Autowired
    private RequestService requestService;

    private static KafkaProducerr kafkaProducerr = new KafkaProducerr();
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
            kafkaProducerr.produce(id,"latex");
            Request r = null;
            while(r==null){
                r = requestService.queryRequest(id,0,1);
            }
            requestService.deleteRequest(id,0,1);
            question.setStem(r.getStem());
            question.setSubStem(r.getSubStem());
            model.addAttribute("question",question);
            return "admin/singlequeryResult";
        }
        return "admin/login";
    }
    @RequestMapping(value="nlp")
    public String nlp(@RequestParam String nlpquestionId,HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            Question question = questionService.queryByQuestionId(nlpquestionId);
            kafkaProducerr.produce(nlpquestionId,"latex");
            Request r = null;
            while(r==null){
                r = requestService.queryRequest(nlpquestionId,0,1);
            }
            question.setStem(r.getStem());
            question.setSubStem(r.getSubStem());
            requestService.deleteRequest(nlpquestionId,0,1);

            model.addAttribute("question",question);
            kafkaProducerr.produce(nlpquestionId,"nlp");
            r=null;
            while(r==null){
                r = requestService.queryRequest(nlpquestionId,1,1);
            }
            requestService.deleteRequest(nlpquestionId,1,1);
            model.addAttribute("result",r.getSolveResult());
            return "admin/singlequerynlpResult";
        }
        return "admin/login";
    }

    @RequestMapping(value="solve")
    public String solve(@RequestParam String solvequestionId,HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            Question question = questionService.queryByQuestionId(solvequestionId);
            kafkaProducerr.produce(solvequestionId,"latex");
            Request r = null;
            while(r==null){
                r = requestService.queryRequest(solvequestionId,0,1);
            }
            question.setStem(r.getStem());
            question.setSubStem(r.getSubStem());
            requestService.deleteRequest(solvequestionId,0,1);
            model.addAttribute("question",question);
            kafkaProducerr.produce(solvequestionId,"solve");
            r=null;
            while(r==null){
                r = requestService.queryRequest(solvequestionId,2,1);
            }
            requestService.deleteRequest(solvequestionId,2,1);
            model.addAttribute("result",r.getSolveResult());
            return "admin/singlequerysolveResult";
        }
        return "admin/login";
    }
}
