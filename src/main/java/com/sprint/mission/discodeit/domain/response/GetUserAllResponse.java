package com.sprint.mission.discodeit.domain.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetUserAllResponse {

	private final List<UserReadResponse> users;
}
