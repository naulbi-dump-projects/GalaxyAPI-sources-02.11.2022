package ru.galaxy773.bukkit.impl.scoreboard.tag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.scoreboard.Collides;
import ru.galaxy773.bukkit.api.scoreboard.PlayerTag;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import ru.galaxy773.bukkit.nms.scoreboard.GTeam;
import ru.galaxy773.bukkit.nms.scoreboard.TeamAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CraftTag implements PlayerTag {

    private static final PacketContainer CONTAINER = NmsAPI.getManager().getPacketContainer();
    
    private final GTeam team;
    private boolean enabled;

    public CraftTag(String name) {
        team = new GTeam(name);
    }

    @Override
    public void sendToAll() {
        enabled = true;
        PacketScoreBoardTeam packet1 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.CREATE);
        PacketScoreBoardTeam packet2 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        PacketScoreBoardTeam packet3 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_ADD);
        Bukkit.getOnlinePlayers().forEach(pl -> CONTAINER.sendPacket(pl, packet1, packet2, packet3));
    }

    @Override
    public void sendTo(Collection<Player> players) {
        enabled = true;
        PacketScoreBoardTeam packet1 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.CREATE);
        PacketScoreBoardTeam packet2 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        PacketScoreBoardTeam packet3 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_ADD);
        players.forEach(pl -> CONTAINER.sendPacket(pl, packet1, packet2, packet3));
    }

    @Override
    public void sendTo(Player player) {
        enabled = true;
        PacketScoreBoardTeam packet1 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.CREATE);
        PacketScoreBoardTeam packet2 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        PacketScoreBoardTeam packet3 = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_ADD);
        CONTAINER.sendPacket(player, packet1, packet2, packet3);
    }

    @Override
    public void addPlayerToTeam(Player player) {
        team.addPlayer(player.getName());
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_ADD);
        sendPacket(packet);
    }

    @Override
    public void addPlayersToTeam(Collection<Player> players) {
        players.forEach(player -> team.addPlayer(player.getName()));
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_ADD);
        sendPacket(packet);
    }

    @Override
    public void addNamesToTeam(Collection<String> names) {
        names.forEach(team::addPlayer);
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_ADD);
        sendPacket(packet);
    }

    @Override
    public void removePlayerFromTeam(Player player) {
        if (!team.removePlayer(player.getName())) {
            return;
        }

        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_REMOVE);
        packet.sendPacket(player);
        sendPacket(packet);
    }

    @Override
    public boolean isContainsTeam(Player player) {
        String name = player.getName();
        return team.getPlayers().contains(name);
    }

    @Override
    public boolean isEmpty() {
        return team.getPlayers().isEmpty();
    }

    @Override
    public List<String> getPlayersTeam() {
        return new ArrayList<>(team.getPlayers());
    }

    @Override
    public void setFriendInv(boolean friendInv) {
        team.setSetFriendInv(friendInv);

        if (enabled) {
            sendPacket(CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE));
        }
    }

    private void sendPacket(GPacket packet) {
        if (enabled) {
            team.getPlayers().forEach(name -> {
                Player player = Bukkit.getPlayer(name);
                packet.sendPacket(player);
            });
        }
    }

    @Override
    public void setPrefixSuffix(String prefix, String suffix) {
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        sendPacket(packet);
    }

    @Override
    public void setPrefix(String prefix) {
        team.setPrefix(prefix);
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        sendPacket(packet);
    }

    @Override
    public void setSuffix(String suffix) {
        team.setSuffix(suffix);
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        sendPacket(packet);
    }

    @Override
    public void disableCollidesForAll() {
        setCollides(Collides.NEVER);
    }

    @Override
    public void setCollides(Collides collides) {
        team.setCollides(collides);
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        sendPacket(packet);
    }

    @Override
    public void remove() {
        PacketScoreBoardTeam packet = CONTAINER.getScoreBoardTeamPacket(team, TeamAction.REMOVE);
        sendPacket(packet);

        team.getPlayers().clear();
        enabled = false;
    }
}
