package ru.galaxy773.bukkit.nms.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatMessageType {
	
    CHAT((byte)0),
    SYSTEM((byte)1),
    GAME_INFO((byte)2);

    private final byte chatType;
}
