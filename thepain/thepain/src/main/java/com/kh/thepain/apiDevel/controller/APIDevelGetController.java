package com.kh.thepain.apiDevel.controller;

import com.kh.thepain.apiDevel.jwtConfig.JwtFilter;
import com.kh.thepain.apiDevel.model.service.APIDevelService;
import com.kh.thepain.apiDevel.model.vo.ApiMember;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import com.kh.thepain.member.model.service.MemberService;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.PostList;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/getApi")
public class APIDevelGetController {

    @Autowired
    private PostListServiceImpl pService;

    @Autowired
    private MemberService mService;

    @Autowired
    private APIDevelService apiService;

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * 채용공고 전체 정보 조회
     * @return
     */
    @Operation(summary = "채용공고 전체 정보 조회", description = "Thepain 사이트에 등록된 모든 채용공고를 최신순서대로 조회합니다.")
    @GetMapping(value="/allJobList")
    //@ResponseBody -> @RestController가 있으면 셍략가능
    public ArrayList<PostList> selectAllJobPostList(){
        return pService.selectJobPostList();
    }

    /**
     * 회사 이름으로 그 회사 채용 공고 전체 조회
     * @param companyName
     */
    @Operation(summary = "특정 회사 채용 공고 조회", description = "특정 회사 이름을 이용하여 그 회사의 채용공고 전체를 조회합니다.")
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
    @Operation(summary = "회원정보 조회", description = "Thepain 사이트의 회원 아이디 및 비밓번호를 사용하여 그 회원의 정보를 조회합니다.")
    @GetMapping(value="/member/{memberId}")
    public ApiMember selectMember(@PathVariable String memberId){
        //회원 정보 조회
        //1. 회원 기본 정보
        //2. 회원 기술 스택 리스트
        ApiMember m = apiService.selectApiMember(memberId);
        m.setSkills( apiService.selectSkills( m.getMemberNo()) );
        return m;
    }
}
