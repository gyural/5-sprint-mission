package com.sprint.mission.discodeit.domain.dto;

import com.sprint.mission.discodeit.domain.enums.ContentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateBiContentDTO {
	private final byte[] content;
	private final int size;
	private final ContentType contentType;
	private final String fileName;

}
