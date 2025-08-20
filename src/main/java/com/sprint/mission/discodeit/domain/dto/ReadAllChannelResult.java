package com.sprint.mission.discodeit.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ReadAllChannelResult {
	List<ChannelDetail> channelDetails;

}
