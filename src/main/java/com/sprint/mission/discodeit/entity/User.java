package com.sprint.mission.discodeit.entity;

public class User extends Common {
	private String username;

	public User(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return super.toString()+"\n" +
		  "User{" +
		  "username='" + username + '\'' +
		  '}';
	}
}


