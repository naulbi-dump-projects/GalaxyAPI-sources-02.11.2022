package ru.galaxy773.bukkit.impl.scoreboard.board.lines;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import ru.galaxy773.bukkit.nms.scoreboard.GObjective;
import ru.galaxy773.bukkit.nms.scoreboard.ObjectiveActionMode;
import ru.galaxy773.bukkit.nms.scoreboard.ObjectiveType;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayNameLine {
    private static final PacketContainer PACKET_CONTAINER = NmsAPI.getManager().getPacketContainer();

    private final GObjective dObjective;
    private final List<String> names = Collections.synchronizedList(new ArrayList<>());
    private String name;
    private long speed;
    private boolean animation;

    private Player owner;

    private int d = 0;
    private int t = 0;

    public DisplayNameLine() {
        dObjective = new GObjective("GAPIBoard", "DisplayName", ObjectiveType.INTEGER);
        name = dObjective.getDisplayName();
        speed = -1;
        animation = false;
    }

    public void setName(String name) {
        this.name = name;
        this.dObjective.setDisplayName(name);
        this.names.clear();
        animation = false;
    }

    public void setNames(String name) {
        setName(name);
        animation = true;
        this.names.addAll(StringUtil.getAnimation(name));
    }

    public void setNames(List<String> names, String name) {
        setName(name);
        if (names.size() > 1) {
            animation = true;
            this.names.addAll(names);
        }
    }

    public GObjective getdObjective() {
        return dObjective;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public List<String> getNames() {
        return names;
    }

    public boolean isAnimation() {
        return animation;
    }

    public Long getSpeed() {
        return speed;
    }

    public Runnable updateObjective() {
        return () -> {
            try {
                if (d == this.names.size()) {
                    if (t == 120){
                        t = 0;
                        d = 0;
                    }
                    this.dObjective.setDisplayName(name);
                    this.t++;
                } else {
                    this.dObjective.setDisplayName(names.get(d));
                    ++this.d;
                }
                sendUpdatePacket();
            } catch (Exception ignored) {

            }
        };
    }

    private void sendUpdatePacket() {
        if (owner == null)
            return;

        PacketScoreboardObjective packet = PACKET_CONTAINER.getScoreboardObjectivePacket(this.dObjective,
                ObjectiveActionMode.UPDATE);
        packet.sendPacket(owner);
    }

    public void setPlayerName(Player owner) {
        this.owner = owner;
    }
}
