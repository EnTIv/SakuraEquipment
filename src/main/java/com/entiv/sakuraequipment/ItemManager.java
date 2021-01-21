package com.entiv.sakuraequipment;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

    //TODO 防具检测可以通过材质, 或者自己加属性值, 没必要纠结
    public static boolean isSakuraEquipment(ItemStack itemStack) {

        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound compound = nbtItem.getCompound("SakuraEquipment");

        return compound != null;
    }

    public static NBTCompound getCompound(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getCompound("SakuraEquipment");
    }

    public static boolean getItemVersion() {
        return true;
    }

}
