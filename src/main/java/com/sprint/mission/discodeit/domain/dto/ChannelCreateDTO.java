package com.sprint.mission.discodeit.domain.dto;

import java.util.List;

import com.sprint.mission.discodeit.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelCreateDTO {

	private String name;
	private String description;
	private List<User> members;

}
