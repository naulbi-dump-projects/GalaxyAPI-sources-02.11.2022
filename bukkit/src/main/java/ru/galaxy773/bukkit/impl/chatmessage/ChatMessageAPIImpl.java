package ru.galaxy773.bukkit.impl.chatmessage;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.chatmessage.ChatMessageAPI;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.types.ChatMessageType;

public class ChatMessageAPIImpl implements ChatMessageAPI {

    private final PacketContainer container = NmsAPI.getManager().getPacketContainer();

    @Override
    public void sendChatMessage(Player player, String json) {
        container.sendChatPacket(player, json, ChatMessageType.SYSTEM);
    }
}
