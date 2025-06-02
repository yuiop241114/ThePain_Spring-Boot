package com.kh.thepain.webSocket.model.service;

import com.kh.thepain.webSocket.model.dao.AlarmDao;
import com.kh.thepain.webSocket.model.vo.Alarm;
import com.kh.thepain.webSocket.model.vo.AlarmHistory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlarmService {
	
	@Autowired
	private AlarmDao aDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// 템플릿 알람 조회
	public Alarm selectAlarmByNo(int alarmNo) {
    	return aDao.selectAlarmByNo(sqlSession, alarmNo);
    }
	
	// 알람 이력 저장
	public int insertAlarmHistory(AlarmHistory history) {
		return aDao.insertAlarmHistory(sqlSession, history);
	}
    public int insertAlarm(AlarmHistory  history) {
        return aDao.insertAlarm(sqlSession, history);
    }
    
    // 특정 회원의 알람 리스트 조회
    public ArrayList<AlarmHistory> selectAlarmListByReceiver(int memberNo) {
        return aDao.selectAlarmListByReceiver(sqlSession, memberNo);
    }

    
    
    public boolean isAlreadySentAlarm(AlarmHistory history) {
        return aDao.isAlreadySentAlarm(sqlSession, history);
    }
    
    
    
    
}