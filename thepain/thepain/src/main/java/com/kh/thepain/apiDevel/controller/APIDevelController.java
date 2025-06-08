package com.kh.thepain.apiDevel.controller;

import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.PostList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@ResponseBody
public class APIDevelController {

    @Autowired
    private PostListServiceImpl pService;

    /**
     * 채용공고 정보 조회
     * @return
     */
    @RequestMapping(value="/jobs", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<PostList> testAPI(){
        ArrayList<PostList> jobPostListAPI = pService.selectJobPostList();
        return jobPostListAPI;
    }
}
