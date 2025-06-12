package com.kh.thepain.apiDevel.model.service;

import com.kh.thepain.apiDevel.model.dao.APIDevelDao;
import com.kh.thepain.apiDevel.model.vo.ApiMember;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import com.kh.thepain.apiDevel.model.vo.Skills;
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

    /**
     * 특정 회원 기본 정보 조회
     * @param memberId
     * @return
     */
    public ApiMember selectApiMember(String memberId){ return apiDao.selectApiMember(memberId); }

    /**
     * 특정 회원 기술 스택 조회
     * @param memberNo
     * @return
     */
    public List<Skills> selectSkills(int memberNo){ return apiDao.selectSkills(memberNo); }
}
