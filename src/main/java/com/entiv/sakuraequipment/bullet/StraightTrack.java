package com.entiv.sakuraequipment.bullet;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class StraightTrack implements BulletTrack {

    @Override
    public Location getNextLocation(Location location, Location playerLocation, Bullet bullet) {
        Vector vector = location.getDirection().multiply(0.05 * bullet.speed);
        return location.add(vector);
    }

    @Override
    public boolean condition() {
        return true;
    }
}
