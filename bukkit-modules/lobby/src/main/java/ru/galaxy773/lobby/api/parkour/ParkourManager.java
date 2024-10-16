/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 */
package ru.galaxy773.lobby.api.parkour;

import java.util.Collection;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ru.galaxy773.lobby.parkour.ParkourWay;

public interface ParkourManager {
    public void addParkourWay(ParkourWay var1);

    public void removeParkourWay(ParkourWay var1);

    public void removeParkourWay(Block var1);

    public ParkourWay getParkourWay(Block var1);

    public ParkourWay getPlayerParkour(Player var1);

    public Collection<ParkourWay> getWays();

    public void loadPlayer(Player var1);

    public void unloadPlayer(Player var1);

    public long getPlayerBestTime(Player var1, ParkourWay var2);

    public void setPlayerBestTime(Player var1, ParkourWay var2, long var3);
}

