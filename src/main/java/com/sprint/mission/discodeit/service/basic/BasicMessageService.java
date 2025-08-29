package com.sprint.mission.discodeit.service.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.dto.CreateMessageDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateMessageDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContents;
import com.sprint.mission.discodeit.domain.entity.Messages;
import com.sprint.mission.discodeit.domain.response.CreateMessageResponse;
import com.sprint.mission.discodeit.domain.response.MessageResponse;
import com.sprint.mission.discodeit.domain.response.MessagesInChannelResponse;
import com.sprint.mission.discodeit.domain.response.UpdateMessageResponse;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	private final ChannelRepository channelRepository;
	private final BinaryContentRepository binaryContentRepository;
	private final BasicBinaryContentService binaryContentService;

	@Override
	public Messages create(CreateMessageDTO dto) {
		String content = dto.getContent();
		UUID channelId = dto.getChannelId();
		UUID userId = dto.getUserId();
		List<CreateBiContentDTO> attachmentsInMessage = dto.getAttachments();

		// Validate
		if (!channelRepository.existsById(channelId)) {
			throw new NoSuchElementException("channel with id " + channelId + "not found");
		}
		if (userRepository.isEmpty(userId)) {
			throw new NoSuchElementException("Author with id " + userId + "not found");

		}

		List<BinaryContents> files = new ArrayList<>();
		if (attachmentsInMessage != null && !attachmentsInMessage.isEmpty()) {
			attachmentsInMessage.forEach((file) -> {
				files.add(binaryContentService.create(file));
			});
		}

		return messageRepository.save(new Messages(content, userId, channelId));
	}

	@Override
	public void delete(UUID id) {
		Messages messagesToDelete = messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message with ID " + id + " not found"));

		// 메시지 관련 Attachment 도 삭제
		// messagesToDelete.getAttachmentIds().forEach(binaryContentRepository::delete);

		// 메시지 삭제
		messageRepository.delete(id);
	}

	@Override
	public void deleteAll() {
		messageRepository.deleteAll();
	}

	@Override
	public void deleteAllByChannelId(UUID channelId) {
		if (channelRepository.existsById(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		messageRepository.deleteByChannelId(channelId);
	}

	@Override
	public Messages update(UpdateMessageDTO dto) {
		Optional.ofNullable(dto).orElseThrow(() -> new IllegalArgumentException("UpdateMessageDTO cannot be null"));
		UUID id = dto.getId();
		String newContent = dto.getNewContent();
		List<UUID> AttachmentIdsToRemove = dto.getRemoveAttachmentIds();
		List<CreateBiContentDTO> newAttachments = dto.getNewAttachments();

		if (newContent == null || newContent.isEmpty()) {
			throw new IllegalArgumentException("New content cannot be null or empty");
		}

		Messages targetMessages = messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message with ID " + id + " not found"));

		// 1. 내용 수정
		targetMessages.setContent(newContent);
		// 2. 삭제할 attachmentId가 있다면 삭제
		if (AttachmentIdsToRemove != null && !AttachmentIdsToRemove.isEmpty()) {
			// 기존 첨부파일 삭제
			AttachmentIdsToRemove.forEach(binaryContentRepository::delete);
			// targetMessages.getAttachmentIds().removeAll(AttachmentIdsToRemove);
		}
		// 3. 새로 추가할 첨부파일이 있다면 추가
		if (newAttachments != null && !newAttachments.isEmpty()) {
			List<BinaryContents> newFiles = newAttachments.stream()
			  .map(binaryContentService::create)
			  .toList();
			List<UUID> newAttachmentIds = newFiles.stream()
			  .map(BinaryContents::getId)
			  .toList();
			// targetMessages.getAttachmentIds().addAll(newAttachmentIds);
		}

		return messageRepository.save(targetMessages);
	}

	@Override
	public Messages read(UUID id) {
		return messageRepository.find(id)
		  .orElseThrow(() -> new NoSuchElementException("Message with ID " + id + " not found"));
	}

	@Override
	public List<Messages> findAllByChannelId(UUID channelId) {
		return messageRepository.findAll().stream().filter(
			message -> message.getChannels().getId().equals(channelId))
		  .toList();
	}

	@Override
	public List<Messages> readAllByChannelId(UUID channelId) {
		return messageRepository.findAllByChannelId(channelId);
	}

	@Override
	public boolean isEmpty(UUID channelId) {
		return messageRepository.isEmpty(channelId);
	}

	public static CreateMessageResponse toCreateMessageResponse(Messages newMessages) {
		return CreateMessageResponse.builder()
		  .id(newMessages.getId())
		  .createdAt(newMessages.getCreatedAt())
		  .updatedAt(newMessages.getUpdatedAt())
		  .content(newMessages.getContent())
		  .authorId(newMessages.getUser().getId())
		  .channelId(newMessages.getChannels().getId())
		  // .attachmentIds(newMessages.getAttachmentIds())
		  .build();
	}

	public static UpdateMessageResponse toUpdateMessageResponse(Messages newMessages) {
		return UpdateMessageResponse.builder()
		  .id(newMessages.getId())
		  .createdAt(newMessages.getCreatedAt())
		  .updatedAt(newMessages.getUpdatedAt())
		  .content(newMessages.getContent())
		  .authorId(newMessages.getUser().getId())
		  .channelId(newMessages.getChannels().getId())
		  // .attachmentIds(newMessages.getAttachmentIds())
		  .build();
	}

	public static MessagesInChannelResponse toMessagesInChannelResponse(List<Messages> messages) {
		return new MessagesInChannelResponse(
		  messages.stream().map(message ->
			new MessageResponse(
			  message.getId(),
			  message.getCreatedAt(),
			  message.getUpdatedAt(),
			  message.getContent(),
			  message.getUser().getId(),
			  message.getChannels().getId(),
			  null
			)
		  ).toList()
		);
	}
}
