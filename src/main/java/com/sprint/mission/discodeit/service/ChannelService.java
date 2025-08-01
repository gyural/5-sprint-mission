package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;

public interface ChannelService {
	public Channel create(ChannelCreateDTO dto);

	public Channel read(UUID id);

	public List<Channel> readAll();

	public void delete(UUID id);

	public void update(ChannelUpdateDTO dto);

	boolean isEmpty(UUID id);

	void deleteAll();

}
