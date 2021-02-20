package com.entiv.sakuraequipment.bullet;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CircularTrajectory implements BulletTrajectory {

    @Override
    public Location getNextLocation(Location location, Location playerLocation, Bullet bullet) {

        double radius = bullet.getRange() / 5;

        if (radius < 1) radius = 1;

        Vector vector = location.getDirection().multiply(radius);

        double angularVelocity = bullet.getSpeed() / radius;

        vector.rotateAroundY(Math.toRadians(angularVelocity));

        playerLocation.setDirection(vector);
        return playerLocation.add(vector);
    }

    @Override
    public boolean condition(Location location, Player shooter, Bullet bullet) {
        return true;
    }


}
