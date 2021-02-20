package com.entiv.sakuraequipment.bullet;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface BulletTrajectory {

    Location getNextLocation(Location location, Location eyeLocation, Bullet bullet);

    boolean condition(Location location, Player shooter, Bullet bullet);
}
