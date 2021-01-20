package com.entiv.sakuraequipment.library.utils;

public abstract class Performance {

    public Performance(int number) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < number; i++) {
            run();
        }

        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("运行耗时: " + time + " ms");
    }

    public abstract void run() throws IllegalAccessException, InstantiationException;
}
