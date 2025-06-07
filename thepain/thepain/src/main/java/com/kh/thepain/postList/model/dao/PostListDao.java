package com.kh.thepain.postList.model.dao;

import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.common.model.vo.PageInfo;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import com.kh.thepain.postList.postListMapper.PostListMapper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostListDao {

//	public int PostListCount(SqlSessionTemplate sqlSession) {
//
//		return sqlSession.selectOne("recruitmentMapper.selectPostListCount");
//
//	}
//
//	public ArrayList<PostList> selectJobPostList(SqlSession sqlSession) {
//		return (ArrayList) sqlSession.selectList("recruitmentMapper.selectJobPostList");
//	}
//
//	public ArrayList<PostList> PostList(SqlSessionTemplate sqlSession, PageInfo pi) {
//		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
//		int limit = pi.getBoardLimit();
//		RowBounds rowBounds = new RowBounds(offset, limit);
//		return (ArrayList) sqlSession.selectList("recruitmentMapper.selectPostList", null, rowBounds);
//	}
//
//	public int insertJob(SqlSession sqlSession, PostWrite pw) {
//		return sqlSession.insert("recruitmentMapper.insertJob", pw);
//	}
//
//	/**
//	 * 공고글 리스트 에서 한개의 글을 클릭하면 디테일 뷰로 넘어갈때 , 디테일 뷰 화면 정보 조회하는 메서드.
//	 *
//	 * @param sqlSession
//	 * @param postNo
//	 * @return
//	 */
//	public PostWrite selectJob(SqlSession sqlSession, int postNo) {
//		return sqlSession.selectOne("recruitmentMapper.selectJobDetail", postNo);
//	}
//
//	/**
//	 * 채용공고 상세 글 삭제하기 메서드.
//	 *
//	 * @param sqlSession
//	 * @param postNo
//	 * @return
//	 */
//	public int deleteJobPost(SqlSessionTemplate sqlSession, int postNo) {
//		return sqlSession.delete("recruitmentMapper.deleteJobPost", postNo);
//	}
//
//	/**
//	 * 업데이트 완료 버튼 클릭시. 채용공고 글 update
//	 *
//	 * @param sqlSession
//	 * @param pw
//	 * @return
//	 */
//	public int updateJobPost(SqlSessionTemplate sqlSession, PostWrite pw) {
//		return sqlSession.update("recruitmentMapper.updateJobPost", pw);
//	}
//
//	public int insertApply(SqlSessionTemplate sqlSession, Apply apply) {
//		return sqlSession.insert("recruitmentMapper.insertApply", apply);
//	}
//
//	public int selectApply(SqlSessionTemplate sqlSession, Apply apply) {
//		return sqlSession.selectOne("recruitmentMapper.selectApply", apply);
//	}
//
//	public List<Apply> selectApplyList(SqlSessionTemplate sqlSession, int memberNo) {
//		return sqlSession.selectList("recruitmentMapper.selectApplyList", memberNo);
//	}
//
//	public int insertJobPost(SqlSessionTemplate sqlSession, PostList pl) {
//		return sqlSession.insert("recruitmentMapper.insertJobPost", pl);
//	}
//
//	public int postNo(SqlSession sqlSession, int memberNo) {
//		return sqlSession.selectOne("recruitmentMapper.postNo", memberNo);
//	}
//
//	/**
//	 * 알람 소켓 작동시, 현 채용공고 글 작성자 memberNo 조회용 메서드.
//	 *
//	 * @param sqlSession
//	 * @param postNo
//	 * @return
//	 */
//	public int selectSenderNo(SqlSession sqlSession, int postNo) {
//		return sqlSession.selectOne("recruitmentMapper.selectSenderNo", postNo);
//	}
//
//	public ArrayList<Attachment> selectResume(SqlSession sqlSession, int memberNo) {
//		return (ArrayList) sqlSession.selectList("recruitmentMapper.selectResume", memberNo);
//	}
//
//	/**
//	 * 글 작성자(senderNo) 를 알아내기 위한 메서드
//	 *
//	 * @param sqlSession
//	 * @param alarmNo
//	 * @return
//	 */
//	public int senderNo(SqlSessionTemplate sqlSession, int postNo) {
//		return sqlSession.selectOne("recruitmentMapper.selectAlarmByNo", postNo);
//	}
//
//	/**
//	 * 알람 - 공고글 타이틀 조회용 메서드
//	 *
//	 * @param sqlSession
//	 * @param postNo
//	 * @return
//	 */
//	public String alarmPostTitle(SqlSessionTemplate sqlSession, int postNo) {
//		return sqlSession.selectOne("recruitmentMapper.alarmPostTitle", postNo);
//	}
//
//	/**
//	 * 데드라인 기준 자동 status 변경 메서드.
//	 *
//	 * @param sqlSession
//	 * @return
//	 */
//	public int expiredJobPost(SqlSessionTemplate sqlSession) {
//		return sqlSession.update("recruitmentMapper.expiredJobPost");
//
//	}
//
//	/**
//	 * 데드라인 기준 자동 status 변경 메서드.
//	 *
//	 * @param sqlSession
//	 * @return
//	 */
//	public int expiredJobWritePost(SqlSessionTemplate sqlSession) {
//		return sqlSession.update("recruitmentMapper.expiredJobWritePost");
//	}
//
//	/**
//	 * 000공고글이 마감일 까지 *일 남았습니다. 확인해보세요! 메세지 출력메서드
//	 *
//	 * @param sqlSession
//	 * @return
//	 */
//	public ArrayList<PostList> alarmDeadLine(SqlSessionTemplate sqlSession, ArrayList<Integer> recruitmentNo) {
//		return (ArrayList) sqlSession.selectList("recruitmentMapper.alarmDeadLine", recruitmentNo);
//	}
//
//	/**
//	 * 공고가 마감되었습니다. 다음 기회를 기대해 주세요. 지원한 공고글 no 알아내는 메서드.
//	 *
//	 * @param sqlSession
//	 * @param receiverNo
//	 * @return
//	 */
//	public ArrayList<Integer> selectPostWriter(SqlSessionTemplate sqlSession, int receiverNo) {
//		return (ArrayList) sqlSession.selectList("recruitmentMapper.selectPostWriter", receiverNo);
//	}
//
//	/**
//	 * 공고가 마감되었습니다. 다음 기회를 기대해 주세요. 지원한 공고글 no 알아내는 메서드.
//	 *
//	 * @param sqlSession
//	 * @param receiverNo
//	 * @return
//	 */
//	public ArrayList<PostList> selectClosedPosts(SqlSessionTemplate sqlSession, ArrayList<Integer> writerList) {
//		return (ArrayList) sqlSession.selectList("recruitmentMapper.selectClosedPosts", writerList);
//	}
//
//	public int updateReadStatus(SqlSessionTemplate sqlSession, int recruitmentNo, int memberNo) {
//		Map<String, Integer> map = new HashMap<>();
//		map.put("recruitmentNo", recruitmentNo);
//		map.put("memberNo", memberNo);
//		return sqlSession.update("recruitmentMapper.updateReadStatus", map);
//	}
//
//	public PostList selectCompany(SqlSessionTemplate sqlSession, int recruitmentNo) {
//		return sqlSession.selectOne("recruitmentMapper.selectCompany", recruitmentNo);
//	}
//
//	/**
//	 * @param sqlSession
//	 * @param companyNo
//	 * @return 회사 번호로 회사 이름 조회 Dao
//	 */
//	public String selectCompanyName(SqlSessionTemplate sqlSession, int companyNo) {
//		return sqlSession.selectOne("recruitmentMapper.selectCompanyName", companyNo);
//	}

	@Autowired
	private PostListMapper plMapper;

	public int PostListCount() {
		return plMapper.PostListCount();
	}

	public ArrayList<PostList> selectJobPostList() {
		return plMapper.selectJobPostList();
	}

	public ArrayList<PostList> PostList(PageInfo pi) {
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage() - 1) * pi.getBoardLimit(), pi.getBoardLimit());
		return plMapper.selectPostList(rowBounds);
	}

	public int insertJob(PostWrite pw) {
		return plMapper.insertJob(pw);
	}

	public PostWrite selectJob(int postNo) {
		return plMapper.selectJobDetail(postNo);
	}

	public int deleteJobPost(int postNo) {
		return plMapper.deleteJobPost(postNo);
	}

	public int updateJobPost(PostWrite pw) {
		return plMapper.updateJobPost(pw);
	}

	public int insertApply(Apply apply) {
		return plMapper.insertApply(apply);
	}

	public int selectApply(Apply apply) {
		return plMapper.selectApply(apply);
	}

	public List<Apply> selectApplyList(int memberNo) {
		return plMapper.selectApplyList(memberNo);
	}

	public int insertJobPost(PostList pl) {
		return plMapper.insertJobPost(pl);
	}

	public int postNo(int memberNo) {
		return plMapper.postNo(memberNo);
	}

	public int selectSenderNo(int postNo) {
		return plMapper.selectSenderNo(postNo);
	}

	public ArrayList<Attachment> selectResume(int memberNo) {
		return plMapper.selectResume(memberNo);
	}

	public int senderNo(int postNo) {
		return plMapper.selectAlarmByNo(postNo);
	}

	public String alarmPostTitle(int postNo) {
		return plMapper.alarmPostTitle(postNo);
	}

	public int expiredJobPost() {
		return plMapper.expiredJobPost();
	}

	public int expiredJobWritePost() {
		return plMapper.expiredJobWritePost();
	}

	public ArrayList<PostList> alarmDeadLine(ArrayList<Integer> recruitmentNo) {
		return plMapper.alarmDeadLine(recruitmentNo);
	}

	public ArrayList<Integer> selectPostWriter(int receiverNo) {
		return plMapper.selectPostWriter(receiverNo);
	}

	public ArrayList<PostList> selectClosedPosts(ArrayList<Integer> writerList) {
		return plMapper.selectClosedPosts(writerList);
	}

	public int updateReadStatus(int recruitmentNo, int memberNo) {
		Map<String, Integer> map = new HashMap<>();
		map.put("recruitmentNo", recruitmentNo);
		map.put("memberNo", memberNo);
		return plMapper.updateReadStatus(map);
	}

	public PostList selectCompany(int recruitmentNo) {
		return plMapper.selectCompany(recruitmentNo);
	}

	public String selectCompanyName(int companyNo) {
		return plMapper.selectCompanyName(companyNo);
	}
}