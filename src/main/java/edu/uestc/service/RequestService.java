package edu.uestc.service;

import edu.uestc.po.Request;

public interface RequestService {
    Request queryRequest(String questionId,int request,int over);

    void deleteRequest(String questionId,int request,int over);
}
