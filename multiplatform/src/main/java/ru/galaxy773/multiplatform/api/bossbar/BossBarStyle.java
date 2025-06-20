package ru.galaxy773.multiplatform.api.bossbar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BossBarStyle {

	NONE(0),
	NOTCHES_6(1),
	NOTCHES_10(2),
	NOTCHES_12(3),
	NOTCHES_20(4);
	
	private final int id;
}
