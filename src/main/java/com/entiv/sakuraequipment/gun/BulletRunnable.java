package com.entiv.sakuraequipment.gun;

import com.entiv.sakuraequipment.Main;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;

// 子弹不用枪械内部类的原因：10种子弹 100 种枪械用的话，不好实现
public class BulletRunnable extends BukkitRunnable {

    Location location;
    Vector vector;
    World world;
    Player shooter;

    double damage;
    double flyTime;

    public BulletRunnable(Player shooter, Bullet bullet) {

        this.shooter = shooter;
        location = shooter.getEyeLocation();

        world = location.getWorld();
        vector = location.getDirection().multiply(0.05 * bullet.getSpeed());
        damage = bullet.getDamage();

        flyTime = bullet.getFlyTime() * 20;
    }

    @Override
    public void run() {

        world.spawnParticle(Particle.BUBBLE_POP, location, 1, 0, 0, 0, 0.01);

        if (checkBlockCollision() || checkEntityCollision()) {
            cancel();
        }

        flightTrack();

        flyTime--;

        if (flyTime == 0) cancel();

    }

    private void flightTrack() {
        location.add(vector);
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

            nearbyEntity.damage(damage, shooter);

            break;
        }
        return true;
    }
}
