package com.entiv.sakuraequipment.gun;

import com.entiv.sakuraequipment.bullet.Bullet;
import com.entiv.sakuraequipment.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// 不可变类, 构造方法为 private 防止继承
public class RandomGun extends Gun {

    private RandomGun(Builder builder) {
        super(builder);
    }

    public static RandomGun getInstance() {

        int damage = randomInt(1, 20);
        double range = randomDouble(10, 30.0, 2);

        int attackSpeed = randomInt(1, 25);
        double bulletSpeed = randomDouble(0, 40, 1);
        int reloadSpeed = randomInt(10, 65);

        int bulletAmount = randomInt(1, 30);

        double criticalRate = randomInt(1, 100);
        double criticalMultiply = randomDouble(1.0, 2.0, 2);

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
    public Bullet getBullet() {
        return new Bullet(damage, bulletSpeed, range, criticalRate, criticalMultiply);
    }

    @Override
    public ItemStack getItemStack() {

        List<String> firstNames = new ArrayList<>(Arrays.asList(
                "黑色",
                "蝰蛇",
                "无尽",
                "都市",
                "翡翠",
                "沙漠",
                "格洛克",
                "鲍伊",
                "法玛斯",

                "银装",
                "竹林",
                "精雕",
                "早雁",
                "烟霞",
                "白发",
                "尘埃",
                "素颜",
                "锦虎",

                "机械",
                "火神",
                "四号",
                "火卫",
                "凤凰",
                "截短",
                "蓝色",
                "蓝洞",
                "古铜",

                "霓虹",
                "炽热",
                "至尊",
                "红线",
                "音乐",
                "复古",
                "灯神",
                "北冥",
                "爪子",

                "全球",
                "九头蛇",
                "轻轨",
                "闪回",
                "狩猎",
                "网格",
                "重围",
                "混沌",
                "纪念碑",

                "雅典娜"
        ));

        List<String> lastNames = new ArrayList<>(Arrays.asList(

                "树蝰",
                "净化者",
                "黄铜",
                "死神",
                "怒氓",
                "之主",
                "元素",
                "幻影",
                "迷雾",

                "骑士",
                "威龙",
                "浪潮",
                "皇帝",
                "侦测",
                "绝命",
                "密电",
                "攻势",
                "如梦",

                "深海",
                "之牙",
                "伪装",
                "色调",
                "之影",
                "之鹰",
                "魅影",
                "鼬鼠",
                "之眼",

                "赛博",
                "澜磷",
                "伪装",
                "紫罗兰",
                "之色",
                "之网",
                "工业",
                "屠夫",
                "骷髅",

                "栖息地",
                "商号",
                "启示录",
                "一",
                "月光",
                "野牛",
                "红苹果",
                "黯翼",
                "野牛",

                "神祗"
        ));

        String firstName = firstNames.get(new Random().nextInt(firstNames.size()));
        String lastName = lastNames.get(new Random().nextInt(lastNames.size()));

        return new ItemBuilder(super.getItemStack()).name("&d&l" + firstName + lastName).build();
    }

    public static class Builder extends Gun.Builder<Builder> {

        public Builder() {
            super();
        }

        @Override
        public RandomGun build() {
            return new RandomGun(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}



