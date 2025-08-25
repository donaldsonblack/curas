package dev.donaldsonblack.cura.model.equipment;

public class Equipment {

	private Integer id;
	private Integer departmentId;
	private String serial;
	private String model;
	private String name;

	public Equipment() {
	}

	public Equipment(Integer id, Integer departmentId, String serial, String model, String name) {
		this.id = id;
		this.departmentId = departmentId;
		this.serial = serial;
		this.model = model;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

		public Equipment build() {
			return new Equipment(id, departmentId, serial, model, name);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
