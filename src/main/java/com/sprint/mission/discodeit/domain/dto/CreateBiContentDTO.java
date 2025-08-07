package com.sprint.mission.discodeit.domain.dto;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.enums.ContentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateBiContentDTO {
	private byte[] content;
	private int size;
	private ContentType contentType;
	private String filename;

	public BinaryContent toBinaryContent() {
		return new BinaryContent(content, size, contentType, filename);
	}
}
