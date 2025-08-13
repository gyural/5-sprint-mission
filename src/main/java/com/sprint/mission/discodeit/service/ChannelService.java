package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.domain.dto.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateChannelDTO;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.response.ReadChannelResponse;

public interface ChannelService {
	public Channel createPublic(CreatePublicChannelDTO dto);

	public Channel createPrivate(CreatePrivateChannelDTO dto);

	public ReadChannelResponse readPrivate(UUID id);

	public ReadChannelResponse readPublic(UUID id);

	public List<ReadChannelResponse> readAllByUserId(UUID userId);

	public void delete(UUID id);

	public Channel update(UpdateChannelDTO dto);

	boolean isEmpty(UUID id);

	void deleteAll();

}
