package edu.uestc.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_request")
public class Request {

    /*
    * id           | int(11)      | NO   | PRI | NULL    | auto_increment |
| over         | int(11)      | NO   |     | NULL    |                |
| question_id  | varchar(255) | YES  |     | NULL    |                |
| request      | int(11)      | NO   |     | NULL    |                |
| stem         | text         | YES  |     | NULL    |                |
| sub_stem     | text         | YES  |     | NULL    |                |
| solve_result | text         | YES  |     | NULL*/
    @Id
    private int id;
    private String questionId;
    private int request;
    private String stem;
    private String subStem;
    private String solveResult;
    private int over;

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getSubStem() {
        return subStem;
    }

    public void setSubStem(String subStem) {
        this.subStem = subStem;
    }

    public String getSolveResult() {
        return solveResult;
    }

    public void setSolveResult(String solveResult) {
        this.solveResult = solveResult;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", questionId='" + questionId + '\'' +
                ", request=" + request +
                ", stem='" + stem + '\'' +
                ", subStem='" + subStem + '\'' +
                ", solveResult='" + solveResult + '\'' +
                '}';
    }

    public Request() {
    }
    public Request(String questionId,int request,String solveResult,int over){
        this.questionId = questionId;
        this.request = request;
        this.solveResult = solveResult;
        this.over = over;
    }
}
