package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.entity.Channel;

public interface ChannelService {
	public Channel createPublic(ChannelCreateDTO dto);

	public Channel createPrivate(ChannelCreateDTO dto);

	public ReadChannelResponse read(UUID id);

	public List<ReadChannelResponse> findAllByUserId(UUID userId);

	public void delete(UUID id);

	public void update(ChannelUpdateDTO dto);

	boolean isEmpty(UUID id);

	void deleteAll();

}
