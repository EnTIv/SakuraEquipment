package com.entiv.sakuraequipment.gun;

import com.entiv.sakuraequipment.Item;
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
public abstract class Gun extends Item {

    public final int damage;
    public final int magazineSize;

    public final double bulletSpeed;
    public final double attackSpeed;
    public final double reloadSpeed;

    public final double range;
    public final double criticalRate;
    public final double criticalMultiply;

    public Consumer<GunShootEvent> shootEvent;

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

    public void onShoot() {

    }

    public abstract void onBulletFlyTick();

    public abstract void onHit();

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = super.getItemStack();
        List<String> lore = new ArrayList<>();

        lore.add("");

        lore.add("&6攻击力: &e&l" + damage);
        lore.add("&6暴击率: &e&l" + Message.formatNumber(criticalRate) + "%");
        lore.add("&6载弹量: &e&l" + magazineSize);

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
        compound.setInteger("MagazineSize", magazineSize);
        compound.setInteger("BulletAmount", magazineSize);

        compound.setDouble("BulletSpeed", bulletSpeed);
        compound.setDouble("AttackSpeed", attackSpeed);
        compound.setDouble("ReloadSpeed", reloadSpeed);

        compound.setDouble("Range", range);
        compound.setDouble("CriticalRate", criticalRate);
        compound.setDouble("CriticalMultiply", criticalMultiply);

    }

    public static abstract class Builder<T extends Item.Builder<T>> extends Item.Builder<T> {

        private int damage = 15;
        private int magazineSize = 30;

        private double bulletSpeed = 3;
        private double attackSpeed = 1;
        private double reloadSpeed = 3;

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
            magazineSize = compound.getInteger("MagazineSize");

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
