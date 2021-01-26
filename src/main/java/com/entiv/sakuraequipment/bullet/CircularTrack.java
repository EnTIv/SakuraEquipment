package com.entiv.sakuraequipment.bullet;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CircularTrack implements BulletTrack {

    @Override
    public Location getNextLocation(Location location, Location playerLocation, Bullet bullet) {

        Vector vector = location.getDirection();
        vector.rotateAroundY(bullet.speed * 0.05);

        playerLocation.setDirection(vector);
        return playerLocation.add(vector);
    }

    @Override
    public boolean condition() {
        return false;
    }


}
