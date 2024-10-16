package ru.galaxy773.lobby.parkour;

import org.bukkit.Location;

public class ParkourCheckpoint {
    private final Location location;
    private final int position;

    public Location getLocation() {
        return this.location;
    }

    public int getPosition() {
        return this.position;
    }

    public ParkourCheckpoint(Location location, int position) {
        this.location = location;
        this.position = position;
    }
}

