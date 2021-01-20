package com.entiv.sakuraequipment.gun;

import org.bukkit.Material;

// 不可变类, 构造方法为 private 防止继承
public class 吸血鬼节杖 extends Gun {

    private 吸血鬼节杖(Builder builder) {
        super(builder);
    }

    @Override
    public void onShoot() {

    }

    public static class Builder extends Gun.Builder<Builder> {

        @Override
        public 吸血鬼节杖 build() {
            return new 吸血鬼节杖(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public static 吸血鬼节杖 getInstance() {

        int damage = randomInt(10, 15);
        int range = randomInt(5, 6);

        double attackSpeed = randomDouble(1.0, 1.3, 2);
        double bulletSpeed = randomDouble(1.0, 3.0, 2);
        double reloadSpeed = randomDouble(2.5, 3.5, 2);

        int bulletAmount = randomInt(30, 35);

        double criticalRate = randomDouble(1.0, 1.3, 2);
        double criticalMultiply = randomDouble(1.8, 2.2, 2);


        return new Builder()
                .material(Material.LEATHER_HORSE_ARMOR)
                .version(1)

                .damage(damage)
                .range(range)

                .attackSpeed(attackSpeed)
                .bulletSpeed(bulletSpeed)
                .reloadSpeed(reloadSpeed)

                .bulletAmount(bulletAmount)
                .criticalRate(criticalRate)
                .criticalMultiply(criticalMultiply)
                .build();
    }
}



