package com.kh.thepain.apiDevel.model.dao;

import com.kh.thepain.apiDevel.mapper.APIDevelMapper;
import com.kh.thepain.apiDevel.model.vo.ApiLogin;
import com.kh.thepain.apiDevel.model.vo.ApiMember;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import com.kh.thepain.apiDevel.model.vo.Skills;
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

    /**
     * 특정 회원 기본 정보 조회
     * @param memberId
     * @return
     */
    public ApiMember selectApiMember(String memberId){ return apiMapper.selectApiMember(memberId); }

    /**
     * 특정 회원 기슬 스택 조회
     * @param memberNo
     * @return
     */
    public List<Skills> selectSkills(int memberNo){ return apiMapper.selectSkills(memberNo); }

    /**
     * 로그인 후 토큰 발생 시 인증에 필요한 이메일 및 비밓번호 조회
     * @param username
     * @return
     */
    public ApiLogin selectApiLogin(String username) { return apiMapper.selectApiLogin(username); }
}
