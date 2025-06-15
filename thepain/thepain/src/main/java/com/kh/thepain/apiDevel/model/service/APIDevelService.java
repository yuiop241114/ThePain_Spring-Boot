package com.kh.thepain.apiDevel.model.service;

import com.kh.thepain.apiDevel.model.dao.APIDevelDao;
import com.kh.thepain.apiDevel.model.vo.ApiMember;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import com.kh.thepain.apiDevel.model.vo.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class APIDevelService implements UserDetailsService {

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


    /**'
     * UserDetail에 있는 사용자 정보(username)을 가져오기 위한 메소드
     * UserDeatailService 인터페이스가 가지고 있는 메소드
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiMember apiMember = apiDao.selectApiMember(username);
        if(apiMember == null) throw new UsernameNotFoundException("해당 하는 사용자는 없음");
        return new org.springframework.security.core.userdetails.User(apiMember.getEmail(), "", Collections.emptyList());
    }
}
