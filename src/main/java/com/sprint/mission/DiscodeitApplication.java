package com.sprint.mission;

import static com.sprint.mission.discodeit.domain.enums.ChannelType.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.CreateBiContentDTO;
import com.sprint.mission.discodeit.domain.dto.CreateReadStatusDTO;
import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.ReadChannelResponse;
import com.sprint.mission.discodeit.domain.dto.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserReadDTO;
import com.sprint.mission.discodeit.domain.dto.UserStatusCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserStatusUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.domain.enums.ContentType;
import com.sprint.mission.discodeit.domain.request.UserLoginRequest;
import com.sprint.mission.discodeit.domain.response.UserLoginResponse;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileBinaryContentRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileReadStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.file.FileUserStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.AuthService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.basic.BinaryContentService;
import com.sprint.mission.discodeit.service.basic.ReadStatusService;
import com.sprint.mission.discodeit.service.basic.UserStatusService;

@SpringBootApplication
public class DiscodeitApplication {

	static BinaryContent setupBinaryContent(BinaryContentRepository binaryContentRepository) {
		byte[] bytes;
		try {
			Path imagePath = Path.of("dummyImage.png");
			bytes = Files.readAllBytes(imagePath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		BinaryContent dummyBinaryContent =
		  new BinaryContent(bytes, bytes.length, ContentType.IMAGE, "dummyImage.png");

		return binaryContentRepository.save(dummyBinaryContent);
	}

	static User setupUser(UserService userService, BinaryContent binaryContent) {
		return userService.create(
		  UserCreateDTO.builder()
			.username("woody")
			.email("woody@codeit.com")
			.password("woody1234")
			.binaryContent(binaryContent)
			.build());
	}

	static Channel setupPUblicChannel(ChannelService channelService) {
		return channelService.createPublic(
		  ChannelCreateDTO.builder().description("공지 채널입니다.").name("공지").build());
	}

	static Message setupMessage(MessageService messageService, Channel channel, User author) {
		return messageService.create(
		  MessageCreateDTO.builder().channelId(channel.getId()).content("안녕하세요").userId(author.getId()).build());
	}

	static void channelCreateTest(ChannelService channelService, ReadStatusRepository readStatusRepository,
	  UserRepository userRepository) {
		System.out.print("ChannelCreateTest.......................");

		// Given
		//  Member List 생성
		int size = 5;
		for (int i = 1; i <= size; i++) {
			userRepository.save(
			  new User("member" + i, "member" + i + "@codeit.com", "member1234", null)
			);
		}
		List<User> memberList = userRepository.findAll();

		// When
		// public 채널과 private 채널을 생성
		Channel publicChannel = channelService.createPublic(
		  ChannelCreateDTO.builder().description("공개 공지 채널입니다.").name("공개 공지").build());
		Channel privateChannel = channelService.createPrivate(
		  ChannelCreateDTO.builder().members(memberList).build());

		// Then
		// 1. UserStatus가 생성되었는지 확인
		List<ReadStatus> readStatuseList = readStatusRepository.findAll();
		boolean isMemberStatusCreated = memberList.stream()
		  .allMatch(member -> readStatuseList.stream()
			.anyMatch(readStatus -> readStatus.getUserId().equals(member.getId())));
		// 2. 채널이 잘 생성되었는지 확인
		boolean isSuccess = !channelService.isEmpty(publicChannel.getId()) &&
		  !channelService.isEmpty(privateChannel.getId()) &&
		  publicChannel.getChannelType() == PUBLIC &&
		  privateChannel.getChannelType() == PRIVATE &&
		  publicChannel.getName().equals("공개 공지") &&
		  publicChannel.getDescription().equals("공개 공지 채널입니다.") &&
		  isMemberStatusCreated;

		System.out.println(isSuccess ?
		  "채널 생성 테스트 통과 ✅" :
		  "채널 생성 테스트 실패 ❌");
	}

	static void channelReadTest(ChannelService channelService, UserRepository userRepository,
	  MessageRepository messageRepository) {
		System.out.print("channelReadTest.......................");

		// Given
		// 1.  MemberList 생성
		int size = 5;
		for (int i = 1; i <= size; i++) {
			userRepository.save(
			  new User("member" + i, "member" + i + "@codeit.com", "member1234", null)
			);
		}
		List<User> memberList = userRepository.findAll();

		// 2. public 채널과 private 채널을 생성
		Channel publicChannel = channelService.createPublic(
		  ChannelCreateDTO.builder().description("공개 공지 채널입니다.").name("공개 공지").build());

		Channel privateChannel = channelService.createPrivate(
		  ChannelCreateDTO.builder().members(memberList).build());

		// 3. Message 생성
		messageRepository.save(
		  new Message(
			"안녕하세요", memberList.get(0).getId(), privateChannel.getId(), memberList.get(0).getUsername())
		);
		Message lastMessage = messageRepository.findAllByChannelId(privateChannel.getId()).get(0);

		// When
		ReadChannelResponse readPublicChannel = channelService.read(publicChannel.getId());
		ReadChannelResponse readPrivateChannel = channelService.read(privateChannel.getId());

		// Then
		boolean isPublicChannelValid = readPublicChannel.getId().equals(publicChannel.getId()) &&
		  readPublicChannel.getName().equals(publicChannel.getName()) &&
		  readPublicChannel.getDescription().equals(publicChannel.getDescription());

		boolean isMemberIdListValid = !readPrivateChannel.getMembersIDList().isEmpty() &&
		  new HashSet<>(readPrivateChannel.getMembersIDList()).containsAll(
			memberList.stream().map(User::getId).toList());
		boolean isPrivateChannelValid =
		  readPrivateChannel.getMembersIDList().size() == memberList.size() &&
			readPrivateChannel.getChannelType() == privateChannel.getChannelType() &&
			readPrivateChannel.getId().equals(privateChannel.getId()) &&
			readPrivateChannel.getLastMessageAt()
			  .equals(lastMessage.getUpdatedAt() == null ? lastMessage.getCreatedAt() : lastMessage.getUpdatedAt());

		boolean isValid = isPublicChannelValid &&
		  isPrivateChannelValid &&
		  isMemberIdListValid;

		System.out.println(isValid ?
		  "채널 조회 테스트 통과 ✅" :
		  "채널 조회 테스트 실패 ❌");
	}

	static void channelReadAllTest(UserRepository userRepository, MessageRepository messageRepository,
	  ChannelService channelService) {
		System.out.print("channelReadAllTest.......................");

		// Given 유저 2명 생성
		User user1 = userRepository.save(new User("user1", "user1@codeit.com", "pw1", null));
		User user2 = userRepository.save(new User("user2", "user2@codeit.com", "pw2", null));
		User userNoChannel = userRepository.save(new User("user3", "user3@codeit.com", "pw3", null));

		// PUBLIC 채널 생성
		Channel publicChannel = channelService.createPublic(
		  ChannelCreateDTO.builder().name("공지").description("공지 채널").build());

		// PRIVATE 채널 생성 (user1, user2 참여)
		List<User> members = List.of(user1, user2);
		Channel privateChannel = channelService.createPrivate(
		  ChannelCreateDTO.builder().members(members).build());

		// 각 채널에 메시지 생성
		Message pubMsg = messageRepository.save(
		  new Message("public msg", user1.getId(), publicChannel.getId(), user1.getUsername()));
		Message privMsg = messageRepository.save(
		  new Message("private msg", user2.getId(), privateChannel.getId(), user2.getUsername()));

		// When
		List<ReadChannelResponse> channelsReqByPublicUser = channelService.findAllByUserId(userNoChannel.getId());
		List<ReadChannelResponse> channelsReqByPrivateUser = channelService.findAllByUserId(user1.getId());

		// Then
		// PUBLIC 채널은 항상 포함, PRIVATE 채널은 참여자만 조회됨
		boolean hasPublic = channelsReqByPublicUser.stream().anyMatch(c -> c.getId().equals(publicChannel.getId()));
		boolean hasNoPrivate = channelsReqByPublicUser.stream()
		  .noneMatch(c -> c.getId().equals(privateChannel.getId()));
		boolean hasPrivate = channelsReqByPrivateUser.stream().anyMatch(c -> c.getId().equals(privateChannel.getId()));

		// 최근 메시지 시간 검증
		ReadChannelResponse pubResp = channelsReqByPublicUser.stream()
		  .filter(c -> c.getId().equals(publicChannel.getId()))
		  .findFirst()
		  .orElse(null);
		ReadChannelResponse privResp = channelsReqByPrivateUser.stream()
		  .filter(c -> c.getId().equals(privateChannel.getId()))
		  .findFirst()
		  .orElse(null);

		boolean pubMsgTimeValid = pubResp != null && pubResp.getLastMessageAt().equals(pubMsg.getCreatedAt());
		boolean privMsgTimeValid = privResp != null && privResp.getLastMessageAt().equals(privMsg.getCreatedAt());

		// PRIVATE 채널 참여자 id 검증
		boolean privMembersValid = privResp != null &&
		  new HashSet<>(privResp.getMembersIDList()).containsAll(List.of(user1.getId(), user2.getId()));

		boolean isValid =
		  hasPublic && hasPrivate && hasNoPrivate && pubMsgTimeValid && privMsgTimeValid && privMembersValid;

		System.out.println(isValid ?
		  "채널 전체 조회 테스트 통과 ✅" :
		  "채널 전체 조회 테스트 실패 ❌");
	}

	static void channelUpdateTest(ChannelService channelService, Channel channel) {
		System.out.print("channelUpdateTest.......................");

		// Given
		String newName = "업데이트된 채널";
		String newDescription = "업데이트된 채널 설명";

		// When
		channelService.update(ChannelUpdateDTO.builder()
		  .id(channel.getId())
		  .channelType(channel.getChannelType())
		  .name(newName)
		  .description(newDescription)
		  .build());

		// Then
		ReadChannelResponse channelToValidate = channelService.read(channel.getId());
		boolean isUpdated = channelToValidate.getName().equals(newName) &&
		  channelToValidate.getDescription().equals(newDescription);

		System.out.println(isUpdated ?
		  "채널 업데이트 테스트 통과 ✅" :
		  "채널 업데이트 테스트 실패 ❌");
	}

	static void channelDeleteTest(ChannelService channelService, Channel channel) {
		System.out.print("channelDeleteTest.......................");

		channelService.delete(channel.getId());
		String log = channelService.isEmpty(channel.getId()) ?
		  "채널이 삭제 테스트 통과 ✅" :
		  "채널이 삭제 테스트 실패 ❌";
		System.out.println(log);
	}

	/**
	 * 사용자 생성 테스트
	 * - 프로필 이미지가 있는 경우와 없는 경우를 모두 테스트
	 */
	static void userCreateTest(
	  UserService userService,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("userCreateTest.......................");

		// Given
		BinaryContent userProfileImage = setupBinaryContent(binaryContentRepository);
		UserCreateDTO dtoWithProfileImage =
		  UserCreateDTO.builder()
			.username("newUser1")
			.email("newUser1@codeit.com")
			.password("newUser1234")
			.binaryContent(userProfileImage)
			.build();
		UserCreateDTO dtoWithNoProfileImage =
		  UserCreateDTO.builder()
			.username("newUser2")
			.email("newUser2@codeit.com")
			.password("newUser1234")
			.build();

		// When
		User u1 = userService.create(dtoWithProfileImage);
		User u2 = userService.create(dtoWithNoProfileImage);

		// Then
		UserReadDTO userWithProfile = userService.read(u1.getId());
		UserReadDTO userWithNoProfile = userService.read(u2.getId());

		// 1. UserProfile가 생성 여부 확인
		// UserProfile 잘 생성 되었는지 확인
		boolean isProfileCreated = userWithProfile.getProfileId() != null &&
		  userWithProfile.getProfileId().equals(userProfileImage.getId()) &&
		  !binaryContentRepository.isEmpty(userWithProfile.getProfileId());
		if (!isProfileCreated) {
			System.out.println("UserWithProfile 생성 실패 ❌");
			return;
		}
		// UserProfile 없이 생성 되었는지 확인
		boolean isNonProfileUserCreated = userWithNoProfile.getProfileId() == null;
		if (!isNonProfileUserCreated) {
			System.out.println("UserNoProfile 생성 실패 ❌");
			return;
		}

		// 2. User 일반 필드 잘 생성 되었는지 확인
		boolean isCreated = u1.getUsername().equals(dtoWithProfileImage.getUsername()) &&
		  u1.getEmail().equals(dtoWithProfileImage.getEmail()) &&
		  u1.getPassword().equals(dtoWithProfileImage.getPassword()) &&
		  u2.getUsername().equals(dtoWithNoProfileImage.getUsername()) &&
		  u2.getEmail().equals(dtoWithNoProfileImage.getEmail()) &&
		  u2.getPassword().equals(dtoWithNoProfileImage.getPassword());
		System.out.println(isCreated ?
		  "사용자 생성 테스트 통과 ✅" :
		  "사용자 생성 테스트 실패 ❌");
	}

	/**
	 * 사용자 조회 테스트
	 * - password는 제외하고 online 정보가 잘 조회되는지 확인
	 */
	static void userReadTest(UserService userService, User user) {
		System.out.print("UserReadTest.......................");
		UserReadDTO readUser = userService.read(user.getId());

		boolean isValid = readUser.getId().equals(user.getId()) &&
		  readUser.getCreatedAt().equals(user.getCreatedAt()) &&
		  readUser.getUsername().equals(user.getUsername()) &&
		  readUser.getEmail().equals(user.getEmail()) &&
		  readUser.getIsOnline() != null;
		// 프로필 이미지가 있는 경우
		if (user.getProfileId() != null) {
			isValid = isValid && readUser.getProfileId().equals(user.getProfileId());
		} else {
			isValid = isValid && readUser.getProfileId() == null;
		}

		System.out.println(isValid ?
		  "사용자 조회 테스트 통과 ✅" :
		  "사용자 조회 테스트 실패 ❌");
	}

	/**
	 * 사용자 갱신 테스트
	 * - 프로필 이미지를 대체할 수 있는지도 확인
	 */
	static void userUpdateTest(UserService userService, User user,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("UserUpdateTest.......................");

		String newUsername = "updatedUser";
		String newEmail = "updateEmail@codeit.com";
		String newPassword = "updatedPassword1234";
		BinaryContent newProfileImage = setupBinaryContent(binaryContentRepository);

		userService.update(UserUpdateDTO.builder()
		  .userId(user.getId())
		  .newUsername(newUsername)
		  .newEmail(newEmail)
		  .newPassword(newPassword)
		  .newProfileImage(newProfileImage)
		  .build());

		UserReadDTO userToValidate = userService.read(user.getId());

		boolean isUpdated = userToValidate.getUsername().equals(newUsername) &&
		  userToValidate.getEmail().equals(newEmail) &&
		  userToValidate.getProfileId().equals(newProfileImage.getId()) &&
		  binaryContentRepository.find(newProfileImage.getId()).isPresent();

		System.out.println(isUpdated ?
		  "사용자 업데이트 테스트 통과 ✅" :
		  "사용자 업데이트 테스트 실패 ❌");

	}

	/**
	 * 사용자 삭제 테스트
	 * - 사용자 삭제 후, 관련된 데이터도 삭제되는지 확인
	 */
	static void userDeleteTest(UserService userService, User user, UserStatusRepository userStatusRepository,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("UserDeleteTest.......................");
		// when
		userService.delete(user.getId());

		// then
		String log = userService.isEmpty(user.getId()) &&
		  userStatusRepository.findByUserId(user.getId()).isEmpty() &&
		  binaryContentRepository.find(user.getProfileId()).isEmpty() ?
		  "사용자 삭제 테스트 통과 ✅" :
		  "사용자 삭제 테스트 실패 ❌";
		System.out.println(log);
	}

	static void messageCreateTest(MessageService messageService, Channel channel, User author,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("MessageCreateTest.......................");

		// Given
		BinaryContent messageAttachment1 = setupBinaryContent(binaryContentRepository);
		BinaryContent messageAttachment2 = setupBinaryContent(binaryContentRepository);
		List<BinaryContent> attachments = List.of(messageAttachment1, messageAttachment2);

		// WHEN
		Message message = messageService.create(
		  MessageCreateDTO.builder()
			.channelId(channel.getId())
			.content("안녕하세요")
			.userId(author.getId())
			.attachments(attachments)
			.build()
		);

		// THEN
		Message storedMessage = messageService.read(message.getId());
		List<UUID> storedAttachments = binaryContentRepository.findAll().stream().map(BinaryContent::getId).toList();

		boolean isCreated = messageService.read(message.getId()) != null &&
		  storedMessage.getContent().equals("안녕하세요") &&
		  storedMessage.getChannelId().equals(channel.getId()) &&
		  storedMessage.getAuthorId().equals(author.getId()) &&
		  storedMessage.getAttachmentIds() != null &&
		  storedMessage.getAttachmentIds().size() == 2 &&
		  storedMessage.getAttachmentIds().contains(messageAttachment1.getId()) &&
		  storedMessage.getAttachmentIds().contains(messageAttachment2.getId()) &&
		  storedAttachments.contains(messageAttachment1.getId()) &&
		  storedAttachments.contains(messageAttachment2.getId());

		System.out.println(isCreated ?
		  "메시지 생성 테스트 통과 ✅" :
		  "메시지 생성 테스트 실패 ❌");
	}

	static void messageReadTest(MessageService messageService, BinaryContentRepository binaryContentRepository,
	  Channel channel, User author) {
		System.out.print("MessageReadTest.......................");

		// Given
		BinaryContent messageAttachment1 = setupBinaryContent(binaryContentRepository);
		BinaryContent messageAttachment2 = setupBinaryContent(binaryContentRepository);
		List<BinaryContent> attachments = List.of(messageAttachment1, messageAttachment2);
		Message message = messageService.create(
		  MessageCreateDTO.builder()
			.channelId(channel.getId())
			.content("안녕하세요")
			.userId(author.getId())
			.attachments(attachments)
			.build()
		);

		// When
		Message readMessage = messageService.read(message.getId());

		// Then
		boolean isValid = readMessage.getId().equals(message.getId()) &&
		  readMessage.getContent().equals(message.getContent()) &&
		  readMessage.getChannelId().equals(message.getChannelId()) &&
		  readMessage.getAuthorId().equals(message.getAuthorId()) &&
		  readMessage.getAttachmentIds() != null &&
		  readMessage.getAttachmentIds().size() == 2 &&
		  readMessage.getAttachmentIds().contains(messageAttachment1.getId()) &&
		  readMessage.getAttachmentIds().contains(messageAttachment2.getId());

		System.out.println(isValid ?
		  "메시지 조회 테스트 통과 ✅" :
		  "메시지 조회 테스트 실패 ❌");
	}

	static void messageUpdateTest(MessageService messageService, Message message) {
		System.out.print("MessageUpdateTest.......................");

		String newContent = "업데이트된 메시지 내용";
		messageService.update(MessageUpdateDTO.builder().id(message.getId()).newContent(newContent).build());

		Message updatedMessage = messageService.read(message.getId());
		boolean isUpdated = updatedMessage.getContent().equals(newContent);

		System.out.println(isUpdated ?
		  "메시지 업데이트 테스트 통과 ✅" :
		  "메시지 업데이트 테스트 실패 ❌");
	}

	static void messageDeleteTest(MessageService messageService, BinaryContentRepository binaryContentRepository,
	  Channel channel, User author) {
		System.out.print("MessageDeleteTest.......................");

		// Given
		BinaryContent messageAttachment1 = setupBinaryContent(binaryContentRepository);
		BinaryContent messageAttachment2 = setupBinaryContent(binaryContentRepository);
		List<BinaryContent> attachments = List.of(messageAttachment1, messageAttachment2);
		Message message = messageService.create(
		  MessageCreateDTO.builder()
			.channelId(channel.getId())
			.content("안녕하세요")
			.userId(author.getId())
			.attachments(attachments)
			.build()
		);
		messageService.delete(message.getId());
		String log = messageService.isEmpty(message.getId()) && binaryContentRepository.findAll().isEmpty() ?
		  "메시지 삭제 테스트 통과 ✅" :
		  "메시지 삭제 테스트 실패 ❌";
		System.out.println(log);
	}

	static void clearAll(ChannelService channelService, UserService userService, MessageService messageService,
	  UserStatusRepository userStatusRepository, BinaryContentRepository binaryContentRepository,
	  ReadStatusRepository readStatusRepository) {
		channelService.deleteAll();
		userService.deleteAll();
		messageService.deleteAll();
		userStatusRepository.deleteAll();
		binaryContentRepository.deleteAll();
		readStatusRepository.deleteAll();
	}

	static void authLoginTest(AuthService authService) {
		System.out.print("AuthLoginTest.......................");

		// Given
		UserLoginRequest successRequest = UserLoginRequest.builder()
		  .username("woody")
		  .password("woody1234")
		  .build();

		UserLoginResponse successResponse = authService.login(successRequest);

		boolean isValid = successResponse.isSuccess() && successResponse.getUser() != null &&
		  successResponse.getUser().getUsername().equals(successRequest.getUsername());

		System.out.println(isValid ?
		  "로그인 테스트 통과 ✅" :
		  "로그인 테스트 실패 ❌");
	}

	static void createReadStatusTest(ReadStatusService readStatusService, UserService basicUserService,
	  BinaryContentRepository binaryContentRepository, ChannelService channelService,
	  ReadStatusRepository readStatusRepository) {
		System.out.print("createReadStatusTest.......................");

		// Given
		User user = setupUser(basicUserService, setupBinaryContent(binaryContentRepository));
		Channel channel = setupPUblicChannel(channelService);

		// When
		// ReadStatusService를 통해 ReadStatus 생성
		readStatusService.create(
		  CreateReadStatusDTO.builder()
			.userId(user.getId())
			.channelId(channel.getId())
			.build());
		// Then
		// ReadStatus가 잘 생성되었는지 확인
		readStatusRepository.findByUserIdAndChannelId(user.getId(), channel.getId())
		  .ifPresentOrElse(
			readStatus -> System.out.println("ReadStatus 생성 테스트 통과 ✅"),
			() -> System.out.println("ReadStatus 생성 테스트 실패 ❌")
		  );

	}

	static void readReadStatusTest(UserService userService, BinaryContentRepository binaryContentRepository,
	  ChannelService channelService,
	  ReadStatusService readStatusService) {
		System.out.print("readReadStatusTest.......................");

		// Given
		User user = setupUser(userService, setupBinaryContent(binaryContentRepository));
		Channel channel1 = setupPUblicChannel(channelService);
		Channel channel2 = setupPUblicChannel(channelService);

		// ReadStatusService를 통해 ReadStatus 생성
		readStatusService.create(
		  CreateReadStatusDTO.builder()
			.userId(user.getId())
			.channelId(channel1.getId())
			.build());

		readStatusService.create(
		  CreateReadStatusDTO.builder()
			.userId(user.getId())
			.channelId(channel2.getId())
			.build());

		// When
		// ReadStatusService를 통해 ReadStatus 조회
		List<ReadStatus> readStatuses = readStatusService.findAllByUserId(user.getId());

		// Then
		// ReadStatus가 잘 조회되었는지 확인
		boolean isValid = readStatuses.size() == 2 &&
		  readStatuses.stream().anyMatch(rs -> rs.getChannelId().equals(channel1.getId())) &&
		  readStatuses.stream().anyMatch(rs -> rs.getChannelId().equals(channel2.getId()));

		String result = isValid ?
		  "ReadStatus 조회 테스트 통과 ✅" :
		  "ReadStatus 조회 테스트 실패 ❌";
		System.out.println(result);

	}

	static void updateReadStatusTest(UserService userService,
	  BinaryContentRepository binaryContentRepository, ChannelService channelService,
	  ReadStatusService readStatusService, ReadStatusRepository readStatusRepository) {
		System.out.print("updateReadStatusTest.......................");

		// Given
		User user = setupUser(userService, setupBinaryContent(binaryContentRepository));
		Channel channel = setupPUblicChannel(channelService);

		// ReadStatusService를 통해 ReadStatus 생성
		ReadStatus readStatus = readStatusService.create(
		  CreateReadStatusDTO.builder()
			.userId(user.getId())
			.channelId(channel.getId())
			.build());

		Instant beforeUpdate = readStatus.getUpdatedAt() == null ? Instant.now() : readStatus.getUpdatedAt();

		// When
		// ReadStatusService를 통해 ReadStatus 업데이트
		readStatusService.update(
		  UpdateReadStatusDTO.builder()
			.id(readStatus.getId())
			.build());

		// Then
		// ReadStatusRepository를 통해 ReadStatus 조회
		ReadStatus updatedReadStatus = readStatusRepository.find(readStatus.getId())
		  .orElseThrow(() -> new IllegalArgumentException("ReadStatus not found"));
		Instant afterUpdate = updatedReadStatus.getUpdatedAt();

		// ReadStatus가 잘 업데이트 되었는지 확인
		String result = afterUpdate.isAfter(beforeUpdate) ?
		  "ReadStatus 업데이트 테스트 통과 ✅" :
		  "ReadStatus 업데이트 테스트 실패 ❌";

		System.out.println(result);

	}

	static void deleteReadStatusTest(UserService userService,
	  BinaryContentRepository binaryContentRepository, ChannelService channelService,
	  ReadStatusService readStatusService, ReadStatusRepository readStatusRepository) {
		System.out.print("deleteReadStatusTest.......................");

		// Given
		User user = setupUser(userService, setupBinaryContent(binaryContentRepository));
		Channel channel = setupPUblicChannel(channelService);

		// ReadStatusService 통해 ReadStatus 생성
		ReadStatus readStatus = readStatusService.create(
		  CreateReadStatusDTO.builder()
			.userId(user.getId())
			.channelId(channel.getId())
			.build());

		// When
		// ReadStatusService를 통해 ReadStatus 삭제
		readStatusService.delete(readStatus.getId());

		// Then
		// ReadStatusRepository를 통해 ReadStatus가 잘 삭제되었는지 확인
		boolean isDeleted = readStatusRepository.find(readStatus.getId()).isEmpty();
		String result = isDeleted ?
		  "ReadStatus 삭제 테스트 통과 ✅" :
		  "ReadStatus 삭제 테스트 실패 ❌";
		System.out.println(result);

	}

	static void createUserStatusTest(UserStatusService userStatusService, UserService userService,
	  BinaryContentRepository binaryContentRepository, UserStatusRepository userStatusRepository) {
		System.out.print("createUserStatusTest.......................");

		// Given
		User user = setupUser(userService, setupBinaryContent(binaryContentRepository));

		// When
		UserStatus userStatus = userStatusService.create(UserStatusCreateDTO.builder()
		  .userId(user.getId())
		  .build());

		// Then
		boolean isCreated = userStatusRepository.find(userStatus.getId()).isPresent();
		String result = isCreated ?
		  "UserStatus 생성 테스트 통과 ✅" :
		  "UserStatus 생성 테스트 실패 ❌";
		System.out.println(result);
	}

	static void readUserStatusTest(UserStatusService userStatusService, UserService userService,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("readUserStatusTest.......................");

		// Given
		User user1 = setupUser(userService, setupBinaryContent(binaryContentRepository));
		User user2 = userService.create(
		  UserCreateDTO.builder()
			.username("woody2")
			.email("woody2@codeit.com")
			.password("woody1234")
			.binaryContent(setupBinaryContent(binaryContentRepository))
			.build());

		UserStatus userStatus1 = userStatusService.create(UserStatusCreateDTO.builder()
		  .userId(user1.getId())
		  .build());
		UserStatus userStatus2 = userStatusService.create(UserStatusCreateDTO.builder()
		  .userId(user2.getId())
		  .build());

		// When
		UserStatus readUserStatusByFind = userStatusService.find(userStatus1.getId());
		List<UserStatus> readUserStatusByFindAll = userStatusService.findAll();

		// Then
		boolean isValid = readUserStatusByFind.getId().equals(userStatus1.getId()) &&
		  readUserStatusByFind.getUserId().equals(user1.getId()) &&
		  readUserStatusByFindAll.size() == 2 &&
		  readUserStatusByFindAll.stream().anyMatch(us -> us.getId().equals(userStatus1.getId())) &&
		  readUserStatusByFindAll.stream().anyMatch(us -> us.getId().equals(userStatus2.getId()));

		String result = isValid ?
		  "UserStatus 조회 테스트 통과 ✅" :
		  "UserStatus 조회 테스트 실패 ❌";
		System.out.println(result);
	}

	static void updateUserStatusTest(UserStatusService userStatusService, UserService userService,
	  BinaryContentRepository binaryContentRepository, UserStatusRepository userStatusRepository) {
		System.out.print("updateUserStatusTest.......................");

		// Given
		User user = setupUser(userService, setupBinaryContent(binaryContentRepository));
		UserStatus userStatus = userStatusService.create(UserStatusCreateDTO.builder()
		  .userId(user.getId())
		  .build());
		Instant beforeUpdate = userStatus.getUpdatedAt() == null ? Instant.now() : userStatus.getUpdatedAt();

		// When & Then
		// update 와 updateByUserId 메서드를 통해 두번 업데이트
		userStatusService.update(UserStatusUpdateDTO.builder()
		  .id(userStatus.getId())
		  .build());
		Instant firstUpdate = userStatusRepository.find(userStatus.getId()).get().getUpdatedAt();

		userStatusService.updateByUserId(userStatus.getUserId());
		Instant secondUpdate = userStatusRepository.find(userStatus.getId()).get().getUpdatedAt();

		boolean isUpdated = firstUpdate.isAfter(beforeUpdate) &&
		  secondUpdate.isAfter(firstUpdate);

		String result = isUpdated ?
		  "UserStatus 업데이트 테스트 통과 ✅" :
		  "UserStatus 업데이트 테스트 실패 ❌";
		System.out.println(result);
	}

	static void deleteUserStatusTest(UserStatusService userStatusService, UserService userService,
	  BinaryContentRepository binaryContentRepository, UserStatusRepository userStatusRepository) {
		System.out.print("deleteUserStatusTest.......................");

		// Given
		User user = setupUser(userService, setupBinaryContent(binaryContentRepository));
		UserStatus userStatus = userStatusService.create(UserStatusCreateDTO.builder()
		  .userId(user.getId())
		  .build());

		// When
		userStatusService.delete(userStatus.getId());

		// Then
		boolean isDeleted = userStatusRepository.find(userStatus.getId()).isEmpty();
		String result = isDeleted ?
		  "UserStatus 삭제 테스트 통과 ✅" :
		  "UserStatus 삭제 테스트 실패 ❌";
		System.out.println(result);
	}

	static void createBinaryContentTest(BinaryContentService binaryContentService,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("createBinaryContentTest.......................");

		// Given
		CreateBiContentDTO dto = CreateBiContentDTO.builder()
		  .content(new byte[] {1, 2, 3, 4, 5})
		  .size(5)
		  .contentType(ContentType.IMAGE)
		  .filename("testImage.png")
		  .build();

		// When
		binaryContentService.create(dto);

		// Then
		BinaryContent storedContent = binaryContentRepository.findAll().stream().findFirst().orElse(null);
		boolean isValid = storedContent != null && storedContent.getFilename().equals(dto.getFilename()) &&
		  storedContent.getSize() == dto.getSize() &&
		  storedContent.getContentType() == dto.getContentType() &&
		  Arrays.equals(storedContent.getContent(), dto.getContent());
		String result = isValid ?
		  "BinaryContent 생성 테스트 통과 ✅" :
		  "BinaryContent 생성 테스트 실패 ❌";
		System.out.println(result);
	}

	static void readBinaryContentTest(BinaryContentService binaryContentService,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("readBinaryContentTest.......................");

		// Given
		BinaryContent binaryContent1 = setupBinaryContent(binaryContentRepository);
		BinaryContent binaryContent2 = setupBinaryContent(binaryContentRepository);
		BinaryContent binaryContent3 = setupBinaryContent(binaryContentRepository);

		// When
		BinaryContent foundContentByfind = binaryContentService.find(binaryContent1.getId());
		List<BinaryContent> foundContentByfindAll = binaryContentService.findAllByIdIn(
		  List.of(binaryContent1.getId(), binaryContent2.getId(), binaryContent3.getId()));

		// Then
		boolean isValid = foundContentByfind != null &&
		  foundContentByfind.getId().equals(binaryContent1.getId()) &&
		  foundContentByfindAll.size() == 3 &&
		  foundContentByfindAll.stream().anyMatch(bc -> bc.getId().equals(binaryContent1.getId())) &&
		  foundContentByfindAll.stream().anyMatch(bc -> bc.getId().equals(binaryContent2.getId())) &&
		  foundContentByfindAll.stream().anyMatch(bc -> bc.getId().equals(binaryContent3.getId()));

		String result = isValid ?
		  "BinaryContent 조회 테스트 통과 ✅" :
		  "BinaryContent 조회 테스트 실패 ❌";
		System.out.println(result);
	}

	static void deleteBinaryContentTest(BinaryContentService binaryContentService,
	  BinaryContentRepository binaryContentRepository) {
		System.out.print("deleteBinaryContentTest.......................");

		// Given
		BinaryContent binaryContent = setupBinaryContent(binaryContentRepository);

		// When
		binaryContentService.delete(binaryContent.getId());

		// Then
		boolean isDeleted = binaryContentRepository.find(binaryContent.getId()).isEmpty();
		String result = isDeleted ?
		  "BinaryContent 삭제 테스트 통과 ✅" :
		  "BinaryContent 삭제 테스트 실패 ❌";
		System.out.println(result);
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		// setup-1 FILE 레포지토리 초기화
		UserRepository userRepository = context.getBean(FileUserRepository.class);
		ChannelRepository channelRepository = context.getBean(FileChannelRepository.class);
		MessageRepository messageRepository = context.getBean(FileMessageRepository.class);
		BinaryContentRepository binaryContentRepository = context.getBean(FileBinaryContentRepository.class);
		UserStatusRepository userStatusRepository = context.getBean(FileUserStatusRepository.class);
		ReadStatusRepository readStatusRepository = context.getBean(FileReadStatusRepository.class);
		// setup-2 Basic 서비스 초기화
		UserService basicUserService = context.getBean(BasicUserService.class);
		MessageService basicMessageService = context.getBean(BasicMessageService.class);
		ChannelService basicChannelService = context.getBean(BasicChannelService.class);

		// setup-3 service 초기화
		AuthService authService = context.getBean(AuthService.class);
		ReadStatusService readStatusService = context.getBean(ReadStatusService.class);
		UserStatusService userStatusService = context.getBean(UserStatusService.class);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ 💾 [FILE] 저장소 기반 BasicService 테스트 시작 ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

		// 💥💥💥 Channel Test Start 💥💥💥
		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃     📡 CHANNEL TEST           ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);
		channelCreateTest(basicChannelService, readStatusRepository, userRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		channelReadTest(basicChannelService, userRepository, messageRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		channelReadAllTest(userRepository, messageRepository, basicChannelService
		);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		channelUpdateTest(basicChannelService, setupPUblicChannel(basicChannelService));
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		channelDeleteTest(basicChannelService, setupPUblicChannel(basicChannelService));
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ✅ END CHANNEL TEST           ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		// 🧑‍💻🧑‍💻🧑‍💻 User Test Start 🧑‍💻🧑‍💻🧑‍💻
		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃       🙋 USER TEST            ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

		BinaryContent binaryContent1 = setupBinaryContent(binaryContentRepository);

		userCreateTest(basicUserService, binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		userReadTest(basicUserService, setupUser(basicUserService, binaryContent1));
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		userUpdateTest(basicUserService, setupUser(basicUserService, binaryContent1), binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);
		userDeleteTest(basicUserService, setupUser(basicUserService, binaryContent1), userStatusRepository,
		  binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ✅ END USER TEST              ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		// 💌💌💌 Message Test Start 💌💌💌
		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃     💌 MESSAGE TEST           ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

		// setup-1
		BinaryContent binaryContent2 = setupBinaryContent(binaryContentRepository);
		Channel fileChannelForMessageforBasic = setupPUblicChannel(basicChannelService);
		User fileUserForMessageforBasic = setupUser(basicUserService, binaryContent2);
		Message fileMessageforBasic;
		// Test
		messageCreateTest(basicMessageService, fileChannelForMessageforBasic, fileUserForMessageforBasic,
		  binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		// setup-2
		binaryContent2 = setupBinaryContent(binaryContentRepository);
		fileChannelForMessageforBasic = setupPUblicChannel(basicChannelService);
		fileUserForMessageforBasic = setupUser(basicUserService, binaryContent2);
		// Test
		messageReadTest(basicMessageService, binaryContentRepository,
		  fileChannelForMessageforBasic, fileUserForMessageforBasic);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		// setup-3
		binaryContent2 = setupBinaryContent(binaryContentRepository);
		fileChannelForMessageforBasic = setupPUblicChannel(basicChannelService);
		fileUserForMessageforBasic = setupUser(basicUserService, binaryContent2);
		fileMessageforBasic =
		  setupMessage(basicMessageService, fileChannelForMessageforBasic, fileUserForMessageforBasic);
		// Test
		messageUpdateTest(basicMessageService, fileMessageforBasic);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		fileChannelForMessageforBasic = setupPUblicChannel(basicChannelService);
		fileUserForMessageforBasic = setupUser(basicUserService, null);
		messageDeleteTest(basicMessageService, binaryContentRepository,
		  fileChannelForMessageforBasic, fileUserForMessageforBasic);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ✅ END MESSAGE TEST           ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃  🙋 USER LOGIN TEST           ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);
		User fileUserForLogin = setupUser(basicUserService, binaryContent2);
		authLoginTest(authService);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);
		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ✅ END USER LOGIN TEST        ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ 🔍ReadStatus Service TEST     ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);
		createReadStatusTest(readStatusService, basicUserService, binaryContentRepository,
		  basicChannelService, readStatusRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		readReadStatusTest(basicUserService, binaryContentRepository, basicChannelService, readStatusService
		);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		updateReadStatusTest(basicUserService, binaryContentRepository, basicChannelService,
		  readStatusService, readStatusRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		deleteReadStatusTest(basicUserService, binaryContentRepository, basicChannelService,
		  readStatusService, readStatusRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ✅ END ReadStatus TEST        ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃  🙋UserStatus Service TEST    ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);
		createUserStatusTest(userStatusService, basicUserService, binaryContentRepository, userStatusRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		readUserStatusTest(userStatusService, basicUserService, binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		updateUserStatusTest(userStatusService, basicUserService, binaryContentRepository, userStatusRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		deleteUserStatusTest(userStatusService, basicUserService, binaryContentRepository, userStatusRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ✅ END UserStatus TEST        ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ?? BinaryContent Service TEST ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);
		createBinaryContentTest(context.getBean(BinaryContentService.class), binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository,
		  binaryContentRepository, readStatusRepository);

		readBinaryContentTest(context.getBean(BinaryContentService.class), binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository
		  , binaryContentRepository, readStatusRepository);

		deleteBinaryContentTest(context.getBean(BinaryContentService.class), binaryContentRepository);
		clearAll(basicChannelService, basicUserService, basicMessageService, userStatusRepository
		  , binaryContentRepository, readStatusRepository);

		System.out.println("""
		  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
		  ┃ ✅ BinaryContent Service TEST ┃
		  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
		  """);

	}
}
