package com.sprint.mission.discodeit.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ReadStatus implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private final UUID id;
	private final Instant createdAt;
	private Instant updatedAt;

	// Foreign key
	private final UUID userId;
	private final UUID channelId;

	public ReadStatus(@NonNull UUID userId, @NonNull UUID channelId) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.userId = userId;
		this.channelId = channelId;
	}

	public void setUpdatedAt() {
		this.updatedAt = Instant.now();
	}

}
