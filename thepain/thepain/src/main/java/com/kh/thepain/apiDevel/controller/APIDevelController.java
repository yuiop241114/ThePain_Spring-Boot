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
    @RequestMapping(value="/jobs", method = RequestMethod.GET)
    //@ResponseBody -> @RestController가 있으면 셍략가능
    public ArrayList<PostList> selectAllJobPostList(){
        return pService.selectJobPostList();
    }

    public void selectJobPost(){

    }
}
