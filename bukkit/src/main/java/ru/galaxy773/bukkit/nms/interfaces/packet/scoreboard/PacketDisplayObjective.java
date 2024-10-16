package ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard;

import ru.galaxy773.bukkit.api.scoreboard.DisplaySlot;
import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import ru.galaxy773.bukkit.nms.scoreboard.GObjective;

public interface PacketDisplayObjective extends GPacket {

    void setObjective(GObjective objective);

    GObjective getObjective();

    void setDisplaySlot(DisplaySlot slot);

    DisplaySlot getSlot();
}
