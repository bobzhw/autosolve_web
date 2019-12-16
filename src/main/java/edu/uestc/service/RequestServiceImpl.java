package edu.uestc.service;

import edu.uestc.dao.RequestRepository;
import edu.uestc.po.Question;
import edu.uestc.po.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Request queryRequest(String questionId, int request, int over) {
        return requestRepository.findByQuestionIdAndRequestAndOver(questionId,request,over);
    }

    @Override
    public void deleteRequest(String questionId, int request, int over) {
        requestRepository.deleteByQuestionIdAndRequestAndOver(questionId,request,over);
    }
}
