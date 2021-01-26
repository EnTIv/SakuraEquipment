package com.entiv.sakuraequipment.bullet;

import com.entiv.sakuraequipment.event.GunDamageEvent;
import com.entiv.sakuraequipment.utils.Message;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 子弹不用枪械内部类的原因：10种子弹 100 种枪械用的话，不好实现
public class BulletRunnable extends BukkitRunnable {

    Location location;
    World world;
    Player shooter;

    double damage;
    double flyTime;
    boolean isCritical;

    Bullet bullet;

    public BulletRunnable(Player shooter, Bullet bullet) {

        this.shooter = shooter;
        location = shooter.getEyeLocation();

        world = location.getWorld();
        damage = bullet.realDamage;

        flyTime = bullet.range * 20;
        isCritical = bullet.isCritical;
        this.bullet = bullet;
    }

    @Override
    public void run() {

        //TODO 采用状态设计模式, 一种子弹只能同时存在一种轨迹, 以最后触发的轨迹条件为准, 现在的问题是如何给子弹添加两种轨迹?
        // 比如默认是使用直线轨迹, 当触发跟踪轨迹条件时使用跟踪轨迹, 碰到方块时触发反弹轨迹

        if (checkBlockCollision() || checkEntityCollision()) {
            location.setDirection(location.getDirection().multiply(-1));
//            cancel();
        }

        Collection<Entity> entities = world.getNearbyEntities(location, 5, 5, 5, entity -> !entity.equals(shooter) && entity instanceof LivingEntity);

        LivingEntity nearestEntity = getNearestEntity(entities);

        if (nearestEntity != null) {

            Vector attract = nearestEntity.getEyeLocation().toVector().subtract(location.toVector()).normalize();

            location.setDirection(attract);
            location = location.add(attract);

        } else {

            updateLocation();

        }

        flyTime--;

        if (flyTime <= 0) cancel();

        world.spawnParticle(Particle.BUBBLE_POP, location, 1, 0, 0, 0, 0.01);
    }

    private void updateLocation() {

        for (BulletTrack bulletTrack : bullet.bulletTracks) {
            location = bulletTrack.getNextLocation(location, shooter.getEyeLocation(), bullet);
        }

    }

    private boolean checkBlockCollision() {
        Block block = world.getBlockAt(location);
        if (block.isPassable()) return false;

        BlockData blockData = world.getBlockAt(location).getBlockData();
        world.spawnParticle(Particle.BLOCK_CRACK, location, 7, 0.5, 0.5, 0.5, 0.0001, blockData);

        return true;
    }

    private boolean checkEntityCollision() {

        Collection<LivingEntity> nearbyEntities = location.getNearbyLivingEntities(0.5, 0.5, 0.5, entity -> !entity.equals(shooter));

        if (nearbyEntities.isEmpty()) return false;

        for (LivingEntity nearbyEntity : nearbyEntities) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 3);
            world.spawnParticle(Particle.REDSTONE, location, 5, 0.2, 0.2, 0.2, 0.00001, dustOptions);

            GunDamageEvent event = new GunDamageEvent(shooter, nearbyEntity, EntityDamageEvent.DamageCause.PROJECTILE, damage);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (isCritical) {
                Message.sendActionBar(shooter, "&4&l暴击!");
            }

            break;
        }

        return true;
    }

    public LivingEntity getNearestEntity(Collection<Entity> entities) {
        List<LivingEntity> livingEntities = entities
                .stream()
                .map(entity -> (LivingEntity) entity)
                .sorted(Comparator.comparing(livingEntity -> livingEntity.getEyeLocation().distance(location))).collect(Collectors.toList());
        return livingEntities.stream().findFirst().orElse(null);
    }
}
