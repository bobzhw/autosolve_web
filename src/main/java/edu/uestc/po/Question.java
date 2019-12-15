package edu.uestc.po;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_question")
public class Question {

    @Id
    private String id;
    private String area;
    private int category;
    private int questionNum;
    private int questionType;
    private Byte solved;
    private String stem;
    private String subStem;
    private String type;
    private String year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public Byte getSolved() {
        return solved;
    }

    public void setSolved(Byte solved) {
        this.solved = solved;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", category=" + category +
                ", questionNum=" + questionNum +
                ", questionType=" + questionType +
                ", solved=" + solved +
                ", stem='" + stem + '\'' +
                ", subStem='" + subStem + '\'' +
                ", type='" + type + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    public Question() {
    }

    public Question(String id,String area,int category,String stem,String subStem){
        this.id=id;
        this.area=area;
        this.category=category;
        this.stem=stem;
        this.subStem=subStem;
    }
}
