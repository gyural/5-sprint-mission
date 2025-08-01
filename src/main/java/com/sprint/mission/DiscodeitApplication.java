package com.sprint.mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;

@SpringBootApplication
public class DiscodeitApplication {
	static User setupUser(UserService userService) {
		User user = userService.create("woody", "woody@codeit.com", "woody1234");
		return user;
	}

	static Channel setupChannel(ChannelService channelService) {
		Channel channel = channelService.create(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
		return channel;
	}

	static Message setupMessage(MessageService messageService, Channel channel, User author) {
		Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());
		return message;
	}

	static void channelCreateTest(ChannelService channelService) {
		System.out.print("ChannelCreateTest.......................");
		Channel channel = channelService.create(ChannelType.PUBLIC, "공지", "공지 채널입니다.");

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

		channelService.update(channel.getId(), channel.getChannelType(), newName, newDescription);

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

	static void userCreateTest(UserService userService) {
		System.out.print("UserCreateTest.......................");
		User user = userService.create("newUser", "newUser@codeit.com", "newUser1234");

		boolean isCreated = userService.read(user.getId()) != null;

		System.out.println(isCreated ?
		  "사용자 생성 테스트 통과 ✅" :
		  "사용자 생성 테스트 실패 ❌");
	}

	static void userReadTest(UserService userService, User user) {
		System.out.print("UserReadTest.......................");
		User readUser = userService.read(user.getId());

		boolean isValid = readUser.getId().equals(user.getId()) &&
		  readUser.getUsername().equals(user.getUsername()) &&
		  readUser.getEmail().equals(user.getEmail());

		System.out.println(isValid ?
		  "사용자 조회 테스트 통과 ✅" :
		  "사용자 조회 테스트 실패 ❌");
	}

	static void userUpdateTest(UserService userService, User user) {
		System.out.print("UserUpdateTest.......................");

		String newUsername = "updatedUser";
		String newEmail = "updateEmail@codeit.com";
		String newPassword = "updatedPassword1234";

		userService.update(user.getId(), newUsername, newEmail, newPassword);

		User userToValidate = userService.read(user.getId());
		boolean isUpdated = userToValidate.getUsername().equals(newUsername) &&
		  userToValidate.getEmail().equals(newEmail);

		System.out.println(isUpdated ?
		  "사용자 업데이트 테스트 통과 ✅" :
		  "사용자 업데이트 테스트 실패 ❌");

	}

	static void userDeleteTest(UserService userService, User user) {
		System.out.print("UserDeleteTest.......................");

		userService.delete(user.getId());

		String log = userService.isEmpty(user.getId()) ?
		  "사용자 삭제 테스트 통과 ✅" :
		  "사용자 삭제 테스트 실패 ❌";
		System.out.println(log);
	}

	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
		System.out.print("MessageCreateTest.......................");
		Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());

		Message storedMessage = messageService.read(message.getId());

		boolean isCreated = messageService.read(message.getId()) != null &&
		  storedMessage.getContent().equals("안녕하세요.") &&
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
		messageService.update(message.getId(), newContent);

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
		// setup-2 Basic 서비스 초기화
		UserService BasicUserService = new BasicUserService(fileUserRepository);
		MessageService BasicMessageService = new BasicMessageService(fileMessageRepository, BasicUserService,
		  fileChannelRepository);
		ChannelService BasicChannelService = new BasicChannelService(fileChannelRepository, fileMessageRepository);

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

		User fileUserforBasic = setupUser(BasicUserService);
		userCreateTest(BasicUserService);
		userReadTest(BasicUserService, fileUserforBasic);
		userUpdateTest(BasicUserService, fileUserforBasic);
		userDeleteTest(BasicUserService, fileUserforBasic);

		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃ ✅ END USER TEST              ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		clearAll(BasicChannelService, BasicUserService, BasicMessageService);

		// 💌💌💌 Message Test Start 💌💌💌
		System.out.println("\n" +
		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
		  "┃     💌 MESSAGE TEST           ┃\n" +
		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

		Channel fileChannelForMessageforBasic = setupChannel(BasicChannelService);
		User fileUserForMessageforBasic = setupUser(BasicUserService);
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
