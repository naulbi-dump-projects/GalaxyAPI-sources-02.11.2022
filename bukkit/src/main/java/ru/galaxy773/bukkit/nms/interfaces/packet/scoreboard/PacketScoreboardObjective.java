package ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard;

import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import ru.galaxy773.bukkit.nms.scoreboard.GObjective;
import ru.galaxy773.bukkit.nms.scoreboard.ObjectiveActionMode;

public interface PacketScoreboardObjective extends GPacket {

    void setObjective(GObjective objective);

    GObjective getObjective();

    void setMode(ObjectiveActionMode mode);

    ObjectiveActionMode getMode();
}
