package com.entiv.sakuraequipment.bullet;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrackingTrajectory implements BulletTrajectory {

    LivingEntity nearestEntity;

    @Override
    public Location getNextLocation(Location location, Location playerLocation, Bullet bullet) {
        Vector attract = nearestEntity.getEyeLocation().toVector().subtract(location.toVector()).normalize().multiply(0.05 * bullet.getSpeed());

        location.setDirection(attract);
        return location.add(attract);
    }

    @Override
    public boolean condition(Location location, Player shooter, Bullet bullet) {

        World world = location.getWorld();

        Collection<Entity> entities = world.getNearbyEntities(location, 5, 5, 5, entity -> !entity.equals(shooter) && entity instanceof LivingEntity);
        LivingEntity nearestEntity = getNearestEntity(location, entities);

        this.nearestEntity = nearestEntity;
        return nearestEntity != null;
    }

    public LivingEntity getNearestEntity(Location location, Collection<Entity> entities) {
        List<LivingEntity> livingEntities = entities
                .stream()
                .map(entity -> (LivingEntity) entity)
                .sorted(Comparator.comparing(livingEntity -> livingEntity.getEyeLocation().distance(location))).collect(Collectors.toList());
        return livingEntities.stream().findFirst().orElse(null);
    }
}
