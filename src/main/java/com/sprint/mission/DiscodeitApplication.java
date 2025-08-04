package com.sprint.mission;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
import com.sprint.mission.discodeit.domain.dto.UserReadDTO;
import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.entity.Channel;
import com.sprint.mission.discodeit.domain.entity.ChannelType;
import com.sprint.mission.discodeit.domain.entity.Message;
import com.sprint.mission.discodeit.domain.entity.User;
import com.sprint.mission.discodeit.domain.entity.UserStatus;
import com.sprint.mission.discodeit.domain.enums.ContentType;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileBinaryContentRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.file.FileUserStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;

@SpringBootApplication
public class DiscodeitApplication {

	static BinaryContent setupBinaryContent(FileBinaryContentRepository fileBinaryContentRepository) {
		byte[] bytes;
		try {
			Path imagePath = Path.of(System.getProperty("user.dir"), "dummyImage.png");
			bytes = Files.readAllBytes(imagePath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		BinaryContent dummyBinaryContent = new BinaryContent(
		  bytes, bytes.length, ContentType.IMAGE, "dummyImage.png");
		return fileBinaryContentRepository.save(dummyBinaryContent);
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

	static Channel setupChannel(ChannelService channelService) {
		return channelService.create(
		  ChannelCreateDTO.builder().channelType(ChannelType.PUBLIC).description("공지 채널입니다.").name("공지").build());
	}

	static Message setupMessage(MessageService messageService, Channel channel, User author) {
		return messageService.create(
		  MessageCreateDTO.builder().channelId(channel.getId()).content("안녕하세요").userId(author.getId()).build());
	}

	static void channelCreateTest(ChannelService channelService) {
		System.out.print("ChannelCreateTest.......................");
		Channel channel = channelService.create(
		  ChannelCreateDTO.builder().channelType(ChannelType.PUBLIC).description("공지 채널입니다.").name("공지").build());

		boolean isCreated = !channelService.isEmpty(channel.getId());

		System.out.println(isCreated ?
		  "채널 생성 테스트 통과 ✅" :
		  "채널 생성 테스트 실패 ❌");
	}

	static void channelReadTest(ChannelService channelService, Channel channel) {
		System.out.print("channelReadTest.......................");
		Channel readChannel = channelService.read(channel.getId());

		boolean isValid = readChannel.getId().equals(channel.getId()) &&
		  readChannel.getName().equals(channel.getName()) &&
		  readChannel.getDescription().equals(channel.getDescription());

		System.out.println(isValid ?
		  "채널 조회 테스트 통과 ✅" :
		  "채널 조회 테스트 실패 ❌");
	}

	static void channelUpdateTest(ChannelService channelService, Channel channel) {
		System.out.print("channelUpdateTest.......................");

		String newName = "업데이트된 채널";
		String newDescription = "업데이트된 채널 설명";

		channelService.update(ChannelUpdateDTO.builder()
		  .id(channel.getId())
		  .channelType(channel.getChannelType())
		  .name(newName)
		  .description(newDescription)
		  .build());

		Channel channelToValidate = channelService.read(channel.getId());
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
	  FileBinaryContentRepository fileBinaryContentRepository,
	  UserStatusRepository userStatusRepository) {
		System.out.print("userCreateTest.......................");

		// Given
		BinaryContent userProfileImage = setupBinaryContent(fileBinaryContentRepository);
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
		  !fileBinaryContentRepository.isEmpty(userWithProfile.getProfileId());
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
		// 2. userStatus 잘 생성 되었는지 확인
		List<UserStatus> userStatusList = userStatusRepository.findAll();

		boolean isUserStatusCreate = userStatusList.stream()
		  .anyMatch(userStatus -> userStatus.getUserId().equals(u1.getId()))
		  && userStatusList.stream()
		  .anyMatch(userStatus -> userStatus.getUserId().equals(u2.getId()));

		if (!isUserStatusCreate) {
			System.out.println("userStatus 생성 실패 ❌");
			return;
		}
		// 3. User 일반 필드 잘 생성 되었는지 확인
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
	 * 사용자 생성 테스트
	 * - username, email 중복 검증 확인
	 */
	static void userCreateDuplicateTest(UserService userService) {

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
	  FileBinaryContentRepository fileBinaryContentRepository) {
		System.out.print("UserUpdateTest.......................");

		String newUsername = "updatedUser";
		String newEmail = "updateEmail@codeit.com";
		String newPassword = "updatedPassword1234";
		BinaryContent newProfileImage = setupBinaryContent(fileBinaryContentRepository);

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
		  fileBinaryContentRepository.find(newProfileImage.getId()).isPresent();

		System.out.println(isUpdated ?
		  "사용자 업데이트 테스트 통과 ✅" :
		  "사용자 업데이트 테스트 실패 ❌");

	}

	/**
	 * 사용자 삭제 테스트
	 * - 사용자 삭제 후, 관련된 데이터도 삭제되는지 확인
	 * @param userService
	 * @param user
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

	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
		System.out.print("MessageCreateTest.......................");
		Message message = messageService.create(
		  MessageCreateDTO.builder().channelId(channel.getId()).content("안녕하세요").userId(author.getId()).build());

		Message storedMessage = messageService.read(message.getId());

		boolean isCreated = messageService.read(message.getId()) != null &&
		  storedMessage.getContent().equals("안녕하세요") &&
		  storedMessage.getChannelId().equals(channel.getId()) &&
		  storedMessage.getAuthorId().equals(author.getId());

		System.out.println(isCreated ?
		  "메시지 생성 테스트 통과 ✅" :
		  "메시지 생성 테스트 실패 ❌");
	}

	static void messageReadTest(MessageService messageService, Message message) {
		System.out.print("MessageReadTest.......................");
		Message readMessage = messageService.read(message.getId());

		boolean isValid = readMessage.getId().equals(message.getId()) &&
		  readMessage.getContent().equals(message.getContent()) &&
		  readMessage.getChannelId().equals(message.getChannelId()) &&
		  readMessage.getAuthorId().equals(message.getAuthorId());

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

	static void messageDeleteTest(MessageService messageService, Message message) {
		System.out.print("MessageDeleteTest.......................");

		messageService.delete(message.getId());

		String log = messageService.isEmpty(message.getId()) ?
		  "메시지 삭제 테스트 통과 ✅" :
		  "메시지 삭제 테스트 실패 ❌";
		System.out.println(log);
	}

	static void clearAll(ChannelService channelService, UserService userService, MessageService messageService) {
		channelService.deleteAll();
		userService.deleteAll();
		messageService.deleteAll();
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		// setup-1 FILE 레포지토리 초기화
		FileUserRepository fileUserRepository = context.getBean(FileUserRepository.class);
		FileChannelRepository fileChannelRepository = context.getBean(FileChannelRepository.class);
		FileMessageRepository fileMessageRepository = context.getBean(FileMessageRepository.class);
		FileBinaryContentRepository fileBinaryContentRepository = context.getBean(FileBinaryContentRepository.class);
		FileUserStatusRepository userStatusRepository = context.getBean(FileUserStatusRepository.class);
		// setup-2 Basic 서비스 초기화
		UserService BasicUserService = context.getBean(BasicUserService.class);
		MessageService BasicMessageService = context.getBean(BasicMessageService.class);
		ChannelService BasicChannelService = context.getBean(BasicChannelService.class);

		BinaryContent binaryContent = setupBinaryContent(fileBinaryContentRepository);
		System.out.println(binaryContent);

		System.out.println("\n" +
		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
		  "💾 [FILE] 저장소 기반 BasicService 테스트 시작\n" +
		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

		// 💥💥💥 Channel Test Start 💥💥💥
		System.out.println("\n" +
		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃     📡 CHANNEL TEST           ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

		Channel fileChannelforBasic = setupChannel(BasicChannelService);
		channelCreateTest(BasicChannelService);
		channelReadTest(BasicChannelService, fileChannelforBasic);
		channelUpdateTest(BasicChannelService, fileChannelforBasic);
		channelDeleteTest(BasicChannelService, fileChannelforBasic);

		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃ ✅ END CHANNEL TEST           ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);

		// 🧑‍💻🧑‍💻🧑‍💻 User Test Start 🧑‍💻🧑‍💻🧑‍💻
		System.out.println("\n" +
		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃       🙋 USER TEST            ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

		BinaryContent binaryContent1 = setupBinaryContent(fileBinaryContentRepository);

		userCreateTest(BasicUserService, fileBinaryContentRepository, userStatusRepository);
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);

		userReadTest(BasicUserService, setupUser(BasicUserService, binaryContent1));
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);

		userUpdateTest(BasicUserService, setupUser(BasicUserService, binaryContent1), fileBinaryContentRepository);
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);
		userDeleteTest(BasicUserService, setupUser(BasicUserService, binaryContent1), userStatusRepository,
		  fileBinaryContentRepository);
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);

		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃ ✅ END USER TEST              ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);

		// 💌💌💌 Message Test Start 💌💌💌
		System.out.println("\n" +
		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃     💌 MESSAGE TEST           ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

		BinaryContent binaryContent2 = setupBinaryContent(fileBinaryContentRepository);
		Channel fileChannelForMessageforBasic = setupChannel(BasicChannelService);
		User fileUserForMessageforBasic = setupUser(BasicUserService, binaryContent2);
		Message fileMessageforBasic = setupMessage(BasicMessageService, fileChannelForMessageforBasic,
		  fileUserForMessageforBasic);

		messageCreateTest(BasicMessageService, fileChannelForMessageforBasic, fileUserForMessageforBasic);
		messageReadTest(BasicMessageService, fileMessageforBasic);
		messageUpdateTest(BasicMessageService, fileMessageforBasic);
		messageDeleteTest(BasicMessageService, fileMessageforBasic);

		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃ ✅ END MESSAGE TEST           ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);

	}
}
