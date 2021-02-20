package com.entiv.sakuraequipment.gun;

import com.entiv.sakuraequipment.Item;
import com.entiv.sakuraequipment.bullet.Bullet;
import com.entiv.sakuraequipment.bullet.StraightTrajectory;
import com.entiv.sakuraequipment.bullet.TrackingTrajectory;
import com.entiv.sakuraequipment.event.GunDamageEvent;
import com.entiv.sakuraequipment.event.GunShootEvent;
import com.entiv.sakuraequipment.utils.ItemBuilder;
import com.entiv.sakuraequipment.utils.Message;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

//TODO 逻辑: 玩家右键 -> 枪开枪(生成对应子弹) -> 触发开枪事件(处理 itemstack) -> 飞行
// 枪械类里面有个生成子弹的方法，每个附魔都会调用这个方法来设置子弹属性
public abstract class Gun extends Item {

    public final int damage;
    public final int magazineSize;

    public final int attackSpeed;
    public final int reloadSpeed;
    public final double bulletSpeed;

    public final double range;
    public final double criticalRate;
    public final double criticalMultiply;

    public Consumer<GunDamageEvent> damageEvent;

    protected Gun(Builder<?> builder) {
        super(builder);

        damage = builder.damage;
        magazineSize = builder.magazineSize;

        bulletSpeed = builder.bulletSpeed;
        attackSpeed = builder.attackSpeed;
        reloadSpeed = builder.reloadSpeed;

        range = builder.range;
        criticalRate = builder.criticalRate;
        criticalMultiply = builder.criticalMultiply;

    }

    public Bullet getBullet() {
        return new Bullet()
                .damage(damage)
                .speed(bulletSpeed)
                .range(range)
                .criticalRate(criticalRate)
                .criticalMultiply(criticalMultiply)
                .addBulletTrack(new StraightTrajectory())
                .addBulletTrack(new TrackingTrajectory());
    }

    public void onBulletFlyTick() {

    }

    public void onHit() {

    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = super.getItemStack();
        List<String> lore = new ArrayList<>();

        lore.add("");

        if (damage != 0) lore.add("&6攻击力: &e&l" + damage);
        if (criticalRate != 0) lore.add("&6暴击率: &e&l" + Message.formatNumber(criticalRate) + "%");
        lore.add("&6载弹量: &e&l" + magazineSize);

        lore.add("&6射程: &e&l" + range);
        lore.add("&6弹速: &e&l" + bulletSpeed);

        lore.add("&6攻击速度: &e&l" + Message.formatNumber(20.0 / attackSpeed));
        lore.add("&6换弹速度: &e&l" + Message.formatNumber(20.0 / reloadSpeed));
        if (criticalMultiply != 0) lore.add("&6暴击倍率: &e&l" + criticalMultiply + "x");



        return new ItemBuilder(itemStack).lore(lore).persistentDataContainer("BulletAmount", magazineSize).build();
    }

    @Override
    protected void setNBTCompound(NBTCompound compound) {

        compound.setInteger("Damage", damage);
        compound.setInteger("MagazineSize", magazineSize);

        compound.setDouble("BulletSpeed", bulletSpeed);
        compound.setInteger("AttackSpeed", attackSpeed);
        compound.setInteger("ReloadSpeed", reloadSpeed);

        compound.setDouble("Range", range);
        compound.setDouble("CriticalRate", criticalRate);
        compound.setDouble("CriticalMultiply", criticalMultiply);

    }

    public static abstract class Builder<T extends Item.Builder<T>> extends Item.Builder<T> {

        private int damage = 15;
        private int magazineSize = 30;

        private double bulletSpeed = 3;
        private int attackSpeed = 20;
        private int reloadSpeed = 3;

        private double range = 5;
        private double criticalRate = 0;
        private double criticalMultiply = 2.0;

        protected Builder() {
            super("Gun");
        }

        public T damage(int val) {
            damage = val;
            return self();
        }

        public T bulletAmount(int val) {
            magazineSize = val;
            return self();
        }

        public T bulletSpeed(double val) {
            bulletSpeed = val;
            return self();
        }

        public T attackSpeed(int val) {
            attackSpeed = val;
            return self();
        }

        public T reloadSpeed(int val) {
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
            attackSpeed = compound.getInteger("AttackSpeed");
            magazineSize = compound.getInteger("MagazineSize");

            bulletSpeed = compound.getDouble("BulletSpeed");
            reloadSpeed = compound.getInteger("ReloadSpeed");

            range = compound.getDouble("Range");
            criticalRate = compound.getDouble("CriticalRate");
            criticalMultiply = compound.getDouble("CriticalMultiply");

            return build();
        }


        public abstract Gun build();
    }
}
