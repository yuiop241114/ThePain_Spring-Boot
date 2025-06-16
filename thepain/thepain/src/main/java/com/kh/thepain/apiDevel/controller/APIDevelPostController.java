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
@RequestMapping("/api")
public class APIDevelPostController {

    @Autowired
    private Post pController;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value="/login_token")
    public String loginToken(@RequestBody ApiLogin apiLogin) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(apiLogin.getEmail(), apiLogin.getPassword())
        );
        return jwtUtil.generateToken(authentication.getName());
    }

}
