package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.utils.reflection.BukkitReflectionUtil;

@UtilityClass
public class ItemUtil {

    public Material getMaterialByVersion(String v12, String v16) {
        return BukkitReflectionUtil.getServerVersionNumber() == 12 ? Material.getMaterial(v12) : Material.getMaterial(v16);
    }

    public ItemStack stringToItem(String item) {
        String[] itemSplit = item.split(" ");

        ItemStack itemStack;

        if (itemSplit[0].contains(":")) {
            if (itemSplit[0].startsWith("Head")) {
                if (itemSplit[0].split(":")[1].length() > 16){
                    itemStack = HeadUtil.getHead(itemSplit[0].split(":")[1]);
                } else {
                    itemStack = HeadUtil.getHead(itemSplit[0].split(":")[1]);
                }
            } else {
                itemStack = new ItemStack(ItemBuilder.builder(itemSplit[0]).build());
            }
        } else {
        	itemStack = new ItemStack(ItemBuilder.builder(itemSplit[0]).build());
        }

        if (itemSplit.length >= 2){
            itemStack.setAmount(Integer.parseInt(itemSplit[1]));
        }

        if (itemSplit.length >= 3){
            String[] enchants = itemSplit[2].toUpperCase().split(";");
            for (String enchant : enchants) {
                try {
                    if (enchant.contains(":")) {
                        itemStack.addUnsafeEnchantment(Enchantment.getByName(enchant.split(":")[0]), Integer.parseInt(enchant.split(":")[1]));
                    } else {
                        itemStack.addUnsafeEnchantment(Enchantment.getByName(enchant), 1);
                    }
                } catch (Exception e) {
                     throw new IllegalArgumentException("Enchantment " + enchant.split(":")[0] + " not found");
                }
            }
        }

        return itemStack;
    }
}
