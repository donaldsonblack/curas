package dev.donaldsonblack.cura.model.equipment;

public class EquipmentPatchRequest {

	private Integer id; // optional; usually path param
	private Integer departmentId;
	private String serial;
	private String model;
	private String name;

	public EquipmentPatchRequest() {
	}

	private EquipmentPatchRequest(Builder b) {
		this.id = b.id;
		this.departmentId = b.departmentId;
		this.serial = b.serial;
		this.model = b.model;
		this.name = b.name;
	}

	// ---- Getters ----
	public Integer getId() {
		return id;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public String getSerial() {
		return serial;
	}

	public String getModel() {
		return model;
	}

	public String getName() {
		return name;
	}

	public static class Builder {
		private Integer id;
		private Integer departmentId;
		private String serial;
		private String model;
		private String name;

		public Builder id(Integer id) {
			this.id = id;
			return this;
		}

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

		public EquipmentPatchRequest build() {
			return new EquipmentPatchRequest(this);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public Equipment applyTo(Equipment existing) {
		if (this.departmentId != null)
			existing.setDepartmentId(this.departmentId);
		if (this.serial != null)
			existing.setSerial(this.serial);
		if (this.model != null)
			existing.setModel(this.model);
		if (this.name != null)
			existing.setName(this.name);
		return existing;
	}
}
