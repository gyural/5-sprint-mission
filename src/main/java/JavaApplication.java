// import com.sprint.mission.discodeit.domain.dto.ChannelCreateDTO;
// import com.sprint.mission.discodeit.domain.dto.ChannelUpdateDTO;
// import com.sprint.mission.discodeit.domain.dto.MessageCreateDTO;
// import com.sprint.mission.discodeit.domain.dto.MessageUpdateDTO;
// import com.sprint.mission.discodeit.domain.dto.UserCreateDTO;
// import com.sprint.mission.discodeit.domain.dto.UserUpdateDTO;
// import com.sprint.mission.discodeit.domain.entity.Channel;
// import com.sprint.mission.discodeit.domain.entity.ChannelType;
// import com.sprint.mission.discodeit.domain.entity.Message;
// import com.sprint.mission.discodeit.domain.entity.User;
// import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
// import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
// import com.sprint.mission.discodeit.repository.file.FileUserRepository;
// import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
// import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
// import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
// import com.sprint.mission.discodeit.service.ChannelService;
// import com.sprint.mission.discodeit.service.MessageService;
// import com.sprint.mission.discodeit.service.UserService;
// import com.sprint.mission.discodeit.service.basic.BasicChannelService;
// import com.sprint.mission.discodeit.service.basic.BasicMessageService;
// import com.sprint.mission.discodeit.service.basic.BasicUserService;
// import com.sprint.mission.discodeit.service.file.FileChannelService;
// import com.sprint.mission.discodeit.service.file.FileMessageService;
// import com.sprint.mission.discodeit.service.file.FileUserService;
//
// public class JavaApplication {
// 	static User setupUser(UserService userService) {
//
// 		return userService.create(
// 		  UserCreateDTO.builder().username("woody").email("woody@codeit.com").password("woody1234").build());
// 	}
//
// 	static Channel setupChannel(ChannelService channelService) {
// 		return channelService.create(
// 		  ChannelCreateDTO.builder().channelType(ChannelType.PUBLIC).description("공지 채널입니다.").name("공지").build());
// 	}
//
// 	static Message setupMessage(MessageService messageService, Channel channel, User author) {
// 		return messageService.create(
// 		  MessageCreateDTO.builder().channelId(channel.getId()).content("안녕하세요").userId(author.getId()).build());
// 	}
//
// 	static void channelCreateTest(ChannelService channelService) {
// 		System.out.print("ChannelCreateTest.......................");
// 		Channel channel = channelService.create(
// 		  ChannelCreateDTO.builder().channelType(ChannelType.PUBLIC).description("공지 채널입니다.").name("공지").build());
//
// 		boolean isCreated = !channelService.isEmpty(channel.getId());
//
// 		System.out.println(isCreated ?
// 		  "채널 생성 테스트 통과 ✅" :
// 		  "채널 생성 테스트 실패 ❌");
// 	}
//
// 	static void channelReadTest(ChannelService channelService, Channel channel) {
// 		System.out.print("channelReadTest.......................");
// 		Channel readChannel = channelService.read(channel.getId());
//
// 		boolean isValid = readChannel.getId().equals(channel.getId()) &&
// 		  readChannel.getName().equals(channel.getName()) &&
// 		  readChannel.getDescription().equals(channel.getDescription());
//
// 		System.out.println(isValid ?
// 		  "채널 조회 테스트 통과 ✅" :
// 		  "채널 조회 테스트 실패 ❌");
// 	}
//
// 	static void channelUpdateTest(ChannelService channelService, Channel channel) {
// 		System.out.print("channelUpdateTest.......................");
//
// 		String newName = "업데이트된 채널";
// 		String newDescription = "업데이트된 채널 설명";
// 		int beforeUpdateCount = channelService.readAll().size();
//
// 		channelService.update(ChannelUpdateDTO.builder()
// 		  .id(channel.getId())
// 		  .channelType(channel.getChannelType())
// 		  .name(newName)
// 		  .description(newDescription)
// 		  .build());
//
// 		Channel channelToValidate = channelService.read(channel.getId());
// 		boolean isUpdated = channelToValidate.getName().equals(newName) &&
// 		  channelToValidate.getDescription().equals(newDescription) &&
// 		  channelService.readAll().size() == beforeUpdateCount;
//
// 		System.out.println(isUpdated ?
// 		  "채널 업데이트 테스트 통과 ✅" :
// 		  "채널 업데이트 테스트 실패 ❌");
// 	}
//
// 	static void channelDeleteTest(ChannelService channelService, Channel channel) {
// 		System.out.print("channelDeleteTest.......................");
//
// 		channelService.delete(channel.getId());
// 		String log = channelService.isEmpty(channel.getId()) ?
// 		  "채널이 삭제 테스트 통과 ✅" :
// 		  "채널이 삭제 테스트 실패 ❌";
// 		System.out.println(log);
// 	}
//
// 	static void userCreateTest(UserService userService) {
// 		System.out.print("UserCreateTest.......................");
// 		User user = userService.create(
// 		  UserCreateDTO.builder().username("woody").email("woody@codeit.com").password("woody1234").build());
//
// 		boolean isCreated = userService.read(user.getId()) != null;
//
// 		System.out.println(isCreated ?
// 		  "사용자 생성 테스트 통과 ✅" :
// 		  "사용자 생성 테스트 실패 ❌");
// 	}
//
// 	static void userReadTest(UserService userService, User user) {
// 		System.out.print("UserReadTest.......................");
// 		User readUser = userService.read(user.getId());
//
// 		boolean isValid = readUser.getId().equals(user.getId()) &&
// 		  readUser.getUsername().equals(user.getUsername()) &&
// 		  readUser.getEmail().equals(user.getEmail());
//
// 		System.out.println(isValid ?
// 		  "사용자 조회 테스트 통과 ✅" :
// 		  "사용자 조회 테스트 실패 ❌");
// 	}
//
// 	static void userUpdateTest(UserService userService, User user) {
// 		System.out.print("UserUpdateTest.......................");
//
// 		String newUsername = "updatedUser";
// 		String newEmail = "updateEmail@codeit.com";
// 		String newPassword = "updatedPassword1234";
// 		int beforeUpdateCount = userService.readAll().size();
//
// 		userService.update(UserUpdateDTO.builder()
// 		  .userId(user.getId())
// 		  .newUsername(newUsername)
// 		  .newEmail(newEmail)
// 		  .newPassword(newPassword)
// 		  .build());
//
// 		User userToValidate = userService.read(user.getId());
// 		boolean isUpdated = userToValidate.getUsername().equals(newUsername) &&
// 		  userToValidate.getEmail().equals(newEmail) &&
// 		  beforeUpdateCount == userService.readAll().size();
//
// 		System.out.println(isUpdated ?
// 		  "사용자 업데이트 테스트 통과 ✅" :
// 		  "사용자 업데이트 테스트 실패 ❌");
//
// 	}
//
// 	static void userDeleteTest(UserService userService, User user) {
// 		System.out.print("UserDeleteTest.......................");
//
// 		userService.delete(user.getId());
//
// 		String log = userService.isEmpty(user.getId()) ?
// 		  "사용자 삭제 테스트 통과 ✅" :
// 		  "사용자 삭제 테스트 실패 ❌";
// 		System.out.println(log);
// 	}
//
// 	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
// 		System.out.print("MessageCreateTest.......................");
// 		Message message = messageService.create(
// 		  MessageCreateDTO.builder().channelId(channel.getId()).content("안녕하세요").userId(author.getId()).build());
//
// 		Message storedMessage = messageService.read(message.getId());
//
// 		boolean isCreated = messageService.read(message.getId()) != null &&
// 		  storedMessage.getContent().equals("안녕하세요.") &&
// 		  storedMessage.getChannelId().equals(channel.getId()) &&
// 		  storedMessage.getAuthorId().equals(author.getId());
//
// 		System.out.println(isCreated ?
// 		  "메시지 생성 테스트 통과 ✅" :
// 		  "메시지 생성 테스트 실패 ❌");
// 	}
//
// 	static void messageReadTest(MessageService messageService, Message message) {
// 		System.out.print("MessageReadTest.......................");
// 		Message readMessage = messageService.read(message.getId());
//
// 		boolean isValid = readMessage.getId().equals(message.getId()) &&
// 		  readMessage.getContent().equals(message.getContent()) &&
// 		  readMessage.getChannelId().equals(message.getChannelId()) &&
// 		  readMessage.getAuthorId().equals(message.getAuthorId());
//
// 		System.out.println(isValid ?
// 		  "메시지 조회 테스트 통과 ✅" :
// 		  "메시지 조회 테스트 실패 ❌");
// 	}
//
// 	static void messageUpdateTest(MessageService messageService, Message message) {
// 		System.out.print("MessageUpdateTest.......................");
// 		String newContent = "업데이트된 메시지 내용";
// 		messageService.update(MessageUpdateDTO.builder().id(message.getId()).newContent(newContent).build());
// 		int beforeUpdateCount = messageService.readAll().size();
//
// 		Message updatedMessage = messageService.read(message.getId());
// 		boolean isUpdated = updatedMessage.getContent().equals(newContent) &&
// 		  messageService.readAll().size() == beforeUpdateCount;
//
// 		System.out.println(isUpdated ?
// 		  "메시지 업데이트 테스트 통과 ✅" :
// 		  "메시지 업데이트 테스트 실패 ❌");
// 	}
//
// 	static void messageDeleteTest(MessageService messageService, Message message) {
// 		System.out.print("MessageDeleteTest.......................");
//
// 		messageService.delete(message.getId());
//
// 		String log = messageService.isEmpty(message.getId()) ?
// 		  "메시지 삭제 테스트 통과 ✅" :
// 		  "메시지 삭제 테스트 실패 ❌";
// 		System.out.println(log);
// 	}
//
// 	static void clearAll(ChannelService channelService, UserService userService, MessageService messageService) {
// 		channelService.deleteAll();
// 		userService.deleteAll();
// 		messageService.deleteAll();
// 	}
//
// 	public static void main(String[] args) {
//
// 		// 🔧 SETUP
// 		// setup-1 JCF 레포지토리 초기화
// 		JCFUserRepository jcfUserRepository = new JCFUserRepository();
// 		JCFChannelRepository jcfChannelRepository = new JCFChannelRepository();
// 		JCFMessageRepository jcfMessageRepository = new JCFMessageRepository();
//
// 		// setup-2 FILE 레포지토리 초기화
// 		FileUserRepository fileUserRepository = new FileUserRepository();
// 		FileChannelRepository fileChannelRepository = new FileChannelRepository();
// 		FileMessageRepository fileMessageRepository = new FileMessageRepository();
//
// 		// ============================
// 		// 💾 FILE 저장소 테스트 시작
// 		// ============================
//
// 		System.out.println("\n" +
// 		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
// 		  "💾 [FILE] 저장소 기반 테스트 시작\n" +
// 		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
//
// 		UserService fileUserService = new FileUserService(fileUserRepository);
// 		MessageService fileMessageService = new FileMessageService(fileMessageRepository, fileUserService,
// 		  fileChannelRepository);
// 		ChannelService fileChannelService = new FileChannelService(fileChannelRepository, fileMessageRepository);
//
// 		// 💥💥💥 Channel Test Start 💥💥💥
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃    💾 [FILE] 📡 CHANNEL TEST  ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		Channel fileChannel = setupChannel(fileChannelService);
// 		channelCreateTest(fileChannelService);
// 		channelReadTest(fileChannelService, fileChannel);
// 		channelUpdateTest(fileChannelService, fileChannel);
// 		channelDeleteTest(fileChannelService, fileChannel);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [FILE] CHANNEL TEST    ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(fileChannelService, fileUserService, fileMessageService);
//
// 		// 🧑‍💻🧑‍💻🧑‍💻 User Test Start 🧑‍💻🧑‍💻🧑‍💻
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃      💾 [FILE] 🙋 USER TEST   ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		User fileUser = setupUser(fileUserService);
// 		userCreateTest(fileUserService);
// 		userReadTest(fileUserService, fileUser);
// 		userUpdateTest(fileUserService, fileUser);
// 		userDeleteTest(fileUserService, fileUser);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [FILE] USER TEST       ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(fileChannelService, fileUserService, fileMessageService);
//
// 		// 💌💌💌 Message Test Start 💌💌💌
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃    💾 [FILE] 💌 MESSAGE TEST  ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		Channel fileChannelForMessage = setupChannel(fileChannelService);
// 		User fileUserForMessage = setupUser(fileUserService);
// 		Message fileMessage = setupMessage(fileMessageService, fileChannelForMessage, fileUserForMessage);
//
// 		messageCreateTest(fileMessageService, fileChannelForMessage, fileUserForMessage);
// 		messageReadTest(fileMessageService, fileMessage);
// 		messageUpdateTest(fileMessageService, fileMessage);
// 		messageDeleteTest(fileMessageService, fileMessage);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [FILE] MESSAGE TEST    ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(fileChannelService, fileUserService, fileMessageService);
//
// 		// ============================
// 		// 🗂️ JCF 저장소 테스트 시작
// 		// ============================
//
// 		System.out.println("\n" +
// 		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
// 		  "🗂️ [JCF] 저장소 기반 Basic Service 테스트 시작\n" +
// 		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
//
// 		UserService jcfUserService = new BasicUserService(jcfUserRepository);
// 		MessageService jcfMessageService = new BasicMessageService(jcfMessageRepository, jcfUserService,
// 		  jcfChannelRepository
// 		);
// 		ChannelService jcfChannelService = new BasicChannelService(jcfChannelRepository, jcfMessageRepository);
//
// 		// 💥💥💥 Channel Test Start 💥💥💥
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃     🗂️ [JCF] 📡 CHANNEL TEST  ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		Channel jcfChannel = setupChannel(jcfChannelService);
// 		channelCreateTest(jcfChannelService);
// 		channelReadTest(jcfChannelService, jcfChannel);
// 		channelUpdateTest(jcfChannelService, jcfChannel);
// 		channelDeleteTest(jcfChannelService, jcfChannel);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [JCF] CHANNEL TEST     ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(jcfChannelService, jcfUserService, jcfMessageService);
//
// 		// 🧑‍💻🧑‍💻🧑‍💻 User Test Start 🧑‍💻🧑‍💻🧑‍💻
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃       🗂️ [JCF] 🙋 USER TEST   ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		User jcfUser = setupUser(jcfUserService);
// 		userCreateTest(jcfUserService);
// 		userReadTest(jcfUserService, jcfUser);
// 		userUpdateTest(jcfUserService, jcfUser);
// 		userDeleteTest(jcfUserService, jcfUser);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [JCF] USER TEST        ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(jcfChannelService, jcfUserService, jcfMessageService);
//
// 		// 💌💌💌 Message Test Start 💌💌💌
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃     🗂️ [JCF] 💌 MESSAGE TEST  ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		Channel jcfChannelForMessage = setupChannel(jcfChannelService);
// 		User jcfUserForMessage = setupUser(jcfUserService);
// 		Message jcfMessage = setupMessage(jcfMessageService, jcfChannelForMessage, jcfUserForMessage);
//
// 		messageCreateTest(jcfMessageService, jcfChannelForMessage, jcfUserForMessage);
// 		messageReadTest(jcfMessageService, jcfMessage);
// 		messageUpdateTest(jcfMessageService, jcfMessage);
// 		messageDeleteTest(jcfMessageService, jcfMessage);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [JCF] MESSAGE TEST     ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(jcfChannelService, jcfUserService, jcfMessageService);
//
// 		// ============================
// 		// 💾 FILE 저장소 테스트 시작
// 		// ============================
//
// 		System.out.println("\n" +
// 		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
// 		  "💾 [FILE] 저장소 기반 BasicService 테스트 시작\n" +
// 		  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
//
// 		UserService BasicfileUserService = new BasicUserService(fileUserRepository);
// 		MessageService BasicfileMessageService = new BasicMessageService(fileMessageRepository, fileUserService,
// 		  fileChannelRepository);
// 		ChannelService BasicfileChannelService = new BasicChannelService(fileChannelRepository, fileMessageRepository);
//
// 		// 💥💥💥 Channel Test Start 💥💥💥
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃    💾 [FILE] 📡 CHANNEL TEST  ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		Channel fileChannelforBasic = setupChannel(fileChannelService);
// 		channelCreateTest(fileChannelService);
// 		channelReadTest(fileChannelService, fileChannelforBasic);
// 		channelUpdateTest(fileChannelService, fileChannelforBasic);
// 		channelDeleteTest(fileChannelService, fileChannelforBasic);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [FILE] CHANNEL TEST    ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(fileChannelService, fileUserService, fileMessageService);
//
// 		// 🧑‍💻🧑‍💻🧑‍💻 User Test Start 🧑‍💻🧑‍💻🧑‍💻
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃      💾 [FILE] 🙋 USER TEST   ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		User fileUserforBasic = setupUser(fileUserService);
// 		userCreateTest(fileUserService);
// 		userReadTest(fileUserService, fileUserforBasic);
// 		userUpdateTest(fileUserService, fileUserforBasic);
// 		userDeleteTest(fileUserService, fileUserforBasic);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [FILE] USER TEST       ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(fileChannelService, fileUserService, fileMessageService);
//
// 		// 💌💌💌 Message Test Start 💌💌💌
// 		System.out.println("\n" +
// 		  "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃    💾 [FILE] 💌 MESSAGE TEST  ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//
// 		Channel fileChannelForMessageforBasic = setupChannel(fileChannelService);
// 		User fileUserForMessageforBasic = setupUser(fileUserService);
// 		Message fileMessageforBasic = setupMessage(fileMessageService, fileChannelForMessageforBasic,
// 		  fileUserForMessageforBasic);
//
// 		messageCreateTest(fileMessageService, fileChannelForMessageforBasic, fileUserForMessageforBasic);
// 		messageReadTest(fileMessageService, fileMessageforBasic);
// 		messageUpdateTest(fileMessageService, fileMessageforBasic);
// 		messageDeleteTest(fileMessageService, fileMessageforBasic);
//
// 		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
// 		  "┃ ✅ END [FILE] MESSAGE TEST    ┃\n" +
// 		  "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
// 		clearAll(fileChannelService, fileUserService, fileMessageService);
//
// 	}
// }
