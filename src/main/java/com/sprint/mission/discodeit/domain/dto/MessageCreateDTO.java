package com.sprint.mission.discodeit.domain.dto;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;

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
	private UUID userId;
	private String authorName;
	private List<BinaryContent> attachments;
}
