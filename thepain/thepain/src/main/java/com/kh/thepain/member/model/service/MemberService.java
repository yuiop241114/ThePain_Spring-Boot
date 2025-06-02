package com.kh.thepain.member.model.service;

import com.kh.thepain.member.model.dao.memberDao;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.vo.Apply;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberService {

	@Autowired
	private memberDao mDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**
	 * @param m
	 * @return
	 * 로그인 서비스 메소드
	 */
	public Member loginController(Member m) {
		return mDao.loginController(sqlSession, m);
	}
	
	/**
	 * @param m
	 * @return
	 * 회원가입 서비스 메소드
	 */
	public int memberEnroll(Member m) {

	    // [1] 회사명이 있는 경우에만 (채용담당자만)
	    if (m.getCorporation() != null && !m.getCorporation().isEmpty()) {
	        
	        // 회사 존재 여부 확인
	        if (mDao.selectDupEnterprise(sqlSession, m.getCorporation()) == 0) {
	            // 회사가 없으면 새로 등록
	            int result = mDao.insertEnterprise(sqlSession, m.getCorporation());
	            if (result <= 0) {
	                return 0; // 회사 등록 실패
	            }
	        }
	        
	        // 회사 번호 세팅 (기존 회사거나 새로 등록했거나)
	        m.setEnterpriseNo(mDao.selectEnterprise(sqlSession, m.getCorporation()));
	        
	        // 채용담당자 회원가입
	        return mDao.memberEnroll(sqlSession, m);
	    } else {
	        
	        // [2] 회사명 없는 경우 → 일반 사용자 or 깃허브 사용자
	        if (m.getToken() != null && !m.getToken().isEmpty()) {
	            // 깃허브 연동 회원
	            return mDao.gitMemberEnroll(sqlSession, m);
	        } else {
	            // 일반 회원
	            return mDao.userMemberEnroll(sqlSession, m);
	        }
	    }
	}
	
	public int checkMember(String email) {
		return mDao.checkMember(sqlSession, email);
	}

	public Member selectGitMember(Member githubInfo) {
		return mDao.selectGitMember(sqlSession, githubInfo);
	}
	
	public Member selectMember(Member loginMember) {
		return mDao.selectMember(sqlSession, loginMember);
	}
	
	public int updateMember(Member loginMember) {
		return mDao.updateMember(sqlSession, loginMember);
	}
	
	public int insertSkill(Map<String, Object> map) {
		return mDao.insertSkill(sqlSession, map);
	}
	
	public List<Map<String, Object>> selectSkills(Member loginMember) {
	    return mDao.selectSkills(sqlSession, loginMember);
	}
	
	public List<Map<String, Object>> skillSearch(String englishKeyword) {
		return mDao.skillSearch(sqlSession, englishKeyword);
	}
	
	public int deleteMemberSkill(int memberNo, int skillNo) {
	    return mDao.deleteMemberSkill(sqlSession, memberNo, skillNo);
	}
	
	public List<Apply> selectApplyList(Member loginMember) {
		return mDao.selectApplyList(sqlSession, loginMember);
	}
	
	/**
	 * @param memberNo
	 * @return
	 * 채용공고에 지원한 지원자에 대한 정보 조회
	 */
	public Member resumeMemberInfo(Member memberInfo) {
		return mDao.resumeMemberInfo(sqlSession, memberInfo);
	}
	
}
