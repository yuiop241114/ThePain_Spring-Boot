package com.kh.thepain.myPage.myPageMapper;

import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface MyPageMapper {

    ArrayList<Attachment> resumeList(Member m);

    int resumeInsert(Attachment fileVo);

    int resumeInsertAs(Attachment fileVo);

    int resumeDelete(ArrayList<Apply> resumeList);

    int selectLatestResumeFileNo(int memberNo);

    int selectApplyCount(Apply apply);

    String companyName(int enterpriseNo);

    ArrayList<PostWrite> postWriteList(int memberNo);

    PostList postWriteInfo(int postNo);

    ArrayList<Apply> applyList(int postNo);

    ArrayList<Member> memberList(ArrayList<Apply> applyList);

    String fileName(int fileNo);

    String selectLatestProfileByMemberNo(int memberNo);

    int deleteProfileByMemberNo(int memberNo);

    Apply memberResumeInfo(Member m);

    Attachment selectLatestProfileByMemberNo(Attachment fileVo);

    int applyPassed(@Param("passed") int passed,
                    @Param("applyNum") int applyNum,
                    @Param("postNo") int postNo);

    Attachment applyResumeSelect(Member m);

    int selectApplyResume(int resumeNum);

    Attachment selectImg(int postNo);
}
