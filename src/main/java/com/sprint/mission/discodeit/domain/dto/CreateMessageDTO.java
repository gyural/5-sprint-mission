package com.sprint.mission.discodeit.domain.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreateMessageDTO {
	private final String content;
	private final UUID channelId;
	private final UUID userId;
	private final List<CreateBiContentDTO> attachments;
}
