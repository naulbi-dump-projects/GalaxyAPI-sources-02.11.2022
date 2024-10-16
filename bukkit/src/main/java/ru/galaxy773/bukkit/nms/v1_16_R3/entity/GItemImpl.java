package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityItem;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityItem;

public class GItemImpl extends GEntityBase<EntityItem> implements GEntityItem {

    public GItemImpl(World world) {
        super(new EntityItem(EntityTypes.ITEM, world));
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        entity.setItemStack(CraftItemStack.asNMSCopy(itemStack));
    }
}
