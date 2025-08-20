package com.sprint.mission.discodeit.controller;

import static com.sprint.mission.discodeit.domain.response.CreatePrivateChannelResponse.*;
import static com.sprint.mission.discodeit.domain.response.CreatePublicChannelResponse.*;
import static com.sprint.mission.discodeit.domain.response.UpdateChannelResponse.*;
import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.dto.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.domain.dto.CreatePrivateChannelResult;
import com.sprint.mission.discodeit.domain.dto.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.domain.dto.CreatePublicChannelResult;
import com.sprint.mission.discodeit.domain.dto.ReadAllChannelResult;
import com.sprint.mission.discodeit.domain.dto.UpdateChannelDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateChannelResult;
import com.sprint.mission.discodeit.domain.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.domain.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.domain.request.UpdatePublicChannelRequest;
import com.sprint.mission.discodeit.domain.response.CreatePrivateChannelResponse;
import com.sprint.mission.discodeit.domain.response.CreatePublicChannelResponse;
import com.sprint.mission.discodeit.domain.response.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.response.UpdateChannelResponse;
import com.sprint.mission.discodeit.service.ChannelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels/")
@Tag(name = "Channel", description = "Channel API")
public class ChannelController {

	private final ChannelService channelService;

	@PostMapping("public")
	public ResponseEntity<CreatePublicChannelResponse> createPublicChannel(
	  @RequestBody @Valid CreatePublicChannelRequest request) {

		CreatePublicChannelResult result = channelService.createPublic(CreatePublicChannelDTO.builder()
		  .name(request.getName())
		  .description(request.getDescription())
		  .build());

		return ResponseEntity.status(CREATED).body(toCreatePublicChannelResponse(result.getChannel()));

	}

	@PostMapping("private")
	public ResponseEntity<CreatePrivateChannelResponse> createPrivateChannel(
	  @RequestBody @Valid CreatePrivateChannelRequest request) {

		CreatePrivateChannelResult result = channelService.createPrivate(CreatePrivateChannelDTO.builder()
		  .UserIds(request.getUserIds())
		  .build());

		return ResponseEntity.status(CREATED).body(toCreatePrivateChannelResponse(result.getChannel()));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteChannel(@PathVariable UUID id) {
		channelService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UpdateChannelResponse> updatePrivateChannel(
	  @PathVariable UUID id,
	  @RequestBody @Valid UpdatePublicChannelRequest request) {
		UpdateChannelResult result = channelService.update(UpdateChannelDTO.builder()
		  .id(id)
		  .name(request.getName())
		  .description(request.getDescription())
		  .build());
		return ResponseEntity.status(OK).body(toUpdateChannelResponse(result.getUpdatedChannel()));
	}

	@GetMapping
	public ResponseEntity<List<ReadChannelResponse>> getAllByUserId(@RequestParam UUID userId) {
		ReadAllChannelResult channelDetails = channelService.readAllByUserId(userId);

		List<ReadChannelResponse> body = channelDetails.getChannelDetails().stream()
		  .map(channel -> ReadChannelResponse.builder()
			.id(channel.getChannel().getId())
			.type(channel.getChannel().getChannelType())
			.name(channel.getChannel().getName())
			.description(channel.getChannel().getDescription())
			.participantIds(channel.getUserIds())
			.lastMessageAt(channel.getLastMessageAt())
			.build())
		  .toList();

		return ResponseEntity.ok(body);
	}
}
