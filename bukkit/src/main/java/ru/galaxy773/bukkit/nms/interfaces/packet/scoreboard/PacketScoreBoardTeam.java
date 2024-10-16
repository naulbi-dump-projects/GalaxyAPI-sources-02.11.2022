package ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard;

import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import ru.galaxy773.bukkit.nms.scoreboard.GTeam;
import ru.galaxy773.bukkit.nms.scoreboard.TeamAction;

public interface PacketScoreBoardTeam extends GPacket {

    void setTeam(GTeam team);
    GTeam getTeam();

    void setTeamAction(TeamAction action);
    TeamAction getTeamAction();
}
