package com.kh.thepain.apiDevel.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
public class APIDevelController {

    @RequestMapping(value="/jobs", method = RequestMethod.GET)
    @ResponseBody
    public String testAPI(){
        return "API 개발전 테스트";
    }
}
