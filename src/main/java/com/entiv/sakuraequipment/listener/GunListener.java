package com.entiv.sakuraequipment.listener;

import com.entiv.sakuraequipment.event.GunShootEvent;
import com.entiv.sakuraequipment.gun.Bullet;
import com.entiv.sakuraequipment.utils.Performance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GunListener implements Listener {

    @EventHandler
    public void onGunShootEvent(GunShootEvent event) {

        new Performance(1) {
            @Override
            public void run() {
                Bullet bullet = new Bullet(event.getShooter());
            }
        };
    }
}
