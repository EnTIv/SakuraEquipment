package com.entiv.sakuraequipment.gun;

import com.entiv.sakuraequipment.utils.ItemBuilder;
import com.entiv.sakuraequipment.utils.Message;

import java.util.Random;

public class Bullet {

    public final double speed;
    public final double damage;
    public final double flyTime;

    public final double criticalRate;
    public final double criticalMultiply;

    public boolean isCritical = false;
    public final double realDamage;

    public Bullet(double damage,double speed,double flyTime, double criticalRate, double criticalMultiply) {
        this.damage = damage;
        this.speed = speed;
        this.flyTime = flyTime;

        this.criticalRate = criticalRate;
        this.criticalMultiply = criticalMultiply;

        int i = new Random().nextInt(100);

        if (i < criticalRate) {
            realDamage = damage * criticalMultiply;
            isCritical = true;
        } else {
            realDamage = damage;
        }

    }

}
