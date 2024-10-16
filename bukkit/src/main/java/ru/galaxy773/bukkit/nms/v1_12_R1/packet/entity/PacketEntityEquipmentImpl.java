package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityEquipment;

@Getter
public class PacketEntityEquipmentImpl extends GPacketEntityBase<PacketPlayOutEntityEquipment, GEntity>
        implements PacketEntityEquipment {

    private EquipType slot;
    private ItemStack itemStack;

    public PacketEntityEquipmentImpl(GEntity entity, EquipType slot, ItemStack itemStack) {
        super(entity);
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @Override
    protected PacketPlayOutEntityEquipment init() {
        return new PacketPlayOutEntityEquipment(entity.getEntityID(), EnumItemSlot.valueOf(slot.name()),
                CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void setSlot(EquipType slot) {
        this.slot = slot;
        init();
    }


    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        init();
    }
}
