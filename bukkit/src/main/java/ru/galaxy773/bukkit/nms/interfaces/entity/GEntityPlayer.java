package ru.galaxy773.bukkit.nms.interfaces.entity;

import com.mojang.authlib.GameProfile;

public interface GEntityPlayer extends GEntityLiving {

    String getName();

    GameProfile getProfile();

    int getPing();
}
