package com.sprint.mission.discodeit.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.dto.CreateMessageDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateMessageDTO;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.domain.response.CreateMessageResponse;
import com.sprint.mission.discodeit.domain.response.MessageResponse;
import com.sprint.mission.discodeit.domain.response.MessagesInChannelResponse;
import com.sprint.mission.discodeit.service.MessageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Tag(name = "Message", description = "Message API")
public class MessageController {

	private final MessageService messageService;

	@RequestMapping(value = "", method = POST, consumes = "multipart/form-data")
	public ResponseEntity<CreateMessageResponse> createMessage(
	  @RequestPart MessageCreateRequest messageCreateRequest,
	  @RequestPart(required = false) List<MultipartFile> attachments) {

		List<CreateBiContentDTO> biContentDTOs = new ArrayList<>();
		if (attachments != null && !attachments.isEmpty()) {
			attachments.forEach(file -> {
				try {
					biContentDTOs.add(new CreateBiContentDTO(
					  file.getBytes(),
					  file.getSize(),
					  file.getContentType(),
					  file.getOriginalFilename()
					));
				} catch (IOException e) {
					throw new RuntimeException("Error processing file: " + file.getOriginalFilename(), e);
				}
			});
		}

		Message newMessage = messageService.create(CreateMessageDTO.builder()
		  .content(messageCreateRequest.getContent())
		  .channelId(messageCreateRequest.getChannelId())
		  .userId(messageCreateRequest.getAuthorId())
		  .attachments(biContentDTOs)
		  .build());

		return ResponseEntity.status(CREATED).body(
		  CreateMessageResponse.builder()
			.id(newMessage.getId())
			.createdAt(newMessage.getCreatedAt())
			.updatedAt(newMessage.getUpdatedAt())
			.content(newMessage.getContent())
			.authorId(newMessage.getAuthorId())
			.channelId(newMessage.getChannelId())
			.attachmentIds(newMessage.getAttachmentIds())
			.build()
		);
	}

	@PatchMapping
	public ResponseEntity<CreateMessageResponse> updateMessage(
	  @RequestParam UUID messageId,
	  @RequestBody @Valid UpdateMessageRequest updateMessageRequest
	) {

		List<CreateBiContentDTO> biContentDTOs = new ArrayList<>();

		Message updatedMessage = messageService.update(UpdateMessageDTO.builder()
		  .id(messageId)
		  .newContent(updateMessageRequest.getNewContent())
		  .newAttachments(biContentDTOs)
		  .build());

		return ResponseEntity.ok().body(
		  CreateMessageResponse.builder()
			.id(updatedMessage.getId())
			.createdAt(updatedMessage.getCreatedAt())
			.updatedAt(updatedMessage.getUpdatedAt())
			.content(updatedMessage.getContent())
			.channelId(updatedMessage.getChannelId())
			.authorId(updatedMessage.getAuthorId())
			.attachmentIds(updatedMessage.getAttachmentIds())
			.build()
		);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable UUID id) {
		messageService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<MessagesInChannelResponse> GetMessagesInChannel(@RequestParam UUID channelId) {
		List<Message> readMessages = messageService.readAllByChannelId(channelId);

		MessagesInChannelResponse response = new MessagesInChannelResponse(
		  readMessages.stream().map(message ->
			new MessageResponse(
			  message.getId(),
			  message.getCreatedAt(),
			  message.getUpdatedAt(),
			  message.getContent(),
			  message.getAuthorId(),
			  message.getChannelId(),
			  message.getAttachmentIds()
			)
		  ).toList()
		);

		return ResponseEntity.ok(response);
	}
}
