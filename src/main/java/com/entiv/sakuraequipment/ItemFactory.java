package com.entiv.sakuraequipment;

import com.entiv.sakuraequipment.gun.Gun;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ItemFactory {

    Map<String, Constructor<?>> factories = new HashMap<>();

    private static final ItemFactory impl = new ItemFactory();

    private ItemFactory() {

    }

    private static Constructor<?> load(String type, String name) {

        try {
            return Class.forName("com.entiv.sakuraequipment." + type + "." + name + "$Builder").getConstructor();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new NullPointerException();
        }

    }

    public Gun.Builder<?> gunCreate(String name) {
        try {
            return (Gun.Builder<?>) factories
                    .computeIfAbsent(name, k -> ItemFactory.load("gun", name))
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new NullPointerException(name);
        }
    }

    public Gun.Builder<?> armorCreate(String name) {
        try {
            return (Gun.Builder<?>) factories
                    .computeIfAbsent(name, k -> ItemFactory.load("armor", name))
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new NullPointerException(name);
        }
    }

    public static ItemFactory getInstance() {
        return impl;
    }
}
