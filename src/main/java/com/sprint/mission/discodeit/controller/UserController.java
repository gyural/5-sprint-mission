package com.sprint.mission.discodeit.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.dto.CreateUserDTO;
import com.sprint.mission.discodeit.domain.dto.UpdateUserDTO;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.domain.request.CreatUsereRequest;
import com.sprint.mission.discodeit.domain.request.CreateUserResponse;
import com.sprint.mission.discodeit.domain.request.UpdateUserRequest;
import com.sprint.mission.discodeit.domain.response.UpdateUserStatusResponse;
import com.sprint.mission.discodeit.domain.response.UserDeleteResponse;
import com.sprint.mission.discodeit.domain.response.UserReadResponse;
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

	@RequestMapping(value = "", method = POST, consumes = "multipart/form-data")
	public ResponseEntity<CreateUserResponse> createUser(
	  @RequestPart @Valid CreatUsereRequest userCreateRequest,
	  @RequestPart(required = false) MultipartFile profilePicture

	) throws IOException {
		Optional<CreateBiContentDTO> biContentDTO = Optional.empty();
		if (profilePicture != null && !profilePicture.isEmpty()) {
			String filename = profilePicture.getOriginalFilename();
			String contentType;
			String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
			switch (ext) {
				case "jpg":
				case "jpeg":
					contentType = "image/jpeg";
					break;
				case "png":
					contentType = "image/png";
					break;
				case "gif":
					contentType = "image/gif";
					break;
				default:
					contentType = "application/octet-stream";
			}

			biContentDTO = Optional.of(new CreateBiContentDTO(
			  profilePicture.getBytes(),
			  profilePicture.getSize(),
			  contentType,
			  profilePicture.getOriginalFilename()
			));
		}

		User createdUser = userService.create(CreateUserDTO.builder()
		  .username(userCreateRequest.getUsername())
		  .email(userCreateRequest.getEmail())
		  .password(userCreateRequest.getPassword())
		  .binaryContent(biContentDTO.orElse(null))
		  .build());

		return ResponseEntity.status(CREATED).body(CreateUserResponse.builder()
		  .username(createdUser.getUsername())
		  .email(createdUser.getEmail())
		  .id(createdUser.getId())
		  .profileId(createdUser.getProfileId())
		  .createdAt(createdUser.getCreatedAt())
		  .updatedAt(createdUser.getUpdatedAt())
		  .build());
	}

	@RequestMapping(value = "/findAll", method = GET)
	public ResponseEntity<List<UserReadResponse>> getAllUser() {

		return ResponseEntity.ok(userService.readAll());
	}

	@RequestMapping(value = "/{id}", method = PUT, consumes = "multipart/form-data")
	public ResponseEntity<UserUpdateResponse> updateUser(
	  @RequestPart @Valid UpdateUserRequest updateUserRequest,
	  @RequestPart(required = false) MultipartFile profilePicture,
	  @PathVariable UUID id
	) throws IOException {

		Optional<CreateBiContentDTO> biContentDTO = Optional.empty();
		if (profilePicture != null && !profilePicture.isEmpty()) {

			biContentDTO = Optional.of(new CreateBiContentDTO(
			  profilePicture.getBytes(),
			  profilePicture.getSize(),
			  profilePicture.getContentType(),
			  profilePicture.getOriginalFilename()
			));
		}

		UserUpdateResponse response = userService.update(UpdateUserDTO.builder()
		  .userId(id)
		  .newUsername(updateUserRequest.getUsername())
		  .newEmail(updateUserRequest.getEmail())
		  .newPassword(updateUserRequest.getPassword())
		  .newProfilePicture(biContentDTO.orElse(null))
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
