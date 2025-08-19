package com.sprint.mission.discodeit.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.dto.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.domain.dto.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateChannelDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.request.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.domain.request.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.domain.request.UpdatePublicChannelRequest;
import com.sprint.mission.discodeit.domain.response.CreatePrivateChannelResponse;
import com.sprint.mission.discodeit.domain.response.CreatePublicChannelResponse;
import com.sprint.mission.discodeit.domain.response.GetAllByUserIdResponse;
import com.sprint.mission.discodeit.domain.response.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.response.UpdateChannelResponse;
import com.sprint.mission.discodeit.service.ChannelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channel/")
@Tag(name = "Channel", description = "Channel API")
public class ChannelController {

	private final ChannelService channelService;

	@RequestMapping(value = "public", method = POST)
	public ResponseEntity<CreatePublicChannelResponse> createPublicChannel(
	  @RequestBody @Valid CreatePublicChannelRequest request) {

		Channel newChannel = channelService.createPublic(CreatePublicChannelDTO.builder()
		  .name(request.getName())
		  .description(request.getDescription())
		  .build());

		return ResponseEntity.status(CREATED)
		  .body(CreatePublicChannelResponse.builder().channel(newChannel).build());

	}

	@RequestMapping(value = "private", method = POST)
	public ResponseEntity<CreatePrivateChannelResponse> createPrivateChannel(
	  @RequestBody @Valid CreatePrivateChannelRequest request) {

		Channel newChannel = channelService.createPrivate(CreatePrivateChannelDTO.builder()
		  .name(request.getName())
		  .description(request.getDescription())
		  .UserIds(request.getUserIds())
		  .build());

		return ResponseEntity.status(CREATED).body(CreatePrivateChannelResponse.builder()
		  .channel(newChannel)
		  .userIds(request.getUserIds())
		  .build());
	}

	@RequestMapping(value = "public", method = PUT)
	public ResponseEntity<UpdateChannelResponse> updatePrivateChannel(@RequestBody UpdatePublicChannelRequest request) {
		Channel updatedChannel = channelService.update(UpdateChannelDTO.builder()
		  .id(request.getId())
		  .name(request.getName())
		  .description(request.getDescription())
		  .build());
		return ResponseEntity.status(OK).body(UpdateChannelResponse.builder()
		  .channel(updatedChannel)
		  .build());
	}

	@RequestMapping(value = "/all", method = GET)
	public ResponseEntity<GetAllByUserIdResponse> getAllByUserId(@RequestParam UUID userId) {
		List<ReadChannelResponse> channels = channelService.readAllByUserId(userId);

		return ResponseEntity.ok(
		  GetAllByUserIdResponse.builder().channelInfos(channels).build());
	}
}
