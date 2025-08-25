package dev.donaldsonblack.cura.model.users;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Partial update for User. Only non-null fields are applied.
 */
public class UserPatchRequest {

	private Integer id; // optional (path usually carries id)
	private UUID cognitoSub;
	private String name;
	private String email;
	private String role;
	private JsonNode departments;

	public UserPatchRequest() {
	}

	private UserPatchRequest(Builder b) {
		this.id = b.id;
		this.cognitoSub = b.cognitoSub;
		this.name = b.name;
		this.email = b.email;
		this.role = b.role;
		this.departments = b.departments;
	}

	// getters
	public Integer getId() {
		return id;
	}

	public UUID getCognitoSub() {
		return cognitoSub;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public JsonNode getDepartments() {
		return departments;
	}

	// builder
	public static class Builder {
		private Integer id;
		private UUID cognitoSub;
		private String name;
		private String email;
		private String role;
		private JsonNode departments;

		public Builder id(Integer id) {
			this.id = id;
			return this;
		}

		public Builder cognitoSub(UUID cognitoSub) {
			this.cognitoSub = cognitoSub;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder role(String role) {
			this.role = role;
			return this;
		}

		public Builder departments(JsonNode departments) {
			this.departments = departments;
			return this;
		}

		public UserPatchRequest build() {
			return new UserPatchRequest(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	// apply-to
	public User applyTo(User existing) {
		if (this.cognitoSub != null)
			existing.setCognitoSub(this.cognitoSub);
		if (this.name != null)
			existing.setName(this.name);
		if (this.email != null)
			existing.setEmail(this.email);
		if (this.role != null)
			existing.setRole(this.role);
		if (this.departments != null)
			existing.setDepartments(this.departments);
		return existing;
	}
}
