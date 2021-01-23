package com.entiv.sakuraequipment.gun;

import com.entiv.sakuraequipment.utils.ItemBuilder;
import com.entiv.sakuraequipment.utils.Message;

public class Bullet {

    private double speed = 3;
    private double damage = 1;
    private double flyTime = 10;

    public Bullet() {

    }

    public Bullet speed(double speed) {
        this.speed = speed;
        return this;
    }

    public Bullet damage(double damage) {
        this.damage = damage;
        return this;
    }

    public Bullet flyTime(double flyTime) {
        this.flyTime = flyTime;
        return this;
    }

    public double getDamage() {
        return damage;
    }

    public double getFlyTime() {
        return flyTime;
    }

    public double getSpeed() {
        return speed;
    }

}
