package com.kh.thepain.common.config;

import com.kh.thepain.apiDevel.jwtConfig.JwtFilter;
import com.kh.thepain.apiDevel.model.service.APIDevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Configuration {

	private final JwtFilter jwtFilter;

	private final APIDevelService apiService;

	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}


		//Spring Security
		@Bean
		public BCryptPasswordEncoder bCryptPassword() { return new BCryptPasswordEncoder(); };

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			return http.csrf().disable() //CSRF 공격 방지 기능을 비활성화
					//세션을 사용하지 않고 Stateless(무상태)로 동작하게 설정
					.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					//요청별로 인가(접근권한) 규칙 설정
					.authorizeHttpRequests(auth -> auth
							.antMatchers("/api/**").permitAll() //해당 URL은 인증 없이 접근 가능
							.anyRequest().authenticated() //바로 위에서 설정한 URL 이외 다른 경로는 인증이 필요
					)
					//커스텀 JWT 필터(jwtFilter)를 UsernamePasswordAuthenticationFilter 이전에 등록하여모든 요청에 대해 JWT 인증을 먼저 처리
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
					.build(); //SecurityFilterChain 객체 생성
		}

		@Bean
		public AuthenticationManager authManager(HttpSecurity http) throws Exception {
			//Spring Security의 인증 빌더(AuthenticationManagerBuilder)를 가져옴
			return http.getSharedObject(AuthenticationManagerBuilder.class)
					//사용자 정보 조회는 apiService를 사용하겠다고 명시(UseDetailsService를 상속한 클래스여야함)
					.userDetailsService(apiService)
					//비밀번호 암호화
					.passwordEncoder(bCryptPassword())
					.and().build(); // AuthenticationManager 객체 생성
		}
	
}
