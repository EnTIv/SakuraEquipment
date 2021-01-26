package com.entiv.sakuraequipment.bullet;

import org.bukkit.Location;

public interface BulletTrack {

    Location getNextLocation(Location location, Location eyeLocation, Bullet bullet);

    boolean condition();
}
