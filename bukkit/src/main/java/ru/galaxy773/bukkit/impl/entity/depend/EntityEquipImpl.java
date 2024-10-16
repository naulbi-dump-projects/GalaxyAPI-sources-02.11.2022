package ru.galaxy773.bukkit.impl.entity.depend;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EntityEquip;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class EntityEquipImpl implements EntityEquip {

    protected static final PacketContainer PACKET_CONTAINER = NmsAPI.getManager().getPacketContainer();

    protected final Map<EquipType, ItemStack> items = new ConcurrentHashMap<>();
    @Override
    public ItemStack getHelmet() {
        return getItem(EquipType.HEAD);
    }
    @Override
    public ItemStack getChestplate() {
        return getItem(EquipType.CHEST);
    }
    @Override
    public ItemStack getLeggings() {
        return getItem(EquipType.LEGS);
    }
    @Override
    public ItemStack getBoots() {
        return getItem(EquipType.FEET);
    }
    @Override
    public ItemStack getItemInMainHand() {
        return getItem(EquipType.MAINHAND);
    }
    @Override
    public ItemStack getItemInOffHand() {
        return getItem(EquipType.OFFHAND);
    }
    @Override
    public ItemStack getItem(EquipType equipType) {
        ItemStack item = items.get(equipType);
        if (item != null)
            return item.clone();

        return null;
    }

    @Override
    public Map<EquipType, ItemStack> getItemsEquip() {
        return new HashMap<>(items);
    }

    @Override
    public void setHelmet(ItemStack helmet) {
        setItem(EquipType.HEAD, helmet);
    }

    @Override
    public void setChestplate(ItemStack chestplate) {
        setItem(EquipType.CHEST, chestplate);
    }

    @Override
    public void setLeggings(ItemStack leggings) {
        setItem(EquipType.LEGS, leggings);
    }

    @Override
    public void setBoots(ItemStack boots) {
        setItem(EquipType.FEET, boots);
    }

    @Override
    public void setItemInMainHand(ItemStack item) {
        setItem(EquipType.MAINHAND, item);
    }

    @Override
    public void setItemInOffHand(ItemStack item) {
        setItem(EquipType.OFFHAND, item);
    }

    @Override
    public void removeItems() {
        items.keySet().forEach(this::removeItem);
    }
}
