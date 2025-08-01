package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Common implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private UUID id;
	private Long createdAt;
	private Long updatedAt;

	public Common() {
		this.id = UUID.randomUUID();
		this.createdAt = System.currentTimeMillis();
		this.updatedAt = null;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Common{" +
		  "id=" + id +
		  ", createdAt=" + createdAt +
		  ", updatedAt=" + updatedAt +
		  '}';
	}
}
