package edu.uestc.Utils;

import edu.uestc.po.NLPResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//commonText保存每个text对应的颜色
public class EntityColor {
    static class TextColor{
        public String text;
        public String color;
        public TextColor(String text,String color){
            this.text = text;
            this.color = color;
        }
    }
    private List<TextColor> textColors = new ArrayList<>();
    private ColorEntity colorEntity = new ColorEntity();
    public List<TextColor> getTextColors() {
        return textColors;
    }

    public EntityColor(NLPResult nlpResult){
        Map<String,String> colors = new HashMap();
        for(NLPResult.Entity e : nlpResult.entities){
            for(String type : e.types){
                colors.put(type,colorEntity.getColor());
            }
        }
        String[] texts = nlpResult.commonText.split("\\$");
        for(String t : texts){
            boolean flag = false;
            for(NLPResult.Entity e : nlpResult.entities){
                if(e.name.equals(t)){
                    textColors.add(new TextColor(t+e.types,colors.get(e.types.get(0))));
                    flag = true;
                    break;
                }

            }
            if(!flag){
                textColors.add(new TextColor(t,"black"));
            }
        }
    }

    public EntityColor(){

    }
}
