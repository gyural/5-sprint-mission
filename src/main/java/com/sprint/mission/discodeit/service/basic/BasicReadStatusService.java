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
import com.sprint.mission.discodeit.service.ReadStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

	private final ReadStatusRepository readStatusRepository;
	private final UserRepository userRepository;
	private final ChannelRepository channelRepository;

	@Override
	public ReadStatus create(CreateReadStatusDTO dto) {

		UUID channelId = dto.getChannelId();
		UUID userId = dto.getUserId();

		if (channelRepository.existsById(channelId)) {
			throw new IllegalArgumentException("Channel ID Not Found: " + channelId);
		}
		if (userRepository.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID Not Found: " + userId);
		}
		if (readStatusRepository.findByUserIdAndChannelId(userId, channelId).isPresent()) {
			throw new IllegalArgumentException(
			  "Read status already exists for user: " + userId + " in channel: " + channelId);
		}

		ReadStatus readStatus = new ReadStatus(userId, channelId);

		return readStatusRepository.save(readStatus);
	}

	@Override
	public List<ReadStatus> findAllByUserId(UUID userId) {
		if (userId == null || userRepository.isEmpty(userId)) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}

		return readStatusRepository.findAllByUserId(userId);
	}

	@Override
	public ReadStatus update(UpdateReadStatusDTO dto) {
		UUID id = dto.getId();

		ReadStatus targetReadStatus = readStatusRepository.find(id)
		  .orElseThrow(() -> new IllegalArgumentException("Read status not found for ID: " + id));

		targetReadStatus.setLastReadAt();

		return readStatusRepository.save(targetReadStatus);
	}

	@Override
	public void delete(UUID id) {
		if (id == null || readStatusRepository.isEmpty(id)) {
			throw new IllegalArgumentException("Read status ID cannot be null or empty");
		}

		readStatusRepository.delete(id);
	}

}
