package ru.galaxy773.bukkit.impl.entity.npc;

import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.impl.entity.depend.EntityEquipImpl;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityEquipment;

@AllArgsConstructor
public final class NpcEquip extends EntityEquipImpl {

    private final CraftNPC npc;

    @Override
    public void setItem(EquipType equipType, ItemStack itemStack) {
        GEntityLiving entity = npc.getEntity();
        if (itemStack == null || entity == null)
            return;

        items.put(equipType, itemStack);
        entity.setEquipment(equipType, itemStack);

        PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(entity, equipType, itemStack);
        npc.sendNearby(packet);
    }

    @Override
    public boolean removeItem(EquipType equipType) {
        GEntityLiving entity = npc.getEntity();
        if (entity == null)
            return false;

        if (items.remove(equipType) != null) {
            entity.setEquipment(equipType, null);

            PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(entity, equipType, null);
            npc.sendNearby(packet);
            return true;
        }

        return false;
    }
}
