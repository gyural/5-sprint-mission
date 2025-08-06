package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.Message;

public interface MessageService {
	/**
 * Creates a new message using the provided data transfer object.
 *
 * @param dto the data transfer object containing information required to create the message
 * @return the created Message entity
 */
Message create(MessageCreateDTO dto);

	/**
 * Deletes the message identified by the specified UUID.
 *
 * @param id the unique identifier of the message to delete
 */
void delete(UUID id);

	/**
 * Deletes all messages from the system.
 */
void deleteAll();

	/**
 * Deletes all messages associated with the specified channel.
 *
 * @param channelId the unique identifier of the channel whose messages will be deleted
 */
void deleteAllByChannelId(UUID channelId);

	/**
 * Updates an existing message with the data provided in the given MessageUpdateDTO.
 *
 * @param dto the data transfer object containing the message ID and updated content
 */
void update(MessageUpdateDTO dto);

	/**
 * Retrieves a message by its unique identifier.
 *
 * @param id the UUID of the message to retrieve
 * @return the Message corresponding to the specified UUID, or null if not found
 */
Message read(UUID id);

	/**
 * Retrieves all messages associated with the specified channel.
 *
 * @param channelId the unique identifier of the channel
 * @return a list of messages belonging to the given channel
 */
List<Message> findAllByChannelId(UUID channelId);

	/**
 * Retrieves all messages associated with the specified channel UUID.
 *
 * @param channelId the unique identifier of the channel
 * @return a list of messages belonging to the given channel
 */
List<Message> readAllByChannelId(UUID channelId);

	boolean isEmpty(UUID channelId);
}
