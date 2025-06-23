package com.kh.thepain.postList.postListMapper;

import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface PostListMapper {

    int PostListCount();

    ArrayList<PostList> selectJobPostList();

    ArrayList<PostList> selectPostList(RowBounds rowBounds);

    int insertJob(PostWrite pw);

    PostWrite selectJobDetail(int postNo);

    int deleteJobPost(int postNo);

    int updateJobPost(PostWrite pw);

    int insertApply(Apply apply);

    int selectApply(Apply apply);

    List<Apply> selectApplyList(int memberNo);

    int insertJobPost(PostList pl);

    int postNo(int memberNo);

    int selectSenderNo(int postNo);

    ArrayList<Attachment> selectResume(int memberNo);

    int selectAlarmByNo(int postNo);

    String alarmPostTitle(int postNo);

    int expiredJobPost();

    int expiredJobWritePost();

    ArrayList<PostList> alarmDeadLine(ArrayList<Integer> recruitmentNo);

    ArrayList<Integer> selectPostWriter(int receiverNo);

    ArrayList<PostList> selectClosedPosts(ArrayList<Integer> writerList);

    int updateReadStatus(Map<String, Integer> paramMap);

    PostList selectCompany(int recruitmentNo);

    String selectCompanyName(int companyNo);

    /**
     * 채용공고 최신순 이미지 정보 조회
     * @return
     */
    ArrayList<Attachment> imgList(int memberNo);
}
