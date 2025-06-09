package com.kh.thepain.apiDevel.model.service;

import com.kh.thepain.apiDevel.model.dao.APIDevelDao;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APIDevelService {

    @Autowired
    private APIDevelDao apiDao;

    /**
     * 특정 회사 채용공고 조회
     * @param companyName
     * @return
     */
    public List<JobPost> postList(String companyName){
        return apiDao.postList(companyName);
    }
}
