package com.kh.thepain.webSocket.model.service;

import com.kh.thepain.webSocket.model.vo.Alarm;

import java.util.ArrayList;

public interface AlarmServiceInterface {

	// 알람 조회
	ArrayList<Alarm> selectList();
	
	//  새 알림 저장
	int insertAlarm(Alarm alarm);
	
	//수신자 별 알림 메세지 출력
	ArrayList<Alarm> selectAlarmListByReceiver(int memberNo);
	
	boolean createAlarmHistory(int alarmNo, int receiverMemberNo);
}
