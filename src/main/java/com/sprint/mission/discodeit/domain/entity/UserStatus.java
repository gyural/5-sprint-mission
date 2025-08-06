package com.sprint.mission.discodeit.domain.entity;

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
	private final UUID userId; /**
	 * Constructs a new UserStatus instance with a unique identifier, the current creation timestamp, and the specified user ID.
	 *
	 * @param userId the unique identifier of the user; must not be null
	 */

	public UserStatus(
	  @NonNull UUID userId
	) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.userId = userId;
	}

	/**
	 * Determines whether the user is currently online based on the last update timestamp.
	 *
	 * @return {@code true} if the user's status was updated within the last 5 minutes; {@code false} otherwise.
	 */
	public boolean isOnline() {
		return updatedAt != null &&
		  Instant.now().minusSeconds(5 * 60).isBefore(updatedAt);
	}

	/**
	 * Updates the user's status timestamp to the current instant.
	 */
	public void setUpdatedAt() {
		this.updatedAt = Instant.now();
	}
}
