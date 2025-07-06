package com.kh.thepain.jjim.controller;

import com.kh.thepain.jjim.model.service.JjimService;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.vo.PostList;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Hidden
@Controller
public class JjimController {

    @Autowired
    private JjimService jService;

    
    @RequestMapping("jjim.me")
    public String viewJjimList(HttpSession session, Model model) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            session.setAttribute("alertMsg", "로그인 후 이용할 수 있습니다.");
            return "redirect:/"; // 메인 페이지로 리다이렉트
        }
        int memberNo = loginMember.getMemberNo();
        ArrayList<PostList> jjimList = jService.selectJjimList(memberNo);

        model.addAttribute("jjimList", jjimList);
        return "myPage/jjimList"; // 또는 "jjim/jjimList"로 경로 맞춰도 됨
    }

    
    
    
    @ResponseBody
    @RequestMapping("jjimToggle.pl")
    public String toggleJjim(@RequestParam("postNo") int postNo,HttpSession session) {
    	
        Member loginMember = (Member) session.getAttribute("loginMember");
       
        if (loginMember == null) {
            return "loginRequired";
        }

        int memberNo = loginMember.getMemberNo();

        boolean alreadyJjim = jService.isAlreadyJjim(memberNo, postNo);
        if (alreadyJjim) {//jjim 삭제
        	jService.removeJjim(memberNo, postNo);

            return "removed";
        } else {//jjim 추가.
        	jService.addJjim(memberNo, postNo);
            return "added";
        }
    }
}