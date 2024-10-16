package ru.galaxy773.bukkit.nms.v1_12_R1.packet.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardDisplayObjective;
import ru.galaxy773.bukkit.api.scoreboard.DisplaySlot;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import ru.galaxy773.bukkit.nms.scoreboard.GObjective;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.GPacketBase;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

@AllArgsConstructor
@Getter
public class PacketDisplayObjectiveImpl extends GPacketBase<PacketPlayOutScoreboardDisplayObjective>
        implements PacketDisplayObjective {

    private DisplaySlot slot;
    private GObjective objective;

    @Override
    public void setObjective(GObjective objective) {
        this.objective = objective;
        init();
    }

    @Override
    public void setDisplaySlot(DisplaySlot slot) {
        this.slot = slot;
        init();
    }

    @Override
    protected PacketPlayOutScoreboardDisplayObjective init() {
        PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();

        ReflectionUtil.setFieldValue(packet, "a", slot.ordinal());
        ReflectionUtil.setFieldValue(packet, "b", (objective == null ? "" : objective.getName()));

        return packet;
    }
}
