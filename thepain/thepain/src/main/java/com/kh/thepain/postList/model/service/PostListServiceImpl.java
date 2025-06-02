package com.kh.thepain.postList.model.service;

import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.postList.model.dao.PostListDao;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostListServiceImpl {

	@Autowired
	private PostListDao PLDao;

	@Autowired
	private SqlSessionTemplate sqlSession;

	public ArrayList<PostList> selectJobPostList() {
		return PLDao.selectJobPostList(sqlSession);
	}

	public int PostListCount() {
		return PLDao.PostListCount(sqlSession);
	}

//	public ArrayList<PostList> PostList(PageInfo pi){
//		
//		return PLDao.PostList(sqlSession, pi);
//		
//	}

	public ArrayList<PostList> getPagedItems(int page) {
		return null;

	}

	// \================================================jobPost
	public int insertJob(PostWrite pw) {
		return PLDao.insertJob(sqlSession, pw);
	}

	public PostWrite selectJob(int postNo) {
		return PLDao.selectJob(sqlSession, postNo);
	}

	public int deleteJobPost(int postNo) {
		return PLDao.deleteJobPost(sqlSession, postNo);
	}

	public int updateJobPost(PostWrite pw) {
		return PLDao.updateJobPost(sqlSession, pw);
	}

	// ==================================================Apply
	public int insertApply(Apply apply) {
		return PLDao.insertApply(sqlSession, apply);
	}

	public int selectApply(Apply apply) {
		return PLDao.selectApply(sqlSession, apply);
	}

	public List<Apply> selectApplyList(int memberNo) {
		return PLDao.selectApplyList(sqlSession, memberNo);
	}

	// ==========================================================
	public int insertJobPost(PostList pl) {
		return PLDao.insertJobPost(sqlSession, pl);
	}

	public int postNo(int memberNo) {
		return PLDao.postNo(sqlSession, memberNo);
	}

	// ===========================================================
	/**
	 * 알람 소켓 작동시, 현 채용공고 글 작성자 memberNo 조회용 메서드.
	 * 
	 * @param postNo
	 * @return
	 */
	public int selectSenderNo(int postNo) {
		return PLDao.selectSenderNo(sqlSession, postNo);
	}

	public ArrayList<Attachment> selectResume(int memberNo) {
		return PLDao.selectResume(sqlSession, memberNo);
	}

	/**
	 * 글 작성자(senderNo) 를 알아내기위한 메서드
	 * 
	 * @param postNo
	 * @return
	 */
	public int senderNo(int postNo) {

		int senderNo = PLDao.senderNo(sqlSession, postNo);

		return senderNo;
	}

	public String alarmPostTitle(int postNo) {

		String Title = PLDao.alarmPostTitle(sqlSession, postNo);
		return Title;
	}


	public int expiredJobPost() {
		return PLDao.expiredJobPost(sqlSession);
	}

	public int expiredJobWritePost() {
		return PLDao.expiredJobWritePost(sqlSession);
	}

	/**
	 * 000공고글이 마감일 까지 *일 남았습니다. 확인해보세요! 메세지 출력메서드
	 * 
	 * @return
	 */
	public ArrayList<PostList> alarmDeadLine(ArrayList<Integer> recruitmentNo) {
		return PLDao.alarmDeadLine(sqlSession, recruitmentNo);
	}

	/**
	 * 알림 두번때 기능 - 채용공고 글 작성자 memberNo찾는 메서드 공고가 마감되었습니다. 다음 기회를 기대해 주세요. 지원한 공고글 no
	 * 알아내는 메서드.
	 * 
	 * @param receiverNo
	 * @return
	 */
	public ArrayList<Integer> selectPostWriter(int receiverNo) {
		return PLDao.selectPostWriter(sqlSession, receiverNo);
	}

	/**
	 * 마감공고글 알림 메세지
	 * 
	 * @param writerList
	 * @return
	 */
	public ArrayList<PostList> selectClosedPosts(ArrayList<Integer> writerList) {
		return PLDao.selectClosedPosts(sqlSession, writerList);
	}

	/**
	 * Read 'n'->'y' update쿼리 실행 메서드
	 * 
	 * @param recruitmentNo
	 * @param memberNo
	 * @return
	 */
	public int updateReadStatus(int recruitmentNo, int memberNo) {
		return PLDao.updateReadStatus(sqlSession, recruitmentNo, memberNo);
	}

	public PostList selectCompany(int recruitmentNo) {
		return PLDao.selectCompany(sqlSession, recruitmentNo);
	}

	/**
	 * @return 회사번호로 회사 이름 조회 서비스 메소드
	 */
	public String selectCompanyName(int companyNo) {
		return PLDao.selectCompanyName(sqlSession, companyNo);
	}

}