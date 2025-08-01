package com.sprint.mission.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ContentType {
	IMAGE("IMAGE"),
	VIDEO("VIDEO"),
	AUDIO("AUDIO"),
	DOCUMENT("DOCUMENT"),
	OTHER("OTHER");

	private final String type;
}
