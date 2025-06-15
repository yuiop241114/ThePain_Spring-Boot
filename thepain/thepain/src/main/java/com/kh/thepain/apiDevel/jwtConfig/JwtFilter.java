package com.kh.thepain.apiDevel.jwtConfig;

import com.kh.thepain.apiDevel.model.service.APIDevelService;
import com.kh.thepain.apiDevel.model.vo.ApiMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {

    private JwtUtil jwtUtil;

    private APIDevelService apiService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        //Header에 있는 Authorization 추출
        String authHeader = httpReq.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) { //헤더가 있는지 Bearer 가 있는 지 확인
            String token = authHeader.substring(7); //Bearer에 띄어쓰기를 포함한 부분 이후 잘라서 Token을 가져옴
            if (jwtUtil.validateToken(token)) { //Token 검증
                String username = jwtUtil.getUsernameFromToken(token); //Token에서 username 추출
                var userDetails = apiService.loadUserByUsername(username); //추출한 아이디로 사용자 정보
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth); //토큰에서 추출한 사용자 정보를 담아 SecurityContext에 등록
            }
        }
        filterChain.doFilter(servletRequest, servletResponse); //모든 인증 처리가 끝난 후, 다음 필터 또는 실제 API 컨트롤러로 요청을 넘김
    }

}
