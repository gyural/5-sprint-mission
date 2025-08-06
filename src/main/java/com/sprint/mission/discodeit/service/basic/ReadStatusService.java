package com.sprint.mission.discodeit.service.basic;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sprint.mission.discodeit.domain.dto.CreateReadStatusDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadStatusService {

	private final ReadStatusRepository readStatusRepository;
	private final UserRepository userRepository;
	private final ChannelRepository channelRepository;

	/**
	 * Creates a new ReadStatus entity for the specified user and channel.
	 *
	 * Validates that the provided user and channel IDs are not null and exist in their respective repositories.
	 * Throws an IllegalArgumentException if a ReadStatus already exists for the given user and channel, or if the IDs are invalid.
	 *
	 * @param dto Data transfer object containing the user and channel IDs.
	 * @return The newly created ReadStatus entity.
	 * @throws IllegalArgumentException if the user or channel ID is null, does not exist, or if a ReadStatus already exists for the user and channel.
	 */
	public ReadStatus create(CreateReadStatusDTO dto) {

		UUID channelId = dto.getChannelId();
		UUID userId = dto.getUserId();

		if (channelId == null || channelRepository.isEmpty(channelId)) {
			throw new IllegalArgumentException("Channel ID cannot be null or empty");
		}
		if (userId == null || userRepository.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}
		if (readStatusRepository.findByUserIdAndChannelId(userId, channelId).isPresent()) {
			throw new IllegalArgumentException(
			  "Read status already exists for user: " + userId + " in channel: " + channelId);
		}

		ReadStatus readStatus = new ReadStatus(userId, channelId);

		return readStatusRepository.save(readStatus);
	}

	/**
	 * Retrieves all read status records associated with the specified user ID.
	 *
	 * @param userId the unique identifier of the user whose read statuses are to be retrieved; must not be null and must exist
	 * @return a list of ReadStatus entities linked to the given user
	 * @throws IllegalArgumentException if the userId is null or does not exist
	 */
	public List<ReadStatus> findAllByUserId(UUID userId) {
		if (userId == null || userRepository.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		return readStatusRepository.findAllByUserId(userId);
	}

	/**
	 * Updates the `updatedAt` timestamp of an existing ReadStatus entity.
	 *
	 * @param dto Data transfer object containing the ID of the ReadStatus to update.
	 * @throws IllegalArgumentException if the ReadStatus with the specified ID does not exist.
	 */
	public void update(UpdateReadStatusDTO dto) {
		UUID id = dto.getId();

		ReadStatus targetReadStatus = readStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("Read status not found for ID: " + id));

		targetReadStatus.setUpdatedAt();

		readStatusRepository.save(targetReadStatus);
	}

	/**
	 * Deletes the ReadStatus entity with the specified ID.
	 *
	 * @param id the unique identifier of the ReadStatus to delete; must not be null or non-existent
	 * @throws IllegalArgumentException if the ID is null or does not correspond to an existing ReadStatus
	 */
	public void delete(UUID id) {
		if (id == null || readStatusRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Read status ID cannot be null or empty");
		}

		readStatusRepository.delete(id);
	}

}
