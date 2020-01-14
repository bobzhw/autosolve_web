package edu.uestc.web.admin;


import edu.uestc.auto.reasoning.relation.RelationLabel;
import edu.uestc.auto.reasoning.relation.bean.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

class EntityString{
    public int index;
    public String entityString;
    public EntityString(int index,String entityString){
        this.index=index;
        this.entityString=entityString;
    }
    public EntityString(String entityString){
        this.entityString=entityString;
    }
}

class SentenceEntity{
    public int index;
    public List<EntityString> entityStrings;
    public String text;
    public SentenceEntity(int index,List<EntityString> entityStrings,String text){
        this.text=text;
        this.index=index;
        this.entityStrings=entityStrings;
    }
}
class EntityPair{
    public String entity1;
    public String entity2;
    public EntityPair(String entity1,String entity2){
        this.entity1=entity1;
        this.entity2=entity2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPair that = (EntityPair) o;
        return entity1.equals(that.entity1) &&
                entity2.equals(that.entity2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity1, entity2);
    }
}
@Controller
@RequestMapping("admin")
public class RelationMarkController {
    @RequestMapping(value="relationmark")
    public String relationmark(@RequestParam String markText,@RequestParam String answer,
                       @RequestParam(required = false) String options,@RequestParam(required = false)String subStems,
                       HttpSession session, Model model){
        session.setAttribute("markText",markText);
        session.setAttribute("answer",answer);
        session.setAttribute("options",options);
        session.setAttribute("subStems",subStems);
        boolean isAnswer = !answer.equals("1");
        try{
            List<String> subStem = null;
            List<String> option = null;
            if(!subStems.isEmpty()){
                subStem = Arrays.asList(subStems.split("#%#"));
            }
            if(!options.isEmpty()){
                option = Arrays.asList(options.split("#%#"));
            }
            RelationLabel relationLabel = new RelationLabel();
            NlpQuestion nlpquestion = null;
            if(!options.isEmpty()){
                 nlpquestion = relationLabel.requestPackage("1", markText,subStem,
                        option,null,isAnswer);
            }
            else{
                 nlpquestion = relationLabel.requestPackage("3", markText,subStem,
                        option,null,isAnswer);
            }
            List<RelationLtp> relationShipList = relationLabel.obtainRelationsByNlp(nlpquestion);
            List<String> relationStringList = new ArrayList<>();
            List<NlpQuestion> nlpQuestions = relationLabel.obtainNlpQuestions(nlpquestion);
            for(RelationLtp re : relationShipList){
                StringBuffer sb = new StringBuffer();
                sb.append("【"+re.getEntity1()+"->"+re.getEntity_1().getTypes()+"】  【"+re.getEntity2()+re.getEntity_2().getTypes()+"】  【"
                        +re.getRelationString()+"】  属性：【");
                for(Property property : re.getProperties()){
                    sb.append(property.getPropertyName()+"->"+property.getPropertyType()+",");
                }
                sb.append("】  ").append("【asking=").append(re.getAsking()).append("】");
                relationStringList.add(sb.toString());
            }
            model.addAttribute("markText",nlpquestion.getFakeText());
            session.setAttribute("faketext",nlpquestion.getFakeText());
            model.addAttribute("relationStringList",relationStringList);
            Map<String,Entity> indexEntity = new HashMap<>();
            List<SentenceEntity> entities = new ArrayList<>();
            int j = 0;
            for(int i = 0;i<nlpQuestions.size();i++){
                List<EntityString> entityList = new ArrayList<>();
                if(i!=0) {
                    entityList.addAll(entities.get(i - 1).entityStrings);
                }
                for(Entity entity : nlpQuestions.get(i).getEntities()){
                    StringBuffer sb = new StringBuffer();
                    sb.append("【").append(entity.getName()).append("】--->【").append(entity.getTypes()).append("】");
                    if(!entityList.contains(new EntityString(sb.toString()))){
                        entityList.add(new EntityString(j+1,sb.toString()));
                        indexEntity.put(Integer.toString(j+1),entity);
                    }
                    j++;
                }
                SentenceEntity sentenceEntity = new SentenceEntity(i+1,entityList, nlpQuestions.get(i).getCommonText());
                entities.add(sentenceEntity);
            }
            model.addAttribute("entities",entities);
            session.setAttribute("entities",entities);
            session.setAttribute("nlpQuestions",nlpQuestions);
            session.setAttribute("indexEntity",indexEntity);
        }
        catch (Exception e){
            e.printStackTrace();
            return "admin/error";
        }
        return "admin/mark_relation1";
    }

