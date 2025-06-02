package com.kh.thepain.myPage.model.service;

import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.myPage.model.dao.MypageDao;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MypageService {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MypageDao mDao;
	
	/**
	 * @param m
	 * @return
	 * 해당 회원의 이력서 파일 정보 조회 서비스 메소드
	 */
	public ArrayList<Attachment> resumeList(Member m) {
		return mDao.resumeList(sqlSession, m);
	}

	/**
	 * @param fileVo
	 * 이력서 inset 서비스 메소드
	 */
	public int resumeInsert(Attachment fileVo) {
		return mDao.resumeInsert(sqlSession, fileVo);
	}
	/**
	 * @param fileVo
	 * 채용 공고 이력서 inset 서비스 메소드
	 */
	public int resumeInsertAs(Attachment fileVo) {
		return mDao.resumeInsertAs(sqlSession, fileVo);
	}
	
	/**
	 * @param resumeList
	 * @return
	 * 이력서 첨부파일 삭제 서비스 메소드
	 */
	public int resumeDelete(ArrayList<Apply> resumeList) {
		return mDao.resumeDelete(sqlSession, resumeList);
	}
	
	public int selectLatestResumeFileNo(int memberNo) {
		return mDao.selectLatestResumeFileNo(sqlSession, memberNo);
	}
	
	public int selectApplyCount(Apply apply) {
		return mDao.selectApplyCount(sqlSession, apply);
	}
	/**
	 * @param enterpriseNo
	 * @return
	 * 채용담당자 회사 이름 조회 서비스 메소드
	 */
	public String companyName(int enterpriseNo) {
		return mDao.companyName(sqlSession, enterpriseNo);
	}
	
	/**
	 * @param m
	 * @return
	 * 채용담당자의 회사 이름 및 채용공고 내용 조회 서비스 메소드
	 */
	public ArrayList<PostWrite> postWriteList(int memberNo) {
		return mDao.postWriteList(sqlSession, memberNo);
	}
	
	/**
	 * @param postNo
	 * @return
	 * 해당 채용공고 정보 조회 서비스 메소드
	 */
	public PostList postWriteInfo(int postNo) {
		return mDao.postWriteInfo(sqlSession, postNo);
	}
	
	/**
	 * @param postNo
	 * @return
	 * 해당 채용공고에 지원한 지원자 정보 조회 서비스 메소드
	 */
	public ArrayList<Apply> applyList(int postNo){
		return mDao.applyList(sqlSession, postNo);
	}
	
	/**
	 * @param applyList
	 * @return
	 * 해당 채용공고에 지원한 지원자의 회원 정보 조회 서비스 메소드
	 */
	public ArrayList<Member> memberList(ArrayList<Apply> applyList){
		return mDao.memberList(sqlSession, applyList);
	}
	
	/**
	 * @param fileNo
	 * @return
	 * 해당 지원자가 제출한 이력서 이름 조회 서비스 메소드
	 */
	public String fileName(int fileNo) {
		return mDao.fileName(sqlSession, fileNo);
	}
	
	public String selectLatestProfileByMemberNo(int memberNo) {
	    return mDao.selectLatestProfileByMemberNo(sqlSession, memberNo);
	}
	
	public int deleteProfileByMemberNo(int memberNo) {
		return mDao.deleteProfileByMemberNo(sqlSession, memberNo);
	}
	
	/**
	 * @param m
	 * @return
	 * 해당 채용공고에 지원한 지원자의 이력서 번호 조회 서비스 메소드
	 */
	public Apply memberResumeInfo(Member m) {
		return mDao.memberResumeInfo(sqlSession, m);
	}
	
	public Attachment selectLatestProfileByMemberNo(Attachment fileVo) {
		return mDao.selectLatestProfileByMemberNo(sqlSession, fileVo);
	}
	
	/**
	 * @param passed
	 * @return
	 * 지원자 합격 불합격 처리 서비스 메소드
	 */
	public int applyPassed(int passed, int postNo, int applyNum) {
		return mDao.applyPassed(sqlSession, passed, applyNum, postNo);
	}
	
	/**
	 * @param resumeNumList
	 * @return
	 * 이력서 삭제 전 채용공고에 사용했는지 조회하는 서비스 메소드
	 */
	public ArrayList<Apply> selectApplyResume(ArrayList<Apply> resumeList) {
		//현재 사용중인 이력서일 경우 제거 대상에서 제외
		int result = 0;
		ArrayList<Apply> deleteResumeList = new ArrayList<Apply>();
		for(int i=0; i<resumeList.size(); i++) {
			//apply_state에서 지원서로 등록중인 이력서를 count 집계함수로 조회
			result = mDao.selectApplyResume(sqlSession, resumeList.get(i).getFileNo());
			if(result == 0) { //0 => 사용한적 없는 이력서
				deleteResumeList.add(resumeList.get(i));
			}
		}
		return deleteResumeList;
	}
	
	/**
	 * @param Member m = 채용공고 지원한 회원 정보
	 * @return
	 * 채용공고 지원한 회원 이력서 파일 다운 정보 조회
	 */
	public Attachment applyResumeSelect(Member m) {
	    return mDao.applyResumeSelect(sqlSession, m);
	}
	
	/**
	 * 채용공고 회사 이미지 호출. 
	 * @param postNo
	 * @return
	 */
	public Attachment selectImg(int postNo) {
	    return mDao.selectImg(sqlSession, postNo);
	}
	
	
}
