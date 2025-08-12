package com.sprint.mission.discodeit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.response.BinaryContentResponse;
import com.sprint.mission.discodeit.service.BinaryContentService;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/binary-content")
public class BinaryContentController {

	private final BinaryContentService binaryContentService;

	@RequestMapping(value = "", method = GET)
	public ResponseEntity<List<BinaryContentResponse>> getBinaryContents(@RequestParam @Size(min = 1) List<UUID> ids) {

		List<BinaryContent> binaryContents = binaryContentService.findAllByIdIn(ids);

		List<BinaryContentResponse> response = binaryContents.stream()
		  .map(content -> BinaryContentResponse.builder()
			.id(content.getId())
			.createdAt(content.getCreatedAt())
			.fileName(content.getFileName())
			.contentType(content.getContentType())
			.content(content.getContent())
			.size(content.getSize())
			.build())
		  .toList();

		return ResponseEntity.ok(response);
	}
}
