package dev.donaldsonblack.cura;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.CommandLineRunner;

@EnableAsync
@SpringBootApplication
public class Cura implements CommandLineRunner {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	public static void main(String[] args) {
		SpringApplication.run(Cura.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("=========================================");
		System.out.println(" ACTIVE SPRING PROFILE: " + activeProfile);
		System.out.println("=========================================");
	}
}
