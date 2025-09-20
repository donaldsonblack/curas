package dev.donaldsonblack.cura.model;

import java.util.Comparator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
	member(0, "Member"),
	USER(0, "User"),
	MANAGER(20, "Manager"),
	ADMIN(50, "Admin"),
	viewer(0, "Viewer"),
	tech(0, "Tech");

	private final Integer weight;
	private final String name;
}
