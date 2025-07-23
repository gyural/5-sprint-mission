import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jsf.JCFChannelService;
import com.sprint.mission.discodeit.service.jsf.JCFMessageService;
import com.sprint.mission.discodeit.service.jsf.JCFUserService;

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
		// 레포지토리 초기화
		JCFUserRepository jcfUserRepository = new JCFUserRepository();
		JCFChannelRepository jcfChannelRepository = new JCFChannelRepository();
		JCFMessageRepository jcfMessageRepository = new JCFMessageRepository();

		// 서비스 초기화
		// TODO Basic*Service 구현체를 초기화하세요.
		JCFUserService jcfUserService = new JCFUserService(jcfUserRepository);
		JCFMessageService jcfMessageService = new JCFMessageService(jcfMessageRepository, jcfChannelRepository,
		  jcfUserService, null);
		JCFChannelService jcfChannelService = new JCFChannelService(jcfMessageService, jcfChannelRepository);

		// 셋업
		User user = setupUser(jcfUserService);
		System.out.println(user);
		Channel channel = setupChannel(jcfChannelService);
		System.out.println(channel);
		// // 테스트
		// messageCreateTest(messageService, channel, user);
	}
}