    @RequestMapping(value="mark")
    public String mark(){
        return "admin/mark";
    }

    @RequestMapping(value="markSentence")
    public String markSentence(@RequestParam String sentenceId,HttpSession session,Model model){
        String faketext = (String)session.getAttribute("faketext");
        session.setAttribute("sentenceId",sentenceId);
        List<SentenceEntity> entities = (List<SentenceEntity>)session.getAttribute("entities");
        SentenceEntity sentenceEntity = entities.get(Integer.parseInt(sentenceId)-1);
        List<NlpQuestion> nlpQuestions = (List<NlpQuestion>)session.getAttribute("nlpQuestions");
        model.addAttribute("sentenceEntity",sentenceEntity);
        model.addAttribute("markText",faketext);
        return "admin/selectEntity";
    }

    @ResponseBody
    @RequestMapping(value="getRelation")
    public KnowledgeSearch getRelationByEntity(String entity1,String entity2,HttpSession session){
        entity1 = entity1.substring(0,entity1.indexOf(':'));
        entity2 = entity2.substring(0,entity2.indexOf(':'));
        RelationLabel relationLabel = new RelationLabel() ;
        Map<String,Entity> indexEntity = (Map<String, Entity>)session.getAttribute("indexEntity");
        KnowledgeSearch ks = relationLabel.obtainRelationByKnowledgeSets(indexEntity.get(entity1),indexEntity.get(entity2));
        session.setAttribute("ks",ks);
        return ks;
    }

    @RequestMapping(value="selectentity")
    public String selectentity(@RequestParam String entity1,@RequestParam String entity2,@RequestParam String commontext,
                               @RequestParam String relation,@RequestParam(required = false) String propertity,
                               @RequestParam String entity3,@RequestParam String asking,HttpSession session,Model model){
        Map<String,Entity> indexEntity = (Map<String, Entity>)session.getAttribute("indexEntity");
        List<NlpQuestion> nlpQuestions = (List<NlpQuestion>)session.getAttribute("nlpQuestions");
        String sentenceId = (String)session.getAttribute("sentenceId");
        String markText = (String)session.getAttribute("markText");
        String answer = (String)session.getAttribute("answer");
        String options = (String)session.getAttribute("options");
        String subStems = (String)session.getAttribute("subStems");
        entity1 = entity1.substring(0,entity1.indexOf(':'));
        entity2 = entity2.substring(0,entity2.indexOf(':'));
        Entity e3 = null;
        if(!entity3.isEmpty()){
            entity3 = entity3.substring(0,entity3.indexOf(':'));
            e3 = indexEntity.get(entity3);
        }
        Entity e1 = indexEntity.get(entity1);
        Entity e2 = indexEntity.get(entity2);
        KnowledgeSearch ks = (KnowledgeSearch)session.getAttribute("ks");
        RelationDate relationDate = new RelationDate(e1,e2,relation);
        relationDate.setAsking(asking.equals("0")?"":asking);
        relationDate.setCommonText(nlpQuestions.get(Integer.parseInt(sentenceId)-1).getShortSentenceReplaced());
        relationDate.setEntity3(e3);
        RelationLabel relationLabel = new RelationLabel();
        try{
            relationLabel.saveRelations(relationDate);
        }
        catch (Exception e){
            e.printStackTrace();
            return "error/error";
        }
        //为下一页面提供参数
        model.addAttribute("sentenceId",sentenceId);
        model.addAttribute("markText",markText);
        model.addAttribute("answer",answer);
        model.addAttribute("options",options);
        model.addAttribute("subStems",subStems);
        return "admin/nextStep";
    }
}
