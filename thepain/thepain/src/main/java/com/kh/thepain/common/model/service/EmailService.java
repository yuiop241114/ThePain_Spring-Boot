package com.kh.thepain.common.model.service;

import com.kh.thepain.common.model.vo.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {
	
	//spring boot가 아닌 spring에서는 메일 사용을 위한 설정을 직절 클래스를 만들어서 bean으로 등록해야한다
	@Autowired
    private JavaMailSender mailSender; 
	
	private final HashMap<String, String> emailAndCode = new HashMap<>();
    private final HashMap<String, Long> expirationTimes = new HashMap<>();
    private final long CODE_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10); // 10분
    
    
    /**
     * @param to
     * @param code
     * @param email
     * 일반 이메일 전송 서비스 메소드
     */
    public void sendApplierEmail(Email email) {
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo(email.getReceptionEmail()); //수신자 이메일(받는 사람)
        message.setSubject(email.getSubject());
        message.setText(email.getContent());
        message.setFrom(email.getSenderEmail()); // 발신자 이메일(보내는 사람)
        
        mailSender.send(message);
    }

	/**
	 * @param to
	 * @param code
	 * 이메일 발송을 위한 서비스 메소드
	 */
	public void sendCodeEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + code);
        message.setFrom("projectacount22@gmail.com"); // 발신자 이메일

        try {
        	//인증 메일 발송
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("이메일 전송 실패: " + e.getMessage());
        }
    }
	
	/**
	 * @param email
	 * @param code
	 * 입력받은 이메일과 인증 코드를 저장 서비스 메소드
	 */
	public void saveCode(String email, String code) {
		emailAndCode.put(email, code); //이메일 및 인증 코드를 저장 
        expirationTimes.put(email, System.currentTimeMillis() + CODE_EXPIRATION_TIME); //만료시간을 지정 및 저장
    }
	
	/**
	 * @param email
	 * @param code
	 * @return
	 * 입력받은 이메일 및 인증 코드 검증 서비스 메소드
	 */
	public boolean verifyCode(String email, String code) {
	    String savedCode = emailAndCode.get(email);
	    Long expirationTime = expirationTimes.get(email);

	    if (savedCode == null || expirationTime == null || System.currentTimeMillis() > expirationTime) {
	        return false; // 코드가 없거나 만료된 경우
	    }

	    return savedCode.equals(code);
	}
}
