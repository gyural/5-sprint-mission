package com.sprint.mission.discodeit.domain.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FindBiContentsIdInDTO {
	List<UUID> ids;
}
