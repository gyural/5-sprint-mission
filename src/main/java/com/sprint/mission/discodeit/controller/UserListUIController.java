package com.sprint.mission.discodeit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserListUIController {
	@RequestMapping(value = "public", method = GET)
	public String getUserListView() {
		return "user-list";
	}
}
