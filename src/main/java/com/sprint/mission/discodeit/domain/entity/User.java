package com.sprint.mission.discodeit.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;

@Getter
public class User implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final Instant createdAt;
	private Instant updatedAt;
	private String username;
	private String email;
	private String password;

	// Foreign key
	private final UUID profileId;

	public User(String username, String email, String password, UUID profileId) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.updatedAt = null;
		this.username = username;
		this.email = email;
		this.password = password;
		this.profileId = profileId;
	}

	public void setUsername(String username) {
		this.username = username;
		this.updatedAt = Instant.now();
	}

	public void setEmail(String email) {
		this.email = email;
		this.updatedAt = Instant.now();
	}

	public void setPassword(String password) {
		this.password = password;
		this.updatedAt = Instant.now();
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

	@Override
	public String toString() {
		return "User{" +
		  "id=" + id +
		  ", createdAt=" + createdAt +
		  ", updatedAt=" + updatedAt +
		  ", username='" + username + '\'' +
		  ", email='" + email + '\'' +
		  ", password='" + password + '\'' +
		  '}';
	}
}


