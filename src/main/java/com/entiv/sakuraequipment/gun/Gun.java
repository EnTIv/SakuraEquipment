package com.entiv.sakuraequipment.gun;

import com.entiv.sakuraequipment.Item;
import com.entiv.sakuraequipment.library.utils.ItemBuilder;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Gun extends Item {

    public final int damage;
    public final int bulletAmount;

    public final double bulletSpeed;
    public final double attackSpeed;
    public final double reloadSpeed;

    public final double range;
    public final double criticalRate;
    public final double criticalMultiply;

    protected Gun(Builder<?> builder) {
        super(builder);

        damage = builder.damage;
        bulletAmount = builder.bulletAmount;

        bulletSpeed = builder.bulletSpeed;
        attackSpeed = builder.attackSpeed;
        reloadSpeed = builder.reloadSpeed;

        range = builder.range;
        criticalRate = builder.criticalRate;
        criticalMultiply = builder.criticalMultiply;
    }

    public abstract void onShoot();

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = super.getItemStack();
        List<String> lore = new ArrayList<>();

        lore.add("");

        lore.add("&6攻击力: &e&l" + damage);
        lore.add("&6暴击率: &e&l" + criticalRate + "%");
        lore.add("&6载弹量: &e&l" + bulletAmount);

        lore.add("&6射程: &e&l" + range);
        lore.add("&6弹速: &e&l" + bulletSpeed);

        lore.add("&6攻击速度: &e&l" + attackSpeed);
        lore.add("&6换弹速度: &e&l" + reloadSpeed);
        lore.add("&6暴击倍率: &e&l" + criticalMultiply + "x");

        return new ItemBuilder(itemStack).lore(lore).build();
    }

    @Override
    protected void setNBTCompound(NBTCompound compound) {

        compound.setInteger("Damage", damage);
        compound.setInteger("BulletAmount", bulletAmount);

        compound.setDouble("BulletSpeed", bulletSpeed);
        compound.setDouble("AttackSpeed", attackSpeed);
        compound.setDouble("ReloadSpeed", reloadSpeed);

        compound.setDouble("Range", range);
        compound.setDouble("CriticalRate", criticalRate);
        compound.setDouble("CriticalMultiply", criticalMultiply);

    }

    protected static abstract class Builder<T extends Item.Builder<T>> extends Item.Builder<T> {

        private int damage = 15;
        private int bulletAmount = 30;

        private double bulletSpeed = 3;
        private double attackSpeed = 1;
        private double reloadSpeed = 3;

        private double range = 5;
        private double criticalRate = 0;
        private double criticalMultiply = 2.0;

        public T damage(int val) {
            damage = val;
            return self();
        }

        public T bulletAmount(int val) {
            bulletAmount = val;
            return self();
        }

        public T bulletSpeed(double val) {
            bulletSpeed = val;
            return self();
        }

        public T attackSpeed(double val) {
            attackSpeed = val;
            return self();
        }

        public T reloadSpeed(double val) {
            reloadSpeed = val;
            return self();
        }

        public T range(double val) {
            range = val;
            return self();
        }

        public T criticalRate(double val) {
            criticalRate = val;
            return self();
        }

        public T criticalMultiply(double val) {
            criticalMultiply = val;
            return self();
        }

        @Override
        public Gun buildFromItemStack(ItemStack itemStack) {

            super.buildFromItemStack(itemStack);

            NBTItem nbtItem = new NBTItem(itemStack);
            NBTCompound compound = nbtItem.getCompound("SakuraEquipment");

            damage = compound.getInteger("Damage");
            bulletAmount = compound.getInteger("BulletAmount");

            bulletSpeed = compound.getDouble("BulletSpeed");
            attackSpeed = compound.getDouble("AttackSpeed");
            reloadSpeed = compound.getDouble("ReloadSpeed");

            range = compound.getDouble("Range");
            criticalRate = compound.getDouble("CriticalRate");
            criticalMultiply = compound.getDouble("CriticalMultiply");

            return build();
        }


        public abstract Gun build();
    }
}
