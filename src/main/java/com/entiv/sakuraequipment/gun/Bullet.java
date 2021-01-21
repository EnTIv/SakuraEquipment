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
public class Bullet extends BukkitRunnable {

    Location location;
    Vector vector;
    World world;
    Player shooter;

    public Bullet(Player shooter) {

        this.shooter = shooter;
        location = shooter.getEyeLocation();

        world = location.getWorld();
        vector = location.getDirection().multiply(0.3);

        runTaskTimer(Main.getInstance(), 0, 1);
    }

    @Override
    public void run() {

        location.add(vector);
        world.spawnParticle(Particle.BUBBLE_POP, location, 1, 0, 0, 0, 0.01);

        if (collisionBlock()) return;
        if (collisionEntity()) return;

        System.out.println(1);
        location.getNearbyEntities(0.5, 0.5, 0.5);
    }

    private boolean collisionBlock() {

        Block block = world.getBlockAt(location);
        System.out.println((block.isPassable()));
        if (block.isPassable()) return false;

        BlockData blockData = world.getBlockAt(location).getBlockData();
        world.spawnParticle(Particle.BLOCK_CRACK, location, 7, 0.5, 0.5, 0.5, 0.0001, blockData);

        cancel();
        return true;
    }

    private boolean collisionEntity() {

        Collection<LivingEntity> nearbyEntities = location.getNearbyLivingEntities(0.5, 0.5, 0.5, entity -> !entity.equals(shooter));

        if (nearbyEntities.isEmpty()) return false;

        for (LivingEntity nearbyEntity : nearbyEntities) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 3);
            world.spawnParticle(Particle.REDSTONE, location, 5, 0.2, 0.2, 0.2, 0.00001, dustOptions);

            break;
        }

        cancel();
        return true;
    }
}
