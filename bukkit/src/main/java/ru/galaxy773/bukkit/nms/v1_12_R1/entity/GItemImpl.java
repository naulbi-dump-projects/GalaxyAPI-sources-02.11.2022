package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityItem;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityItem;

public class GItemImpl extends GEntityBase<EntityItem> implements GEntityItem {

    public GItemImpl(World world) {
        super(new EntityItem(world));
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        entity.setItemStack(CraftItemStack.asNMSCopy(itemStack));
    }
}
