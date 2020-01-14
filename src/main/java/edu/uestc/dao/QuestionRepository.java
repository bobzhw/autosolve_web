package edu.uestc.dao;

import edu.uestc.po.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zw on 2019/12/14.
 */
public interface QuestionRepository extends JpaRepository<Question,Long> {
    Question findById(String id);
    List<Question> findByAreaAndYearAndCategory(String area,String year,int category);
    List<Question> findByStemLike(String key);


}
