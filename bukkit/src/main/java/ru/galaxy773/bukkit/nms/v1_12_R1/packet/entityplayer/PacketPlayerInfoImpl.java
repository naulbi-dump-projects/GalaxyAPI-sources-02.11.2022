package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entityplayer;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketPlayerInfo;
import ru.galaxy773.bukkit.nms.types.PlayerInfoActionType;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityPlayerImpl;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity.GPacketEntityBase;

@Getter
public class PacketPlayerInfoImpl extends GPacketEntityBase<PacketPlayOutPlayerInfo, GEntityPlayer>
        implements PacketPlayerInfo {

    private PlayerInfoActionType actionType;

    public PacketPlayerInfoImpl(GEntityPlayer entity, PlayerInfoActionType actionType) {
        super(entity);
        this.actionType = actionType;
    }

    @Override
    protected PacketPlayOutPlayerInfo init() {
        return new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.valueOf(actionType.name()),
                ((GEntityPlayerImpl)entity).getEntityNms());
    }

    @Override
    public void setPlayerInfoAction(PlayerInfoActionType actionType) {
        this.actionType = actionType;
        init();
    }

}
