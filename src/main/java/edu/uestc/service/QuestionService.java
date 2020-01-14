package edu.uestc.service;

import edu.uestc.po.Question;

import java.util.List;

/**
 * Created by zw on 2019/12/14.
 */
public interface QuestionService {
    Question queryByQuestionId(String id);

    List<Question> queryTaoti(String area,String year,String wl);


    List<Question> keySearch(String key);

    void modifyQuestion(String questionId,String stem2,String subStem2,String options);


}
