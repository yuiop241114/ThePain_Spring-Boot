package com.kh.thepain.common.model.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Email {
	private String subject;
	private String content;
	private String receptionEmail;
	private String senderEmail;
}
