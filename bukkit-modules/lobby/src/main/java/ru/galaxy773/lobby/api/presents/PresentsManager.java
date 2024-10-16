/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 */
package ru.galaxy773.lobby.api.presents;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface PresentsManager {
    public int getMaxCount();

    public int getFindedCount(Player var1);

    public boolean locationExists(Block var1);

    public boolean locationFind(Player var1, Location var2);

    public void loadPlayer(Player var1);

    public void unloadPlayer(Player var1);
}

