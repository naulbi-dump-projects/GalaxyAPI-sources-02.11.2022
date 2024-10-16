package ru.galaxy773.bukkit.nms.interfaces.packet.entity;

import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EquipType;

public interface PacketEntityEquipment extends GPacketEntity<GEntity> {

    void setSlot(EquipType slot);

    EquipType getSlot();

    void setItemStack(ItemStack itemStack);

    ItemStack getItemStack();
}
