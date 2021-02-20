package com.entiv.sakuraequipment.bullet;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class StraightTrajectory implements BulletTrajectory {

    @Override
    public Location getNextLocation(Location location, Location playerLocation, Bullet bullet) {
        Vector vector = location.getDirection().multiply(0.05 * bullet.getSpeed());
        return location.add(vector);
    }

    @Override
    public boolean condition(Location location, Player shooter, Bullet bullet) {
        return true;
    }

}
