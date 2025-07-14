package com.sprint.mission.test;

import com.sprint.mission.test.service.userService.ChannelServiceTest;
import com.sprint.mission.test.service.userService.UserServiceTest;

public class JavaApplication {

	public static void main(String[] args) {

		// 1. UserService 테스트 실행
		UserServiceTest userServiceTest = new UserServiceTest();
		userServiceTest.testCreateUser();
		userServiceTest.testReadUser();
		userServiceTest.testUpdateUser();
		userServiceTest.testDeleteUser();

		// 2. ChannelService 테스트 실행
		ChannelServiceTest channelServiceTest = new ChannelServiceTest();
		// channelServiceTest.testCreateChannel();
		channelServiceTest.testReadChannel();
		// channelServiceTest.testUpdateChannel();
		// channelServiceTest.testDeleteChannel();
	}
}
