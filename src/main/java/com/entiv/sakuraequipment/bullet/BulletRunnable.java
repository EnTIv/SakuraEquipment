package com.entiv.sakuraequipment.bullet;

import com.entiv.sakuraequipment.event.GunDamageEvent;
import com.entiv.sakuraequipment.utils.Message;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

// 子弹不用枪械内部类的原因：10种子弹 100 种枪械用的话，不好实现
public class BulletRunnable extends BukkitRunnable {

    Location bulletLocation;
    Player shooter;

    double flyTime;

    Bullet bullet;
    BulletTrajectory bulletTrajectory = null;

    public BulletRunnable(Player shooter, Bullet bullet) {

        this.shooter = shooter;
        bulletLocation = shooter.getEyeLocation();

        flyTime = bullet.getRange() * 20;
        this.bullet = bullet;
    }

    @Override
    public void run() {

        //TODO 采用状态设计模式, 一种子弹只能同时存在一种轨迹, 以最后触发的轨迹条件为准, 现在的问题是如何给子弹添加两种轨迹?
        // 比如默认是使用直线轨迹, 当触发跟踪轨迹条件时使用跟踪轨迹, 碰到方块时触发反弹轨迹

        if (checkBlockCollision() || checkEntityCollision()) {
//            location.setDirection(location.getDirection().multiply(-1));
            cancel();
        }

        updateLocation();

        flyTime--;

        if (flyTime <= 0) cancel();

        bulletLocation.getWorld().spawnParticle(Particle.BUBBLE_POP, bulletLocation, 1, 0, 0, 0, 0.01);
    }

    private void updateLocation() {

        for (int i = bullet.bulletTrajectories.size() - 1; i >= 0; i--) {

            BulletTrajectory bulletTrajectory = bullet.bulletTrajectories.get(i);

            if (bulletTrajectory.condition(bulletLocation, shooter, bullet)) {
                this.bulletTrajectory = bulletTrajectory;
                break;
            }

        }

        bulletLocation = bulletTrajectory.getNextLocation(bulletLocation, shooter.getEyeLocation(), bullet);
    }

    private boolean checkBlockCollision() {

        World world = bulletLocation.getWorld();
        Block block = world.getBlockAt(bulletLocation);

        if (block.isPassable()) return false;

        BlockData blockData = world.getBlockAt(bulletLocation).getBlockData();
        world.spawnParticle(Particle.BLOCK_CRACK, bulletLocation, 7, 0.5, 0.5, 0.5, 0.0001, blockData);

        return true;
    }

    private boolean checkEntityCollision() {

        Collection<LivingEntity> nearbyEntities = bulletLocation.getNearbyLivingEntities(0.5, 0.5, 0.5, entity -> !entity.equals(shooter));

        if (nearbyEntities.isEmpty()) return false;

        for (LivingEntity nearbyEntity : nearbyEntities) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 3);
            bulletLocation.getWorld().spawnParticle(Particle.REDSTONE, bulletLocation, 5, 0.2, 0.2, 0.2, 0.00001, dustOptions);

            GunDamageEvent event = new GunDamageEvent(shooter, nearbyEntity, EntityDamageEvent.DamageCause.PROJECTILE, bullet.getDamage());
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (bullet.isCritical()) {
                Message.sendActionBar(shooter, "&4&l暴击!");
            }

            break;
        }

        return true;
    }

}
