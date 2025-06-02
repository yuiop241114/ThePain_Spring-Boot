package com.kh.thepain.webSocket.model.vo;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AlarmHistory {
	
	private int alarmHistoryNo;
	private int alarmNo;
	private int receiverNo;
	private int senderNo;
	private String alarmHistoryContent;
	private Date createDate;
	
}
