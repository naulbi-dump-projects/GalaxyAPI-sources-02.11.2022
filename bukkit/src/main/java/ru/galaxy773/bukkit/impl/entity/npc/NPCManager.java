package ru.galaxy773.bukkit.impl.entity.npc;

import io.netty.util.internal.ConcurrentSet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.entity.npc.NPC;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import ru.galaxy773.bukkit.nms.scoreboard.GTeam;
import ru.galaxy773.bukkit.nms.scoreboard.TagVisibility;
import ru.galaxy773.bukkit.nms.scoreboard.TeamAction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NPCManager {

    private final PacketContainer packetContainer = NmsAPI.getManager().getPacketContainer();

    private final Map<String, Set<Integer>> playersTeam = new ConcurrentHashMap<>();
    private final Map<Integer, CraftNPC> npcList = new ConcurrentHashMap<>();

    public void addPlayerToTeam(Player player, NPC npc, ChatColor chatColor) {
        String name = player.getName();
        Set<Integer> npcs = playersTeam.computeIfAbsent(name, s -> new ConcurrentSet<Integer>());
        if (npcs.contains(npc.getEntityID()))
            return;

        npcs.add(npc.getEntityID());
        createTeam(player, npc, chatColor);
    }

    void removeFromTeams(Player player) {
        playersTeam.remove(player.getName());
    }

    public void addNPC(CraftNPC npc) {
        if (npc.entity == null)
            return;

        npcList.put(npc.getEntityID(), npc);
    }

    public Map<Integer, CraftNPC> getNPCs() {
        return npcList;
    }

    void removeNPC(CraftNPC npc) {
        npcList.remove(npc.getEntityID());
    }

    private void createTeam(Player player, NPC npc, ChatColor chatColor) {
        GTeam team = new GTeam(npc.getName() + "_TEAM");
        team.setVisibility(TagVisibility.NEVER);
        team.setPrefix(chatColor.toString());
        PacketScoreBoardTeam packet = packetContainer.getScoreBoardTeamPacket(team, TeamAction.CREATE);
        packet.sendPacket(player);
    }
}
