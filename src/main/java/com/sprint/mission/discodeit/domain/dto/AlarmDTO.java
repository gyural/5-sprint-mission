package com.sprint.mission.discodeit.domain.dto;

import java.util.UUID;

public class AlarmDTO {
	private String AlarmMessage;
	private String AlarmType;
	private UUID targetUserId;
	private UUID ChannelId;

	public AlarmDTO(String alarmMessage, String alarmType, UUID targetUserId, UUID channelId) {
		this.AlarmMessage = alarmMessage;
		this.AlarmType = alarmType;
		this.targetUserId = targetUserId;
		this.ChannelId = channelId;
	}

	@Override
	public String toString() {
		return "AlarmDTO{" +
		  "AlarmMessage='" + AlarmMessage + '\'' +
		  ", AlarmType='" + AlarmType + '\'' +
		  ", targetUserId='" + targetUserId + '\'' +
		  ", ChannelId='" + ChannelId + '\'' +
		  '}';
	}
}
