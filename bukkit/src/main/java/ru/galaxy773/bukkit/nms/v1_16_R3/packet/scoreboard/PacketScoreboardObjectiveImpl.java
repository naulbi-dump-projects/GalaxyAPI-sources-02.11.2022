package ru.galaxy773.bukkit.nms.v1_16_R3.packet.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import ru.galaxy773.bukkit.nms.scoreboard.GObjective;
import ru.galaxy773.bukkit.nms.scoreboard.ObjectiveActionMode;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.GPacketBase;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

@Getter
@AllArgsConstructor
public class PacketScoreboardObjectiveImpl extends GPacketBase<PacketPlayOutScoreboardObjective>
        implements PacketScoreboardObjective {

    private GObjective objective;
    private ObjectiveActionMode mode;
    
    @Override
    public void setObjective(GObjective objective) {
        this.objective = objective;
        init();
    }
    
    @Override
    public void setMode(ObjectiveActionMode mode) {
        this.mode = mode;
        init();
    }
    
    @Override
    protected PacketPlayOutScoreboardObjective init() {
        final PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();

        ReflectionUtil.setFieldValue(packet, "a", objective.getName());
        ReflectionUtil.setFieldValue(packet, "b", new ChatMessage(objective.getDisplayName()));
        ReflectionUtil.setFieldValue(packet, "c", IScoreboardCriteria.EnumScoreboardHealthDisplay.valueOf(objective.getType().name()));
        ReflectionUtil.setFieldValue(packet, "d", mode.ordinal());

        return packet;
    }
}
