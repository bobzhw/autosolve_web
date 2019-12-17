package edu.uestc.po;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class NLPResult {
    public int type;
    public String questionId;
    public String commonText;
    public String fakeText;
    public List<Entity> entities;
    public List<SubStemRelation> stemRelations;
    public List<String> options;
    public List<Relation> solutions;

    static class Properties{
        public String propertyName;
        public String getPropertyType;
    }
    static class Relation{
        public String entity1;
        public String entity2;
        public String type1;
        public String type2;
        public String relationString;
        public List<Properties> properties;
        public  int isConlusion;

    }
    public static class Entity{
        public String name;
        public List<String> types;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    static class SubStemRelation{
        public String isQuestion;
        public List<_SubStemRelation> relations;


        static class _SubStemRelation{
            public String entity1;
            public  String entity2;
            public String type1;
            public String type2;
            public String relationString;
            public List<Properties> properties;
            public String asking;
        }
    }

    public static void main(String[] args) {
        String json="{\n" +
                "  \"commonText\": \"若复数$(1-i)(a+i)$在复平面内对应的点在第二象限,则实数$a$的取值范围是$(   )$\", \n" +
                "  \"entities\": [\n" +
                "    {\n" +
                "      \"name\": \"(1-i)(a+i)\", \n" +
                "      \"types\": [\n" +
                "        \"Express\"\n" +
                "      ]\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"rsa\", \n" +
                "      \"types\": [\n" +
                "        \"Point\"\n" +
                "      ]\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"rsb\", \n" +
                "      \"types\": [\n" +
                "        \"第二象限\"\n" +
                "      ]\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"a\", \n" +
                "      \"types\": [\n" +
                "        \"RealNumber\"\n" +
                "      ]\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"rsc\", \n" +
                "      \"types\": [\n" +
                "        \"Interval\"\n" +
                "      ]\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"(   )\", \n" +
                "      \"types\": [\n" +
                "        \"Express\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ], \n" +
                "  \"fakeText\": \"若复数$(1-i)(a+i)$在复平面内对应的点$rsa$在第二象限$rsb$,则实数$a$的区间$rsc$是$(   )$\", \n" +
                "  \"options\": [\n" +
                "    \"$A$、$(-∞,1)$\", \n" +
                "    \"$B$、$(-∞,-1)$\", \n" +
                "    \"$C$、$(1,+∞)$\", \n" +
                "    \"$D$、$(-1,+∞)$\"\n" +
                "  ], \n" +
                "  \"questionId\": \"YGPkuNLT\", \n" +
                "  \"solutions\": [], \n" +
                "  \"stemRelations\": [], \n" +
                "  \"subStemRelations\": [], \n" +
                "  \"type\": 1\n" +
                "}";
        NLPResult nlpresult = JSONObject.parseObject(json,NLPResult.class);
        for(Entity entity : nlpresult.entities){
            System.out.println(entity.name+" "+ entity.types);
        }
    }

}
