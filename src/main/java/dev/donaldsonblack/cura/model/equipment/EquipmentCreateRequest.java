package dev.donaldsonblack.cura.model.equipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EquipmentCreateRequest {

	@NotNull
	private Integer departmentId;

	@NotBlank
	@Size(max = 100)
	private String serial;

	@NotBlank
	@Size(max = 100)
	private String model;

	@NotBlank
	@Size(max = 200)
	private String name;

	public EquipmentCreateRequest() {
	}

	private EquipmentCreateRequest(Builder b) {
		this.departmentId = b.departmentId;
		this.serial = b.serial;
		this.model = b.model;
		this.name = b.name;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class Builder {
		private Integer departmentId;
		private String serial;
		private String model;
		private String name;

		public Builder departmentId(Integer departmentId) {
			this.departmentId = departmentId;
			return this;
		}

		public Builder serial(String serial) {
			this.serial = serial;
			return this;
		}

		public Builder model(String model) {
			this.model = model;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public EquipmentCreateRequest build() {
			return new EquipmentCreateRequest(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public Equipment toEquipmentEntity() {
		Equipment e = new Equipment();
		e.setDepartmentId(this.departmentId);
		e.setSerial(this.serial);
		e.setModel(this.model);
		e.setName(this.name);
		return e;
	}
}
