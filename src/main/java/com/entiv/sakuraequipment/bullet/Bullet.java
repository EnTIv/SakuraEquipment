package com.entiv.sakuraequipment.bullet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bullet {

    public final List<BulletTrajectory> bulletTrajectories = new ArrayList<>();
    private double speed;
    private double damage;
    private double range;
    private double criticalRate;
    private double criticalMultiply;
    private double realDamage;
    private boolean isCritical = false;

    public Bullet damage(double damage) {

        this.damage = damage;
        return this;
    }

    public Bullet speed(double speed) {
        this.speed = speed;
        return this;
    }

    public Bullet range(double range) {
        this.range = range;
        return this;
    }

    public Bullet criticalRate(double criticalRate) {
        this.criticalRate = criticalRate;
        return this;
    }

    public Bullet criticalMultiply(double criticalMultiply) {
        this.criticalMultiply = criticalMultiply;
        return this;
    }

    public Bullet addBulletTrack(BulletTrajectory bulletTrajectory) {
        bulletTrajectories.add(bulletTrajectory);
        return this;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDamage() {
        int i = new Random().nextInt(100);

        if (i < criticalRate) {
            this.damage = damage * criticalMultiply;
            isCritical = true;
        }

        return damage;
    }

    public double getCriticalMultiply() {
        return criticalMultiply;
    }

    public double getCriticalRate() {
        return criticalRate;
    }

    public double getRange() {
        return range;
    }

    public boolean isCritical() {
        return isCritical;
    }
}


