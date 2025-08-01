package com.sprint.mission.discodeit.domain.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageCreateDTO {
	private String content;
	private UUID channelId;
	private java.util.UUID userId;
}
