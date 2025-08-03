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
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;

public class JavaApplication {
	static User setupUser(UserService userService) {
		User user = userService.create("woody", "woody@codeit.com", "woody1234");
		return user;
	}

	static Channel setupChannel(ChannelService channelService) {
		Channel channel = channelService.create(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
		return channel;
	}

	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
		Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());
		System.out.println("메시지 생성: " + message.getId());
	}

	public static void main(String[] args) {
		//JCF 레포지토리 초기화
		// JCFUserRepository jcfUserRepository = new JCFUserRepository();
		// JCFChannelRepository jcfChannelRepository = new JCFChannelRepository();
		// JCFMessageRepository jcfMessageRepository = new JCFMessageRepository();
		// 서비스 JCF Service로 초기화
		// UserService userService = new JCFUserService(jcfUserRepository);
		// MessageService messageService = new JCFMessageService(jcfMessageRepository, jcfChannelRepository, userService,
		//   null);
		// ChannelService channelService = new JCFChannelService(messageService, jcfChannelRepository);

		// File 레포지토리 초기화
		FileUserRepository fileUserRepository = new FileUserRepository();
		FileChannelRepository fileChannelRepository = new FileChannelRepository();
		FileMessageRepository fileMessageRepository = new FileMessageRepository();

		// 서비스 초기화 File Service로 초기화
		FileUserService userService = new FileUserService(fileUserRepository);
		MessageService messageService = new FileMessageService(fileMessageRepository, userService,
		  fileChannelRepository);
		ChannelService channelService = new FileChannelService(fileChannelRepository, fileMessageRepository);

		// TODO Basic*Service 구현체를 초기화하세요.

		// 셋업
		User user = setupUser(userService);
		System.out.println(user);
		Channel channel = setupChannel(channelService);
		System.out.println(channel);
		Message message = messageService.create("안녕하세요.", channel.getId(), user.getId());
		System.out.println(message);
		// // 테스트
		// messageCreateTest(messageService, channel, user);
	}
}
