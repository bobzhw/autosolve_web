package edu.uestc.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import edu.uestc.Utils.*;
import edu.uestc.po.User;
import edu.uestc.service.QuestionService;
import edu.uestc.service.RequestService;
import org.apache.commons.codec.language.bm.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import scala.Int;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zw on 2019/12/25.
 */

@Controller
@RequestMapping("admin")
public class MarkRuleController {
    @Autowired
    private RequestService requestService;

    private static DataBase dataBase = DataBase.getInstance();
    private static KafkaProducerr kafkaProducerr = new KafkaProducerr();
    @Autowired
    private QuestionService questionService;
//       private static SocketPool socketPool = SocketPool.getInstance("121.48.165.136",8899);
//       private static SocketPool socketPool = SocketPool.getInstance("127.0.0.1",8899);
    private String ip="121.48.165.136";
//    private String ip="192.168.1.249";
    //private String ip="127.0.0.1";
    private int port = 8899;
//    private static SocketPool socketPool = SocketPool.getInstance("192.168.1.249", 8899);
//    private SocketInfo socketInfo = socketPool.getSocketInfo();
    @RequestMapping(value = "makeRule")

    public String makerule(@RequestParam(required = false)

                                       String conditionContext,
                           @RequestParam(required = false) String conclusionContext,
                           HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if(conditionContext.equals("") || conclusionContext.equals("")){
                return "admin/rule";
            }
            SocketInfo socketInfo = new SocketInfo(ip,port);
            RequestText requestText =  new RequestText(conditionContext,conclusionContext);
            socketInfo.write("1"+JSON.toJSONString(requestText));
            String res = socketInfo.read();
            System.out.println(res);
            RuleData ruleData = JSONObject.parseObject(res, RuleData.class);
            if (ruleData == null || ruleData.getTripples().size() != 2) {
                return "admin/rule";
            }
            Map<Integer, RuleData.Tripple> conditionMap = new HashMap<>();
            Map<Integer, RuleData.Tripple> conclusionMap = new HashMap<>();
            RuleData.Tripples conditions = null;
            RuleData.Tripples conclusions = null;
            for(RuleData.Tripples tripples:ruleData.getTripples()){
                if(tripples.getType()==1){
                    conditions=tripples;
                }
                else{
                    conclusions=tripples;
                }
            }
            for(RuleData.Tripple tripple : conditions.getTripples()){
                conditionMap.put(tripple.getId(),tripple);
            }
            for(RuleData.Tripple tripple : conclusions.getTripples()){
                conclusionMap.put(tripple.getId(),tripple);
            }
            session.setAttribute("ruleData",ruleData);
            session.setAttribute("conditionMap",conditionMap);
            session.setAttribute("conclusionMap",conclusionMap);
            model.addAttribute("conditions",conditions);
            model.addAttribute("conclusions",conclusions);
            session.setAttribute("methods", ruleData.getMethod());
            return "admin/ruleSelect";
        }
        return "admin/login";
    }

    @RequestMapping(value = "checkChoice")
    public String select(@RequestParam(required = false) String conditionids,
                         @RequestParam(required = false) String conclusionids,
                         HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if(conditionids==null){
                conditionids="";
            }
            if(conclusionids==null){
                conclusionids="";
            }
            Map<Integer, RuleData.Tripple>  conclusionMap = (Map<Integer, RuleData.Tripple> )session.getAttribute("conclusionMap");
            Map<Integer, RuleData.Tripple>  conditionMap = (Map<Integer, RuleData.Tripple> )session.getAttribute("conditionMap");
            List<RuleData.Method> methods = (List<RuleData.Method>)session.getAttribute("methods");
            String[] condid = conditionids.split(",");
            String[] consid = conclusionids.split(",");
            List<RuleData.Tripple> condiString = new ArrayList<>();
            List<RuleData.Tripple> consString = new ArrayList<>();
            for(String s : condid){
                if(!s.equals(""))
                    condiString.add(conditionMap.get(Integer.parseInt(s)));
            }
            for(String s : consid){
                if(!s.equals(""))
                consString.add(conclusionMap.get(Integer.parseInt(s)));
            }
            RuleData ruleData = (RuleData)session.getAttribute("ruleData");
            session.setAttribute("cond",condiString);
            session.setAttribute("cons",consString);
            model.addAttribute("cond",condiString);
            model.addAttribute("cons",consString);
            model.addAttribute("methods",methods);
            model.addAttribute("parameters",ruleData.getParameters());
            List<RuleData.Tripple> res = new ArrayList<>();
            res.addAll(condiString);
            res.addAll(consString);
            ruleData.setResult(res);
            SocketInfo socketInfo = new SocketInfo(ip,port);
            socketInfo.write("2"+JSON.toJSONString(ruleData));
            return "admin/rule";
        } else {
            return "admin/login";
        }
    }

    @RequestMapping(value="selectOver")
    public String selectover(@RequestParam(required = false) String methods,
                             @RequestParam(required = false) String parms,
                             HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if(methods==null || parms==null){
                return "admin/conlusion";
            }
            String[] methodArr = methods.split(",");
            String[] parmeterArr = parms.split(",");
            Map<String,List<String>> parmeterMap = new HashMap<>();
            for(String s : parmeterArr){
                String[] sgr = s.split("-");
                if(!parmeterMap.containsKey(sgr[0])){
                    List<String> lists = new ArrayList<>();
                    lists.add(sgr[1]);
                    parmeterMap.put(sgr[0],lists);
                }else{
                    List<String> lists = parmeterMap.get(sgr[0]);
                    lists.add(sgr[1]);
                    parmeterMap.put(sgr[0],lists);
                }
            }
            List<RuleData.Tripple> condiString = (List<RuleData.Tripple>)session.getAttribute("cond");
            List<RuleData.Tripple> consString = (List<RuleData.Tripple>)session.getAttribute("cons");
            RuleData ruleData = (RuleData)session.getAttribute("ruleData");
            if(methodArr.length!=consString.size()){
                List<RuleData.Method> methodlist = (List<RuleData.Method>)session.getAttribute("methods");
                model.addAttribute("cond",condiString);
                model.addAttribute("cons",consString);
                model.addAttribute("methods",methodlist);
                return "admin/ruleSelect";
            }
            List<RuleData.Tripple> res = new ArrayList<>();
            res.addAll(condiString);
            for(int i = 0;i<methodArr.length;i++){
                RuleData.Tripple tripple = consString.get(i);
                int id = tripple.getId();
                String tmp = tripple.getTripple_string();
                String[] lr = tmp.split("->");
                StringBuffer sb = new StringBuffer();
                for(String s : parmeterMap.get(Integer.toString(id))){
                    sb.append(ruleData.getParameters().get(Integer.parseInt(s)).getName()).append("&");
                }

                tripple.setTripple_string(lr[0]+"->#"+methodArr[i]+"#"+lr[1].substring(0,lr[1].indexOf('【'))+":"+ sb.toString().substring(0,sb.length()-1));
                res.add(tripple);
            }
            ruleData.setResult(res);
            SocketInfo socketInfo = new SocketInfo(ip,port);
            socketInfo.write("2"+JSON.toJSONString(ruleData));
//            session.removeAttribute("conditions");
//            session.removeAttribute("conclusions");
//            session.removeAttribute("cond");
//            session.removeAttribute("cons");
//            session.removeAttribute("methods");
//            session.removeAttribute("ruleData");
            return "admin/rule";
        }
        return "admin/login";
    }

}
