package com.sprint.mission.discodeit.entity;

import java.util.Objects;
import java.util.UUID;

public class User {

	private final UUID id;
	private final Long createdAt;
	private Long updatedAt;
	private String username;

	public UUID getId() {
		return id;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public User(String username) {
		this.id = UUID.randomUUID();
		this.createdAt = System.currentTimeMillis();
		this.updatedAt = null;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		this.updatedAt = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "User{" +
		  "id=" + id +
		  ", createdAt=" + createdAt +
		  ", updatedAt=" + updatedAt +
		  ", username='" + username + '\'' +
		  '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}


