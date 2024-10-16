package ru.galaxy773.bukkit.nms.v1_12_R1.packet.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import ru.galaxy773.bukkit.nms.scoreboard.GTeam;
import ru.galaxy773.bukkit.nms.scoreboard.TeamAction;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.GPacketBase;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

@Getter
@AllArgsConstructor
public class PacketScoreBoardTeamImpl extends GPacketBase<PacketPlayOutScoreboardTeam> implements PacketScoreBoardTeam {

    private GTeam team;
    private TeamAction teamAction;

    @Override
    public void setTeam(GTeam team) {
        this.team = team;
        init();
    }

    @Override
    public void setTeamAction(TeamAction action) {
        this.teamAction = action;
        init();
    }

    @Override
    protected PacketPlayOutScoreboardTeam init() {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

        ReflectionUtil.setFieldValue(packet, "a", team.getName());
        ReflectionUtil.setFieldValue(packet, "e", team.getVisibility().getValue());

        ReflectionUtil.setFieldValue(packet, "i", teamAction.getMode());
        ReflectionUtil.setFieldValue(packet, "j", team.packOptionData());
        ReflectionUtil.setFieldValue(packet, "g", 0);

        ReflectionUtil.setFieldValue(packet, "h", team.getPlayers());

        if (teamAction == TeamAction.CREATE || teamAction == TeamAction.UPDATE) {
            ReflectionUtil.setFieldValue(packet, "b", team.getDisplayName());
            ReflectionUtil.setFieldValue(packet, "c", team.getPrefix());
            ReflectionUtil.setFieldValue(packet, "d", team.getSuffix());

            ReflectionUtil.setFieldValue(packet, "f", team.getCollides().getValue());
        }

        return packet;
    }
}
