package com.entiv.sakuraequipment;

import com.entiv.sakuraequipment.gun.吸血鬼节杖;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
    //TODO 将普通的物品解析为 SakuraRPG 物品

    //TODO 防具检测可以通过材质, 或者自己加属性值, 没必要纠结
    public static boolean isSakuraEquipment(ItemStack itemStack) {

        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound compound = nbtItem.getCompound("SakuraEquipment");

        return compound != null;
    }

    public static boolean getItemVersion() {
        return true;
    }

}
