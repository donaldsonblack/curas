package dev.donaldsonblack.cura.model.users;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Creates a new User. DB generates id/created; role defaults to 'user' if
 * omitted.
 */
public class UserCreateRequest {

	@NotNull
	private UUID cognitoSub;

	@NotBlank
	@Size(max = 200)
	private String name;

	@NotBlank
	@Email
	@Size(max = 320)
	private String email;

	// Optional: let DB default to 'user' if null
	@Size(max = 50)
	private String role;

	// Optional JSON array of department IDs; default [] if null
	private JsonNode departments;

	public UserCreateRequest() {
	}

	private UserCreateRequest(Builder b) {
		this.cognitoSub = b.cognitoSub;
		this.name = b.name;
		this.email = b.email;
		this.role = b.role;
		this.departments = b.departments;
	}

	// getters/setters
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public JsonNode getDepartments() {
		return departments;
	}

	public void setDepartments(JsonNode departments) {
		this.departments = departments;
	}

	// builder
	public static class Builder {
		private UUID cognitoSub;
		private String name;
		private String email;
		private String role;
		private JsonNode departments;

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

		public UserCreateRequest build() {
			return new UserCreateRequest(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	// mapper
	public User toUserEntity() {
		User u = new User();
		u.setCognitoSub(this.cognitoSub);
		u.setName(this.name);
		u.setEmail(this.email);
		u.setRole(this.role); // may be null; DB default applies
		u.setDepartments(this.departments != null ? this.departments : JsonNodeFactory.instance.arrayNode());
		return u;
	}
}
