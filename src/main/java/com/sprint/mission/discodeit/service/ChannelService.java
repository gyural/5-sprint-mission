package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.entity.Channel;

public interface ChannelService {
	/**
 * Creates a new public channel using the provided channel creation data.
 *
 * @param dto the data transfer object containing information required to create the public channel
 * @return the created public channel entity
 */
public Channel createPublic(ChannelCreateDTO dto);

	/**
 * Creates a new private channel using the provided channel creation data.
 *
 * @param dto the data transfer object containing information required to create the private channel
 * @return the newly created private channel
 */
public Channel createPrivate(ChannelCreateDTO dto);

	/**
 * Retrieves channel details for the specified channel UUID.
 *
 * @param id the unique identifier of the channel to retrieve
 * @return a ReadChannelResponse containing the channel's details
 */
public ReadChannelResponse read(UUID id);

	/**
 * Retrieves all channels associated with the specified user.
 *
 * @param userId the UUID of the user whose channels are to be retrieved
 * @return a list of channel details for the given user
 */
public List<ReadChannelResponse> findAllByUserId(UUID userId);

	/**
 * Deletes the channel identified by the specified UUID.
 *
 * @param id the unique identifier of the channel to delete
 */
public void delete(UUID id);

	/**
 * Updates an existing channel with the information provided in the update DTO.
 *
 * @param dto the data transfer object containing updated channel details
 */
public void update(ChannelUpdateDTO dto);

	/**
 * Determines whether the channel identified by the given UUID contains any members or content.
 *
 * @param id the UUID of the channel to check
 * @return true if the channel is empty; false otherwise
 */
boolean isEmpty(UUID id);

	void deleteAll();

}
