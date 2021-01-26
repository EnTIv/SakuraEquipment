package com.entiv.sakuraequipment.gun;

public class 分裂枪 extends Gun {

    private 分裂枪(Builder builder) {
        super(builder);
    }

    @Override
    public void onBulletFlyTick() {

    }

    @Override
    public void onHit() {

    }


    public static class Builder extends Gun.Builder<Builder> {

        public Builder() {
            super();
        }

        @Override
        public 分裂枪 build() {
            return new 分裂枪(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
