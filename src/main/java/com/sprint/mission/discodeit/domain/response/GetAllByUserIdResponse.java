package com.sprint.mission.discodeit.domain.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GetAllByUserIdResponse {
	List<ReadChannelResponse> channelInfos;
}
