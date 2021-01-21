package com.entiv.sakuraequipment.listener;


import com.entiv.sakuraequipment.ItemFactory;
import com.entiv.sakuraequipment.ItemManager;
import com.entiv.sakuraequipment.gun.Gun;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (itemStack == null) return;

        if (ItemManager.isSakuraEquipment(itemStack)) {

            NBTItem nbtItem = new NBTItem(itemStack);
            NBTCompound compound = nbtItem.getCompound("SakuraEquipment");

            String name = compound.getString("Name");
            String type = compound.getString("Type");

            if (!type.equals("Gun")) return;

            Gun.Builder<?> builder = ItemFactory.getInstance().gunCreate(name);
            Gun gun = builder.buildFromItemStack(itemStack);
            System.out.println(gun.name);
            System.out.println(gun.damage);
        }
    }

    @EventHandler
    public void onRocketHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();
        Damageable hitEntity = (Damageable) event.getHitEntity();

        if (hitEntity == null) return;
        hitEntity.damage(10, Bukkit.getPlayer("EnTIv"));
//        projectile.getWorld().createExplosion(projectile.getLocation(), 5, false, false);
    }

}
