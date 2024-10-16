package ru.galaxy773.bukkit.impl.entity.customstand;

import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.impl.entity.depend.EntityEquipImpl;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityArmorStand;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityEquipment;

@AllArgsConstructor
public final class StandEquip extends EntityEquipImpl {

    private final CraftStand stand;

    @Override
    public void setItem(EquipType equipType, ItemStack itemStack) {
        if (itemStack == null)
            return;

        GEntityArmorStand armorStand = stand.getEntity();

        items.put(equipType, itemStack);
        armorStand.setEquipment(equipType, itemStack);

        PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(armorStand, equipType, itemStack);
        stand.sendPacket(packet);
    }

    @Override
    public boolean removeItem(EquipType equipType) {
        GEntityArmorStand armorStand = stand.getEntity();
        if (armorStand == null)
            return false;

        if (items.remove(equipType) != null) {
            armorStand.setEquipment(equipType, null);

            PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(armorStand, equipType, null);
            stand.sendPacket(packet);
            return true;
        }

        return false;
    }
}
