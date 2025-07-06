package com.kh.thepain.webSocket.socketMapper;

import com.kh.thepain.webSocket.model.vo.Alarm;
import com.kh.thepain.webSocket.model.vo.AlarmHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SocketMapper {
    Alarm selectAlarmByNo(int alarmNo);

    int insertAlarm(AlarmHistory history);

    int insertAlarmHistory(AlarmHistory history);

    ArrayList<AlarmHistory> selectAlarmListByReceiver(int memberNo);

    int isAlreadySentAlarm(AlarmHistory history);
}
