package ru.galaxy773.bukkit.api.chatmessage;

import org.bukkit.entity.Player;

public interface ChatMessageAPI {

    void sendChatMessage(Player player, String json);
}
