package edu.uestc.dao;

import edu.uestc.po.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface RequestRepository extends JpaRepository<Request,Long>{
    @Query(value="select * from t_request where question_id=?1 and request=?2 and over=?3",nativeQuery = true)
    Request findByQuestionIdAndRequestAndOver(String questionId,int request,int over);
    @Transactional
    @Modifying
    @Query(value = "delete from t_request where question_id=?1 and request=?2 and over=?3",nativeQuery = true)
    void deleteByQuestionIdAndRequestAndOver(String questionId,int request,int over);

}
