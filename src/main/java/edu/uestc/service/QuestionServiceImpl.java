package edu.uestc.service;


import edu.uestc.dao.QuestionRepository;
import edu.uestc.po.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2019/12/14.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question queryByQuestionId(String id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> queryTaoti(String area, String year, String wl) {
        if(wl.equals("W"))
            return questionRepository.findByAreaAndYearAndCategory(area,year,0);
        if(wl.equals("L"))
            return questionRepository.findByAreaAndYearAndCategory(area,year,1);
        return new ArrayList<>();
    }
}
