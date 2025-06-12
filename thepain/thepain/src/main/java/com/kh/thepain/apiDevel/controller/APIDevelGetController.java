package com.kh.thepain.apiDevel.controller;

import com.kh.thepain.apiDevel.model.service.APIDevelService;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import com.kh.thepain.member.model.service.MemberService;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.PostList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class APIDevelGetController {

    @Autowired
    private PostListServiceImpl pService;

    @Autowired
    private MemberService mService;

    @Autowired
    private APIDevelService apiService;

    /**
     * 채용공고 전체 정보 조회
     * @return
     */
    @RequestMapping(value="/allJobList", method = RequestMethod.GET)
    //@ResponseBody -> @RestController가 있으면 셍략가능
    public ArrayList<PostList> selectAllJobPostList(){
        return pService.selectJobPostList();
    }

    /**
     * 회사 이름으로 그 회사 채용 공고 전제 조회
     * @param companyName
     */
    @GetMapping(value="/jobList/{companyName}")
    public List<JobPost> selectJobPost(@PathVariable String companyName){
        return apiService.postList(companyName);
    }

    /**
     * 회원 아이디로 회원 정보 조회 
     * 반환 데이터 바꿔줘야 됨
     * @param memberId
     * @return
     */
    @GetMapping(value="/member/{id}")
    public Member selectMember(@PathVariable String memberId){
        Member m = new Member();
        m.setEmail(memberId);
        return mService.loginController(m);
    }
}
