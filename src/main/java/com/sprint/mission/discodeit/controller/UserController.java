package com.sprint.mission.discodeit.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.mission.discodeit.domain.dto.CreateUserDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateUserDTO;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.domain.request.CreatUsereRequest;
import com.sprint.mission.discodeit.domain.request.CreateUserResponse;
import com.sprint.mission.discodeit.domain.request.UpdateUserRequest;
import com.sprint.mission.discodeit.domain.response.GetUserAllResponse;
import com.sprint.mission.discodeit.domain.response.UpdateUserStatusResponse;
import com.sprint.mission.discodeit.domain.response.UserDeleteResponse;
import com.sprint.mission.discodeit.domain.response.UserUpdateResponse;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	private final UserStatusService userStatusService;

	@RequestMapping(value = "", method = POST)
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreatUsereRequest request) {
		System.out.println(request.toString());

		User createdUser = userService.create(CreateUserDTO.builder()
		  .username(request.getUsername())
		  .email(request.getEmail())
		  .password(request.getPassword())
		  .binaryContent(request.getProfilePicture())
		  .build());

		return ResponseEntity.status(CREATED).body(CreateUserResponse.builder()
		  .message("User created successfully")
		  .username(createdUser.getUsername())
		  .build());
	}

	@RequestMapping(value = "/all", method = GET)
	public ResponseEntity<GetUserAllResponse> getAllUser() {

		return ResponseEntity.ok(userService.readAll());
	}

	@RequestMapping(value = "/{id}", method = PUT)
	public ResponseEntity<UserUpdateResponse> updateUser(@RequestBody @Valid UpdateUserRequest request,
	  @PathVariable UUID id) {
		UserUpdateResponse response = userService.update(UpdateUserDTO.builder()
		  .userId(id)
		  .newUsername(request.getUsername())
		  .newEmail(request.getEmail())
		  .newPassword(request.getPassword())
		  .build());

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable UUID id) {
		return ResponseEntity.ok(userService.delete(id));
	}

	@RequestMapping(value = "/status/{userID}", method = PUT)
	public ResponseEntity<UpdateUserStatusResponse> updateUserStatus(@PathVariable UUID userID) {
		UserStatus newUserStatus = userStatusService.updateByUserId(userID);

		UpdateUserStatusResponse response = UpdateUserStatusResponse.builder()
		  .userId(newUserStatus.getUserId())
		  .lastActiveAt(newUserStatus.getLastActiveAt())
		  .isOnline(newUserStatus.isOnline())
		  .build();

		return ResponseEntity.ok(response);
	}

}
