package com.entiv.sakuraequipment.event;

import com.entiv.sakuraequipment.ItemManager;
import com.entiv.sakuraequipment.gun.Gun;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class GunShootEvent extends Event {

    private final ItemStack itemStack;
    private final Gun gun;
    private final Player shooter;

    private static final HandlerList handlers = new HandlerList();

    public GunShootEvent(ItemStack itemStack, Gun gun, Player shooter) {
        this.gun = gun;
        this.itemStack = itemStack;
        this.shooter = shooter;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Gun getGun() {
        return gun;
    }

    public Player getShooter() {
        return shooter;
    }
}
