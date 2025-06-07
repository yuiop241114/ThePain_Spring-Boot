package com.kh.thepain.myPage.model.dao;

import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.myPage.myPageMapper.MyPageMapper;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MypageDao {

//	/**
//	 * @param sqlSession
//	 * @param m
//	 * @return
//	 * 해당 회원의 이력서 정보 리스트 조회 Dao
//	 */
//	public ArrayList<Attachment> resumeList(SqlSessionTemplate sqlSession, Member m) {
//		return (ArrayList)sqlSession.selectList("mypageMapper.resumeList", m);
//	}
//	/**
//	 * @param sqlSession
//	 * @param fileVo
//	 * @return
//	 * 이력서 정보 attachment에 insert Dao
//	 */
//	public int resumeInsert(SqlSessionTemplate sqlSession, Attachment fileVo) {
//		return sqlSession.insert("mypageMapper.resumeInsert", fileVo);
//	}
//	/**
//	 * @param sqlSession
//	 * @param fileVo
//	 * @return
//	 * 공고 지원 이력서 정보 attachment에 insert Dao
//	 */
//	public int resumeInsertAs(SqlSessionTemplate sqlSession, Attachment fileVo) {
//		return sqlSession.insert("mypageMapper.resumeInsertAs", fileVo);
//	}
//
//	public int resumeDelete(SqlSessionTemplate sqlSession, ArrayList<Apply> resumeList) {
//		//마이바티스의 동적 SQL 방식을 이용해서
//		//IN 문법을 이용해서 여러개를 처리할 수 있다
//		//ArrayList를 넘겨서 foreach문을 이용해서 사용 가능
//		return sqlSession.delete("mypageMapper.resumeDelete", resumeList);
//	}
//
//	public int selectLatestResumeFileNo(SqlSessionTemplate sqlSession, int memberNo) {
//		return sqlSession.selectOne("mypageMapper.selectLatestResumeFileNo", memberNo);
//	}
//
//	public int selectApplyCount(SqlSessionTemplate sqlSession, Apply apply) {
//		return sqlSession.selectOne("mypageMapper.selectApplyCount", apply);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param enterpriseNo
//	 * @return
//	 * 채용담당자의 회사 이름 조회 Dao
//	 */
//	public String companyName(SqlSessionTemplate sqlSession, int enterpriseNo) {
//		return sqlSession.selectOne("mypageMapper.companyName", enterpriseNo);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param enterpriseNo
//	 * @return
//	 * 채용담당자의 회사 이름 및 채용공고 내용 조회 Dao
//	 */
//	public ArrayList<PostWrite> postWriteList(SqlSessionTemplate sqlSession, int memberNo) {
//		return (ArrayList)sqlSession.selectList("mypageMapper.postWriteList", memberNo);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param memberNo
//	 * @return
//	 * 해당 채용공고 정보 조회 Dao
//	 */
//	public PostList postWriteInfo(SqlSessionTemplate sqlSession, int postNo) {
//		return sqlSession.selectOne("mypageMapper.postWriteInfo", postNo);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param postNo
//	 * @return
//	 * 해당 채용공고에 지원한 지원자 정보 조회 Dao
//	 */
//	public ArrayList<Apply> applyList(SqlSessionTemplate sqlSession, int postNo){
//		return (ArrayList)sqlSession.selectList("mypageMapper.applyList", postNo);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param applyList
//	 * @return
//	 * 해당 채용공고에 지원한 지원자의 회원 정보 조회 Dao
//	 */
//	public ArrayList<Member> memberList(SqlSessionTemplate sqlSession, ArrayList<Apply> applyList){
//		return (ArrayList)sqlSession.selectList("mypageMapper.memberList", applyList);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param fileNo
//	 * @return
//	 * 해당 지원자가 제출한 이력서 이름 조회 Dao
//	 */
//	public String fileName(SqlSessionTemplate sqlSession, int fileNo) {
//		return sqlSession.selectOne("mypageMapper.fileName", fileNo);
//	}
//	public String selectLatestProfileByMemberNo(SqlSessionTemplate sqlSession, int memberNo) {
//	    return sqlSession.selectOne("mypageMapper.selectLatestProfileByMemberNo", memberNo);
//	}
//
//	public int deleteProfileByMemberNo(SqlSessionTemplate sqlSession, int memberNo) {
//		return sqlSession.delete("mypageMapper.deleteProfileByMemberNo", memberNo);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param m
//	 * @return
//	 * 해당 채용공고에 지원한 지원자의 이력서 번호 조회 Dao
//	 */
//	public Apply memberResumeInfo(SqlSessionTemplate sqlSession, Member m) {
//		//memberNo : 지원자 회원번호, career : 채용공고 번호
//		return sqlSession.selectOne("mypageMapper.memberResumeInfo", m);
//	}
//
//	public Attachment selectLatestProfileByMemberNo(SqlSessionTemplate sqlSession, Attachment fileVo) {
//		return sqlSession.selectOne("mypageMapper.selectLatestProfileByMemberNo", fileVo);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param passed
//	 * @return
//	 * 지원자 합격 불합격 처리 Dao
//	 */
//	public int applyPassed(SqlSessionTemplate sqlSession, int passed, int applyNum, int postNo) {
//		Map<String, Object> applyInfo = new HashMap();
//		applyInfo.put("passed", passed);
//		applyInfo.put("applyNum", applyNum);
//		applyInfo.put("postNo", postNo);
//		return sqlSession.update("mypageMapper.applyPassed", applyInfo);
//	}
//
//	/**
//	 * @param Member m = 채용공고 지원한 회원 정보
//	 * @return
//	 * 채용공고 지원한 회원 이력서 파일 다운 정보 조회
//	 */
//	public Attachment applyResumeSelect(SqlSessionTemplate sqlSession, Member m) {
//	    return sqlSession.selectOne("mypageMapper.applyResumeSelect", m);
//	}
//
//
//	/**
//	 * @param sqlSession
//	 * @param resumeNumList
//	 * @return
//	 * 이력서 삭제 전 채용공고에 사용했는지 조회 Dao
//	 */
//	public int selectApplyResume(SqlSessionTemplate sqlSession, int resumeNum) {
//		return sqlSession.selectOne("mypageMapper.selectApplyResume", resumeNum);
//	}
//
//	/**
//	 * 채용공고 회사 이미지 호출
//	 * @param sqlSession
//	 * @param resumeNum
//	 * @return
//	 */
//	public Attachment selectImg(SqlSessionTemplate sqlSession, int postNo) {
//		return sqlSession.selectOne("mypageMapper.selectImg", postNo);
//	}

	@Autowired
	private MyPageMapper myMapper;

	public ArrayList<Attachment> resumeList(Member m) {
		return myMapper.resumeList(m);
	}

	public int resumeInsert(Attachment fileVo) {
		return myMapper.resumeInsert(fileVo);
	}

	public int resumeInsertAs(Attachment fileVo) {
		return myMapper.resumeInsertAs(fileVo);
	}

	public int resumeDelete(ArrayList<Apply> resumeList) {
		return myMapper.resumeDelete(resumeList);
	}

	public int selectLatestResumeFileNo(int memberNo) {
		return myMapper.selectLatestResumeFileNo(memberNo);
	}

	public int selectApplyCount(Apply apply) {
		return myMapper.selectApplyCount(apply);
	}

	public String companyName(int enterpriseNo) {
		return myMapper.companyName(enterpriseNo);
	}

	public ArrayList<PostWrite> postWriteList(int memberNo) {
		return myMapper.postWriteList(memberNo);
	}

	public PostList postWriteInfo(int postNo) {
		return myMapper.postWriteInfo(postNo);
	}

	public ArrayList<Apply> applyList(int postNo) {
		return myMapper.applyList(postNo);
	}

	public ArrayList<Member> memberList(ArrayList<Apply> applyList) {
		return myMapper.memberList(applyList);
	}

	public String fileName(int fileNo) {
		return myMapper.fileName(fileNo);
	}

	public String selectLatestProfileByMemberNo(int memberNo) {
		return myMapper.selectLatestProfileByMemberNo(memberNo);
	}

	public int deleteProfileByMemberNo(int memberNo) {
		return myMapper.deleteProfileByMemberNo(memberNo);
	}

	public Apply memberResumeInfo(Member m) {
		return myMapper.memberResumeInfo(m);
	}

	public Attachment selectLatestProfileByMemberNo(Attachment fileVo) {
		return myMapper.selectLatestProfileByMemberNo(fileVo);
	}

	public int applyPassed(int passed, int applyNum, int postNo) {
		return myMapper.applyPassed(passed, applyNum, postNo);
	}

	public Attachment applyResumeSelect(Member m) {
		return myMapper.applyResumeSelect(m);
	}

	public int selectApplyResume(int resumeNum) {
		return myMapper.selectApplyResume(resumeNum);
	}

	public Attachment selectImg(int postNo) {
		return myMapper.selectImg(postNo);
	}
}
