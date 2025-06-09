package com.kh.thepain.apiDevel.model.dao;

import com.kh.thepain.apiDevel.mapper.APIDevelMapper;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class APIDevelDao {

    @Autowired
    private APIDevelMapper apiMapper;

    /**
     * 특정 회사 채용공고 전체 조회
     * @param companyName
     * @return
     */
    public List<JobPost> postList(String companyName){
        return apiMapper.postList(companyName);
    }
}
