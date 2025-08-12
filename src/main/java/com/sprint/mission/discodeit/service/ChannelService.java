package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreateChannelDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateChannelDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.response.ReadChannelResponse;

public interface ChannelService {
	public Channel createPublic(CreateChannelDTO dto);

	public Channel createPrivate(CreateChannelDTO dto);

	public ReadChannelResponse readPrivate(UUID id);

	public ReadChannelResponse readPublic(UUID id);

	public List<ReadChannelResponse> findAllByUserId(UUID userId);

	public void delete(UUID id);

	public void update(UpdateChannelDTO dto);

	boolean isEmpty(UUID id);

	void deleteAll();

}
