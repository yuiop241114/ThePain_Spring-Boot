package com.kh.thepain.apiDevel.controller;

import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.PostList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class APIDevelController {

    @Autowired
    private PostListServiceImpl pService;

    /**
     * 채용공고 전체 정보 조회
     * @return
     */
    @RequestMapping(value="/allJobList", method = RequestMethod.GET)
    //@ResponseBody -> @RestController가 있으면 셍략가능
    public ArrayList<PostList> selectAllJobPostList(){
        return pService.selectJobPostList();
    }

    @GetMapping(value="/jobList/{companyName}", method = RequestMethod.GET)
    public void selectJobPost(@PathVariable String companyName){
        //회사이름으로 회사번호 조회 후 회사번호로 채용담당자 마이페이지에서 채용공고 조회하는 메소드 찾아서 가쟈옴
    }
}
