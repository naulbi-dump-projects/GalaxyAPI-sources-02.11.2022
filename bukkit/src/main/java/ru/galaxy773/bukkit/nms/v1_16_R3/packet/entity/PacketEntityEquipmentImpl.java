package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityEquipment;

import java.util.ArrayList;
import java.util.List;

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
        final List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> x = new ArrayList<>();
        x.add(new Pair<>(EnumItemSlot.valueOf(slot.name()), CraftItemStack.asNMSCopy(itemStack)));
        return new PacketPlayOutEntityEquipment(this.entity.getEntityID(), x);
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
