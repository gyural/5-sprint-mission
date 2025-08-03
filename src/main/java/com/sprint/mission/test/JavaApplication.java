package com.sprint.mission.test;

import com.sprint.mission.test.service.userService.userServiceTest;

public class JavaApplication {

	public static void main(String[] args) {

		// 1. UserService 테스트 실행
		userServiceTest userServiceTest = new userServiceTest();
		userServiceTest.testCreateUser();
		userServiceTest.testReadUser();
		userServiceTest.testUpdateUser();
		userServiceTest.testDeleteUser();
	}
}
