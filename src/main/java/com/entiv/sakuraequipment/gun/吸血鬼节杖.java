package com.entiv.sakuraequipment.gun;

import org.bukkit.Material;

// 不可变类, 构造方法为 private 防止继承
public class 吸血鬼节杖 extends Gun {

    private 吸血鬼节杖(Builder builder) {
        super(builder);
    }

    public static 吸血鬼节杖 getInstance() {

        int damage = randomInt(10, 15);
        double range = randomDouble(5, 7, 1);

        double attackSpeed = randomDouble(1.0, 1.3, 1);
        double bulletSpeed = randomDouble(1.0, 3.0, 1);
        double reloadSpeed = randomDouble(2.5, 3.5, 1);

        int bulletAmount = randomInt(30, 35);

        double criticalRate = randomInt(10, 20);
        double criticalMultiply = randomDouble(1.8, 2.2, 1);


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

    @Override
    public void onShoot() {

    }

    public static class Builder extends Gun.Builder<Builder> {

        public Builder() {
            super();
        }

        @Override
        public 吸血鬼节杖 build() {
            return new 吸血鬼节杖(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}



