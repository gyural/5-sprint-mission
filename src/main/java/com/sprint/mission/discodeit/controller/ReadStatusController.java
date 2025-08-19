package com.sprint.mission.discodeit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.dto.CreateReadStatusDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.domain.request.CreateReadStatusRequest;
import com.sprint.mission.discodeit.domain.request.UpdateReadStatusRequest;
import com.sprint.mission.discodeit.domain.response.CreateReadStatusResponse;
import com.sprint.mission.discodeit.domain.response.UpdateReadStatusResponse;
import com.sprint.mission.discodeit.service.ReadStatusService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/read-status")
@Tag(name = "ReadStatus", description = "Message 읽음 상태 API")
public class ReadStatusController {

	private final ReadStatusService readStatusService;

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<CreateReadStatusResponse> createReadStatus(
	  @RequestBody @Valid CreateReadStatusRequest request) {
		ReadStatus newReadStatus = readStatusService.create(CreateReadStatusDTO.builder()
		  .userId(request.getUserId())
		  .channelId(request.getChannelId()).build());

		CreateReadStatusResponse response = CreateReadStatusResponse.builder()
		  .id(newReadStatus.getId())
		  .createdAt(newReadStatus.getCreatedAt())
		  .updatedAt(newReadStatus.getUpdatedAt())
		  .lastReadAt(newReadStatus.getLastReadAt())
		  .userId(newReadStatus.getUserId())
		  .channelId(newReadStatus.getChannelId())
		  .build();

		// Logic to create a read status
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "", method = PUT)
	public ResponseEntity<UpdateReadStatusResponse> updateReadStatus(
	  @RequestBody @Valid UpdateReadStatusRequest request) {
		ReadStatus updatedReadStatus = readStatusService.update(UpdateReadStatusDTO.builder()
		  .id(request.getId()).build());

		UpdateReadStatusResponse response = UpdateReadStatusResponse.builder()
		  .id(updatedReadStatus.getId())
		  .createdAt(updatedReadStatus.getCreatedAt())
		  .updatedAt(updatedReadStatus.getUpdatedAt())
		  .lastReadAt(updatedReadStatus.getLastReadAt())
		  .userId(updatedReadStatus.getUserId())
		  .channelId(updatedReadStatus.getChannelId())
		  .build();

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/user/{userId}", method = GET)
	public ResponseEntity<List<ReadStatus>> getReadStatusesByUserId(@PathVariable UUID userId) {

		return ResponseEntity.ok(readStatusService.findAllByUserId(userId));
	}
}
