package ru.galaxy773.bukkit.nms.v1_16_R3.packet.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.ScoreboardServer;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreboardScore;
import ru.galaxy773.bukkit.nms.scoreboard.GScore;
import ru.galaxy773.bukkit.nms.scoreboard.ScoreboardAction;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.GPacketBase;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

@Getter
@AllArgsConstructor
public class PacketScoreboardScoreImpl extends GPacketBase<PacketPlayOutScoreboardScore>
        implements PacketScoreboardScore {

    private GScore score;
    private ScoreboardAction action;
    
    @Override
    public void setScore(GScore score) {
        this.score = score;
        init();
    }
    
    @Override
    public void setAction(ScoreboardAction action) {
        this.action = action;
        init();
    }
    
    @Override
    protected PacketPlayOutScoreboardScore init() {
        final PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();
        ReflectionUtil.setFieldValue(packet, "a", score.getName());
        ReflectionUtil.setFieldValue(packet, "b", score.getGObjective().getName());
        ReflectionUtil.setFieldValue(packet, "c", score.getScore());
        ReflectionUtil.setFieldValue(packet, "d", ScoreboardServer.Action.valueOf(action.name()));
        return packet;
    }
}
