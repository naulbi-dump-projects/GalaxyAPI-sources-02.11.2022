package ru.galaxy773.multiplatform.api.bossbar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BossBarColor {

	PINK(0),
	BLUE(1),
	RED(2),
	GREEN(3),
	YELLOW(4),
	PURPLE(5),
	WHITE(6);
	
	private final int id;
}
