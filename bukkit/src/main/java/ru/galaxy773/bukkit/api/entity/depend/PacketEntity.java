package ru.galaxy773.bukkit.api.entity.depend;

import org.bukkit.Location;

public interface PacketEntity extends PacketObject {
	
    int getEntityID();

    Location getLocation();

    void onTeleport(Location location);
}
