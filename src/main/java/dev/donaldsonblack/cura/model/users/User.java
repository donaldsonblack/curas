package dev.donaldsonblack.cura.model.users;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public class User {

	private Integer id;
	private UUID cognitoSub; // maps to DB column cognito_sub
	private String name;
	private String email;
	private JsonNode departments; // JSONB array; e.g., [1,2,3]
	private String role; // default 'user' in DB
	private Instant created; // default NOW() in DB

	public User() {
	}

	public User(Integer id, UUID cognitoSub, String name, String email,
			JsonNode departments, String role, Instant created) {
		this.id = id;
		this.cognitoSub = cognitoSub;
		this.name = name;
		this.email = email;
		this.departments = departments;
		this.role = role;
		this.created = created;
	}

	// Getters & setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UUID getCognitoSub() {
		return cognitoSub;
	}

	public void setCognitoSub(UUID cognitoSub) {
		this.cognitoSub = cognitoSub;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public JsonNode getDepartments() {
		return departments;
	}

	public void setDepartments(JsonNode departments) {
		this.departments = departments;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	// Builder
	public static class Builder {
		private Integer id;
		private UUID cognitoSub;
		private String name;
		private String email;
		private JsonNode departments;
		private String role;
		private Instant created;

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

		public Builder departments(JsonNode departments) {
			this.departments = departments;
			return this;
		}

		public Builder role(String role) {
			this.role = role;
			return this;
		}

		public Builder created(Instant created) {
			this.created = created;
			return this;
		}

		public User build() {
			return new User(id, cognitoSub, name, email, departments, role, created);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
