package dev.donaldsonblack.cura.event;

public class ApiAccessEvent {
	private final String userid;
	private final String endpoint;
	private final String method;
	private final String ip;

	public ApiAccessEvent(String userId, String endpoint, String method, String ip) {
		this.userid = userId;
		this.endpoint = endpoint;
		this.method = method;
		this.ip = ip;
	}

	public String getUserId() {
		return this.userid;
	}

	public String getEndpoint() {
		return this.endpoint;
	}

	public String getMethod() {
		return this.method;
	}

	public String getIp() {
		return this.ip;
	}

}
