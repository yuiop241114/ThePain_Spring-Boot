package com.kh.thepain.common.model.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/*
 * 자바에서 이메일 기능을 사용하기 위한 JavaMailSender
 * 설정 Bean 수동 등록 
 * */

@Configuration
public class MailConfig {
	@Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Gmail SMTP 서버 주소
        mailSender.setPort(587); // Gmail에서 사용하는 TLS 포트
        mailSender.setUsername("projectacount22@gmail.com"); // Gmail 계정
        mailSender.setPassword("rxuy ivcu saow wdec"); // Gmail 앱 비밀번호

        // 추가 속성 설정
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // 디버깅 활성화

        return mailSender;
    }
}
