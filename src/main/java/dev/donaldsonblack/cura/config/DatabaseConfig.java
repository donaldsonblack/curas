package dev.donaldsonblack.cura.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import org.json.JSONObject;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {
	private String password;
	private String username;

	@Value("${aws.rds.endpoint}")
	private String url;

	@Value("${aws.secret.region}")
	private String secretRegion;

	@Value("${aws.secret.name}")
	private String secretName;

	@Bean
	public DataSource dataSource() {
		try {
			getSecrets();

		} catch (Exception e) {
			System.err.println("Error retrieving secrets: " + e.getMessage());
			throw new RuntimeException("Failed to retrieve database credentials from AWS Secrets Manager", e);
		}
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);
		return dataSource;
	}

	private void getSecrets() throws Exception {
		SecretsManagerClient client = SecretsManagerClient.builder()
				.region(Region.of(secretRegion))
				.build();

		GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
				.secretId(secretName)
				.build();

		GetSecretValueResponse getSecretValueResponse;
		try {
			getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
		} catch (Exception e) {
			// Handle exceptions such as ResourceNotFoundException, InvalidRequestException,
			// etc.
			throw e;
		}
		String secretString = getSecretValueResponse.secretString();

		JSONObject jsonObject = new JSONObject(secretString);
		this.password = jsonObject.getString("password");
		this.username = jsonObject.getString("username");

	}
}
