package com.sprint.mission.test.service.userService;

import com.sprint.mission.discodeit.service.jsf.JCFMessageService;
import com.sprint.mission.discodeit.service.jsf.JCFUserService;

public class MessageServiceTest {

	private final JCFUserService userService = new JCFUserService();
	private final JCFMessageService messageService = new JCFMessageService(userService);

	public void testCreateMessage() {
	}

}
