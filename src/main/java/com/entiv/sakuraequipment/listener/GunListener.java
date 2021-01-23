package com.entiv.sakuraequipment.listener;

import com.entiv.sakuraequipment.Main;
import com.entiv.sakuraequipment.event.GunShootEvent;
import com.entiv.sakuraequipment.gun.BulletRunnable;
import com.entiv.sakuraequipment.gun.Gun;
import com.entiv.sakuraequipment.utils.Message;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GunListener implements Listener {

    private static final NamespacedKey namespacedKey = new NamespacedKey(Main.getInstance(), "BulletAmount");

    @EventHandler
    public void onGunShootEvent(GunShootEvent event) {

        Gun gun = event.getGun();
        Player player = event.getShooter();
        ItemStack itemStack = event.getItemStack();
        Integer bulletAmount = getBulletAmount(itemStack);

        if (player.hasCooldown(itemStack.getType())) return;

        consumeBullet(player, itemStack, gun.magazineSize);

        BulletRunnable runnable = new BulletRunnable(player, gun.getBullet());
        runnable.runTaskTimer(Main.getInstance(), 0, 1);
        player.setCooldown(itemStack.getType(), gun.attackSpeed);

        if (bulletAmount <= 1) {
            reload(player, itemStack, gun);
        }
    }

    private Integer getBulletAmount(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        Integer bulletAmount = container.get(namespacedKey, PersistentDataType.INTEGER);
        assert bulletAmount != null;

        return bulletAmount;
    }

    private void consumeBullet(Player player, ItemStack itemStack, Integer magazineSize) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        Integer bulletAmount = getBulletAmount(itemStack);

        container.set(namespacedKey, PersistentDataType.INTEGER, bulletAmount -= 1);
        itemStack.setItemMeta(itemMeta);

        Message.sendActionBar(player, "&a&l" + bulletAmount + " | " + magazineSize);
    }

    private void reload(Player player, ItemStack itemStack, Gun gun) {
        Message.sendActionBar(player, "&c&l换弹中...");

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(namespacedKey, PersistentDataType.INTEGER, gun.magazineSize);
        itemStack.setItemMeta(itemMeta);

        player.setCooldown(itemStack.getType(), gun.reloadSpeed);
    }
}
