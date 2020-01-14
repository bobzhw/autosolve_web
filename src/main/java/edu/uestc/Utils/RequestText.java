package edu.uestc.Utils;

public class RequestText {
    private String condition;
    private String conclusion;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
    public RequestText(String condition, String conclusion){
        this.condition=condition;
        this.conclusion=conclusion;
    }
}
