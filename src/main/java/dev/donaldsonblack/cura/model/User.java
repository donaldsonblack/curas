package dev.donaldsonblack.cura.model;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public class User {
	private Integer id;
	private UUID cognitoSub;
	private String name;
	private String email;
	private JsonNode departments; // Consider using List<String> with JSONB mapping if needed
	private String role;
	private Instant createdAt;
	private Instant updatedAt;

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

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
