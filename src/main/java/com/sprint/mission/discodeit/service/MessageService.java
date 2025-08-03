package com.sprint.mission.discodeit.service;

public interface MessageService {
	void create(String content, Long channelId, String userId);

	void delete(Long id);

	void update(Long id, String newContent);

	void read(Long id);
}
