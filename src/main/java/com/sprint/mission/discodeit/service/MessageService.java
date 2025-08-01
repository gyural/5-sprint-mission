package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.Message;

public interface MessageService {
	Message create(MessageCreateDTO dto);

	void delete(UUID id);

	void deleteAll();

	void deleteAllByChannelId(UUID channelId);

	void update(MessageUpdateDTO dto);

	Message read(UUID id);

	List<Message> readAll();

	List<Message> readAllByChannelId(UUID channelId);

	boolean isEmpty(UUID channelId);
}
