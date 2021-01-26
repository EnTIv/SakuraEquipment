package com.entiv.sakuraequipment.event;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GunDamageEvent extends EntityDamageByEntityEvent {

    public GunDamageEvent(Entity damager, Entity damagee, DamageCause cause, double damage) {
        super(damager, damagee, cause, damage);
    }

}
