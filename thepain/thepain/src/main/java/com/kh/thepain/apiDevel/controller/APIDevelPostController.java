package com.kh.thepain.apiDevel.controller;

import com.kh.thepain.apiDevel.jwtConfig.JwtUtil;
import com.kh.thepain.apiDevel.model.vo.ApiLogin;
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

}
