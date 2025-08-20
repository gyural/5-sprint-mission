package com.sprint.mission.discodeit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.dto.FindBiContentIdInDTO;
import com.sprint.mission.discodeit.domain.dto.FindBiContentsIdInDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.response.BinaryContentResponse;
import com.sprint.mission.discodeit.service.BinaryContentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/binaryContents")
@Tag(name = "BinaryContent", description = "첨부파일 API")

public class BinaryContentController {

	private final BinaryContentService binaryContentService;

	@GetMapping
	public ResponseEntity<List<BinaryContentResponse>> getBinaryContents(
	  @RequestParam @Size(min = 1) List<UUID> binaryContentIds) {

		List<BinaryContent> binaryContents = binaryContentService.findAllByIdIn(FindBiContentsIdInDTO.builder()
		  .ids(binaryContentIds)
		  .build());

		List<BinaryContentResponse> response = binaryContents.stream()
		  .map(content -> BinaryContentResponse.builder()
			.id(content.getId())
			.createdAt(content.getCreatedAt())
			.fileName(content.getFileName())
			.contentType(content.getContentType())
			.bytes(content.getContent())
			.size(content.getSize())
			.build())
		  .toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BinaryContentResponse> getBinaryContents(@PathVariable @NotNull UUID id) {

		BinaryContent binaryContent =
		  binaryContentService.find(FindBiContentIdInDTO.builder().id(id).build());

		BinaryContentResponse response = BinaryContentResponse.builder()
		  .id(binaryContent.getId())
		  .createdAt(binaryContent.getCreatedAt())
		  .fileName(binaryContent.getFileName())
		  .contentType(binaryContent.getContentType())
		  .bytes(binaryContent.getContent())
		  .size(binaryContent.getSize())
		  .build();

		return ResponseEntity.ok(response);
	}
}
