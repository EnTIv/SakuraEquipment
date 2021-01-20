package com.entiv.sakuraequipment;

import com.entiv.sakuraequipment.library.utils.ItemBuilder;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public abstract class Item {

    public final Material material;
    public final int version;

    public final String name;
    public final String type;
    public final String uuid;
    public final String describe;

    protected Item(Builder<?> builder) {

        material = builder.material;
        version = builder.version;

        name = builder.name;
        type = builder.type;
        uuid = builder.uuid;
        describe = builder.describe;
    }

    // 设置物品所需的 NBT
    protected abstract void setNBTCompound(NBTCompound compound);
    public ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        ItemStack itemStack = new ItemBuilder(material).name("&d&l" + name).lore(lore).addLore(describe).build();
        NBTItem nbtItem = new NBTItem(itemStack);

        nbtItem.addCompound("SakuraEquipment");
        NBTCompound compound = nbtItem.getCompound("SakuraEquipment");

        compound.setString("Name", name);
        compound.setString("UUID", uuid);
        compound.setInteger("Version", version);

        setNBTCompound(compound);

        return nbtItem.getItem();
    }

    // 取值范围为 [min, max]
    protected static int randomInt(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }

    // 取值范围为 [min, max], scale 取几位小数
    protected static double randomDouble(double min, double max, int scale) {
        double randomDouble = min + ((max - min) * new Random().nextDouble());
        BigDecimal bigDecimal = BigDecimal.valueOf(randomDouble);
        return bigDecimal.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(uuid, item.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    // 建造者模式
    protected abstract static class Builder<T extends Builder<T>> {

        private int version = 1;
        private Material material = Material.STONE;

        private String name = getClass().getSimpleName();
        private String type = "Item";

        private String uuid = UUID.randomUUID().toString();
        private String describe;

        public T version(int val) {
            version = val;
            return self();
        }

        public T material(Material val) {
            material = val;
            return self();
        }

        public T describe(String val) {
            describe = val;
            return self();
        }

        public Item buildFromItemStack(ItemStack itemStack) {

            NBTItem nbtItem = new NBTItem(itemStack);
            NBTCompound compound = nbtItem.getCompound("SakuraEquipment");

            name = compound.getString("Name");
            type = compound.getString("Type");
            uuid = compound.getString("UUID");
            version = compound.getInteger("Version");

            return build();
        }

        public abstract Item build();

        protected abstract T self();
    }
}
