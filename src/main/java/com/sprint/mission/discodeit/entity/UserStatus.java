package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserStatus implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final Instant createdAt;
	private Instant updatedAt;

	// Foreign key
	private final UUID userId; // 유저 ID

	public UserStatus(
	  @NonNull UUID userId
	) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.userId = userId;
	}

	public boolean isOnline() {
		return updatedAt != null &&
		  Instant.now().minusSeconds(5 * 60).isBefore(updatedAt);
	}

	public void setUpdatedAt() {
		this.updatedAt = Instant.now();
	}
}
