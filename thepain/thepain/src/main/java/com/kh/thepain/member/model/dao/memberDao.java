package com.kh.thepain.member.model.dao;

import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.vo.Apply;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class memberDao {
	
	/**
	 * @param sqlSession
	 * @param m
	 * @return
	 * 로그인 Dao
	 */
	public Member loginController(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.loginMember", m);
	}
	
	/**
	 * @param sqlSession
	 * @param m
	 * @return
	 * 입력한 회사 이름 등록 전 있는 회사인지 확인
	 */
	public int selectDupEnterprise(SqlSessionTemplate sqlSession, String enterpriseName) {
		return sqlSession.selectOne("memberMapper.selectDupEnterprise", enterpriseName);
	}
	
	/**
	 * @param sqlSession
	 * @param enterpriseName
	 * @return
	 * 입력한 회사 이름 등록 Dao
	 */
	public int insertEnterprise(SqlSessionTemplate sqlSession, String enterpriseName) {
		return sqlSession.insert("memberMapper.insertEnterprise", enterpriseName);
	}
	
	/**
	 * @param sqlSession
	 * @param enterpriseName
	 * @return
	 * 입력한 회사 이름 번호 조회 Dao
	 */
	public int selectEnterprise(SqlSessionTemplate sqlSession, String enterpriseName) {
		return sqlSession.selectOne("memberMapper.selectEnterprise", enterpriseName);
		
	}
	
	/**
	 * @param sqlSession
	 * @param m
	 * @return
	 * 회원가입 Dao
	 */
	public int memberEnroll(SqlSessionTemplate sqlSession, Member m) { // 채용담당자
		return sqlSession.insert("memberMapper.memberEnroll", m);
	}
	
	public int checkMember(SqlSessionTemplate sqlSession, String email) {
		return sqlSession.selectOne("memberMapper.checkMember", email);
	}
	
	public int gitMemberEnroll(SqlSessionTemplate sqlSession, Member m) { // 깃허브 유저
		return sqlSession.insert("memberMapper.gitMemberEnroll", m);
	}
	
	public int userMemberEnroll(SqlSessionTemplate sqlSession, Member m) { // 일반 회원
		return sqlSession.insert("memberMapper.userMemberEnroll", m);
	}
	
	public Member selectGitMember(SqlSessionTemplate sqlSession, Member githubInfo) {
		return sqlSession.selectOne("memberMapper.selectGitMember", githubInfo);
	}
	
	public Member selectMember(SqlSessionTemplate sqlSession, Member loginMember) {
		return sqlSession.selectOne("memberMapper.selectMember", loginMember);
	}
	
	public int updateMember(SqlSessionTemplate sqlSession, Member loginMember) {
		return sqlSession.update("memberMapper.updateMember", loginMember);
	}
	
	public int insertSkill(SqlSessionTemplate sqlSession, Map<String, Object> map) {
		return sqlSession.insert("memberMapper.insertSkill", map);
	}
	
	public List<Map<String, Object>> selectSkills(SqlSessionTemplate sqlSession, Member loginMember){
		return sqlSession.selectList("memberMapper.selectSkills", loginMember);
	}
	
	public List<Map<String, Object>> skillSearch(SqlSessionTemplate sqlSession, String englishKeyword) {
	    return sqlSession.selectList("memberMapper.skillSearch", englishKeyword);
	}
	
	public int deleteMemberSkill(SqlSessionTemplate sqlSession, int memberNo, int skillNo) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("memberNo", memberNo);
	    map.put("skillNo", skillNo);

	    return sqlSession.delete("memberMapper.deleteMemberSkill", map);
	}
	
	public List<Apply> selectApplyList(SqlSessionTemplate sqlSession, Member loginMember) {
		return sqlSession.selectList("memberMapper.selectApplyList", loginMember);
	}
	
	/**
	 * @param sqlSession
	 * @param memberNo
	 * @return
	 * 채용공고에 지원한 지원자에 대한 정보 조회
	 */
	public Member resumeMemberInfo(SqlSessionTemplate sqlSession, Member memberInfo) {
		return sqlSession.selectOne("memberMapper.resumeMemberInfo", memberInfo);
	}
	
}