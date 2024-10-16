package ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard;

import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import ru.galaxy773.bukkit.nms.scoreboard.GScore;
import ru.galaxy773.bukkit.nms.scoreboard.ScoreboardAction;

public interface PacketScoreboardScore extends GPacket {

    GScore getScore();

    void setScore(GScore score);

    ScoreboardAction getAction();

    void setAction(ScoreboardAction action);
}
