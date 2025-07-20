package com.sprint.mission.discodeit.service.alarm;

import com.sprint.mission.discodeit.dto.AlarmDTO;

public class AlarmService {

	/**
	 * 알람을 전송하는 메소드 : 알람로직 구현이 힘들어 콘솔 출력으로 대체 원래라면 실제 API를 통해 알람을 전송하는 로직이 들어가야 합니다.
	 * @param alarm
	 */
	public void sendAlarm(AlarmDTO alarm) {
		System.out.println("알람 전송: " + alarm);
	}

}
