package com.kh.thepain.webSocket.model.dao;

import com.kh.thepain.webSocket.model.vo.Alarm;
import com.kh.thepain.webSocket.model.vo.AlarmHistory;
import com.kh.thepain.webSocket.socketMapper.SocketMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AlarmDao {

	
//	/**
//	 * 알람 조회
//	 * @param sqlSession
//	 * @param alarmNo
//	 * @return
//	 */
//	public Alarm selectAlarmByNo(SqlSessionTemplate sqlSession, int alarmNo){
//		return sqlSession.selectOne("alarmMapper.selectAlarmByNo", alarmNo);
//	}
//
//	/**
//	 * 특정된 회원에게 메세지 전달 후 db 저장
//	 * @param sqlSession
//	 * @param alarm
//	 * @return
//	 */
//	public int insertAlarm(SqlSessionTemplate sqlSession, AlarmHistory history) {
//	    return sqlSession.insert("alarmMapper.insertAlarm", history);
//	}
//	  public int insertAlarmHistory(SqlSessionTemplate sqlSession, AlarmHistory history) {
//	        return sqlSession.insert("alarmMapper.insertAlarmHistory", history);
//	    }
//
//
//	public ArrayList<AlarmHistory> selectAlarmListByReceiver(SqlSessionTemplate sqlSession, int memberNo) {
//	    return (ArrayList)sqlSession.selectList("alarmMapper.selectAlarmListByReceiver", memberNo);
//	}
//
//	public boolean isAlreadySentAlarm(SqlSessionTemplate sqlSession, AlarmHistory history) {
//	    return (Integer) sqlSession.selectOne("alarmMapper.isAlreadySentAlarm", history) > 0;
//	}

	@Autowired
	private SocketMapper mMapper;

	/**
	 * 알람 번호로 알람 조회
	 */
	public Alarm selectAlarmByNo(int alarmNo){
		return mMapper.selectAlarmByNo(alarmNo);
	}

	/**
	 * 특정 회원에게 메세지 전달 후 DB 저장
	 */
	public int insertAlarm(AlarmHistory history) {
		return mMapper.insertAlarm(history);
	}

	public int insertAlarmHistory(AlarmHistory history) {
		return mMapper.insertAlarmHistory(history);
	}

	public ArrayList<AlarmHistory> selectAlarmListByReceiver(int memberNo) {
		return mMapper.selectAlarmListByReceiver(memberNo);
	}

	public boolean isAlreadySentAlarm(AlarmHistory history) {
		return mMapper.isAlreadySentAlarm(history) > 0;
	}

}
