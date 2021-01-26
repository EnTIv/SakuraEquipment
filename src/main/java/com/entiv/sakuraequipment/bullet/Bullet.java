package com.entiv.sakuraequipment.bullet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bullet {

    public final double speed;
    public final double damage;
    public final double range;

    public final double criticalRate;
    public final double criticalMultiply;
    public final double realDamage;

    public boolean isCritical = false;

    final List<BulletTrack> bulletTracks = new ArrayList<>();

    public Bullet(double damage, double speed, double range, double criticalRate, double criticalMultiply) {

        this.damage = damage;
        this.speed = speed;
        this.range = range;

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

    public void addBulletTrack(BulletTrack bulletTrack) {
        bulletTracks.add(bulletTrack);
    }
}
