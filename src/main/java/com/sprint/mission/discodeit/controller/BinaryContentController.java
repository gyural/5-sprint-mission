package com.sprint.mission.discodeit.controller;

import static com.sprint.mission.discodeit.service.basic.BasicBinaryContentService.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.dto.FindBiContentResult;
import com.sprint.mission.discodeit.domain.dto.FindBiContentsIdInDTO;
import com.sprint.mission.discodeit.domain.response.BinaryContentResponse;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.basic.BasicBinaryContentService;

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

		List<FindBiContentResult> binaryContents = binaryContentService.findAllByIdIn(FindBiContentsIdInDTO.builder()
		  .ids(binaryContentIds)
		  .build());

		return ResponseEntity.ok(
		  binaryContents.stream().map(BasicBinaryContentService::biContentResultToResponse).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BinaryContentResponse> getBinaryContents(@PathVariable @NotNull UUID id) {
		FindBiContentResult result = binaryContentService.find(id);

		return ResponseEntity.ok(biContentResultToResponse(result));
	}
}
