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
	private UUID profileId;

	/**
	 * Constructs a new User with the specified username, email, password, and profile ID.
	 *
	 * The user is assigned a unique identifier and the creation timestamp is set to the current instant.
	 *
	 * @param username   the user's username
	 * @param email      the user's email address
	 * @param password   the user's password
	 * @param profileId  the UUID referencing the associated profile
	 */
	public User(String username, String email, String password, UUID profileId) {
		this.id = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.updatedAt = null;
		this.username = username;
		this.email = email;
		this.password = password;
		this.profileId = profileId;
	}

	/**
	 * Updates the user's username and sets the updatedAt timestamp to the current instant.
	 *
	 * @param username the new username to assign to the user
	 */
	public void setUsername(String username) {
		this.username = username;
		this.updatedAt = Instant.now();
	}

	/**
	 * Updates the user's email address and sets the updated timestamp to the current instant.
	 *
	 * @param email the new email address for the user
	 */
	public void setEmail(String email) {
		this.email = email;
		this.updatedAt = Instant.now();
	}

	/**
	 * Updates the user's password and sets the updated timestamp to the current instant.
	 *
	 * @param password the new password for the user
	 */
	public void setPassword(String password) {
		this.password = password;
		this.updatedAt = Instant.now();
	}

	/**
	 * Updates the user's profile ID and sets the updated timestamp to the current instant.
	 *
	 * @param profileId the new profile ID to associate with the user
	 */
	public void setProfileId(UUID profileId) {
		this.profileId = profileId;
		this.updatedAt = Instant.now();
	}

	/**
	 * Determines whether this user is equal to another object based on the unique identifier.
	 *
	 * @param o the object to compare with this user
	 * @return true if the specified object is a User with the same id; false otherwise
	 */
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


