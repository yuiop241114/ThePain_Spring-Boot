package com.kh.thepain.apiDevel.controller;

import com.kh.thepain.apiDevel.jwtConfig.JwtUtil;
import com.kh.thepain.apiDevel.model.service.APIDevelService;
import com.kh.thepain.apiDevel.model.vo.ApiLogin;
import com.kh.thepain.apiDevel.model.vo.ApiMember;
import com.kh.thepain.postList.controller.Post;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/postApi")
public class APIDevelPostController {

    @Autowired
    private Post pController;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private APIDevelService apiService;

    /**
     * 로그인시 토큰 발생 메소드
     * @param apiLogin
     * @return
     */
    @PostMapping(value="/loginToken")
    public String loginToken(@RequestBody ApiLogin apiLogin) {
        //입력 받은 아이디 및 비밀번호 인증
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(apiLogin.getEmail(), apiLogin.getPassword())
        );
        //아이디로 토큰 발행 및 반환
        return jwtUtil.generateToken(authentication.getName());
    }

    @PostMapping(value="/insertPost")
    public void insertPost(@RequestHeader("Authorization") String bearerToken){
        //이미지 및 채용공고 정보 받아서 등록
        String token = bearerToken.substring(7); //헤더에서 Token 추출
        String email = jwtUtil.getUsernameFromToken(token); //Token에 있는 아이디 추출

        //채용담당자인지 확인
        ApiMember memberInfo = apiService.selectApiMember(email);
        if(!memberInfo.getGitUserName().equals("")){
            System.out.println("깃허브 회원");
        }else{
            System.out.println("채용담당자");
        }
    }

}
