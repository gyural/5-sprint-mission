package com.sprint.mission.discodeit.domain.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UpdateMessageDTO {
	private final UUID id;
	private final String newContent;
	private final List<UUID> removeAttachmentIds;
	private final List<CreateBiContentDTO> newAttachments;
}
