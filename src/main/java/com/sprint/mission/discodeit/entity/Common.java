package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Common implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;
	private Long createdAt;
	private Long updatedAt;

	public Common() {
		this.id = UUID.randomUUID();
		this.createdAt = System.currentTimeMillis();
		this.updatedAt = this.createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
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
