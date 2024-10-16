package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.entity.EntityEquip;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ArmorStandUtil {

    @Getter
    private final List<CustomStand> CUSTOM_STANDS = new ArrayList<>();
    private final BukkitPlugin JAVA_PLUGIN = BukkitPlugin.getInstance();

    public void fixArmorStand() {
        World world = Bukkit.getWorld("lobby");
        if (world == null)
            return;

        fixArmorStand(world);
    }

    public void fixArmorStand(World world) {
        EntityAPI entityAPI = BukkitAPI.getEntityAPI();
        world.getEntities().forEach(entity -> {
            if (entity instanceof ArmorStand && !checkLocation(entity.getLocation())) {
                ArmorStand armorStand = (ArmorStand) entity;
                CustomStand customStand = entityAPI.createStand(JAVA_PLUGIN, entity.getLocation().clone());
                if (armorStand.hasArms())
                    customStand.setArms(true);
                if (!armorStand.hasBasePlate())
                    customStand.setBasePlate(false);
                if (armorStand.isSmall())
                    customStand.setSmall(true);
                if (!armorStand.isVisible())
                    customStand.setInvisible(true);
                if (armorStand.isCustomNameVisible())
                    customStand.setCustomName(armorStand.getCustomName());
                setEquip(armorStand, customStand);
                setPosition(armorStand, customStand);

                customStand.setPublic(true);

                CUSTOM_STANDS.add(customStand);
                armorStand.remove();
            }
        });
    }

    private boolean checkLocation(Location location) {
        return location.getX() == 0 && location.getY() == 0 && location.getZ() == 0
                && location.getPitch() == 0 && location.getYaw() == 0;
    }

    private void setPosition(ArmorStand armorStand, CustomStand customStand) {
        customStand.setBodyPose(armorStand.getBodyPose());
        customStand.setHeadPose(armorStand.getHeadPose());
        customStand.setLeftArmPose(armorStand.getLeftArmPose());
        customStand.setRightArmPose(armorStand.getRightArmPose());
        customStand.setRightLegPose(armorStand.getRightLegPose());
        customStand.setLeftLegPose(armorStand.getLeftLegPose());
    }

    private void setEquip(ArmorStand armorStand, CustomStand customStand) {
        EntityEquipment equipment = armorStand.getEquipment();

        EntityEquip equip = customStand.getEntityEquip();
        if (equipment.getItemInMainHand() != null)
            equip.setItemInMainHand(equipment.getItemInMainHand());

        if (equipment.getItemInOffHand() != null)
            equip.setItemInOffHand(equipment.getItemInOffHand());

        if (equipment.getBoots() != null)
            equip.setBoots(equipment.getBoots());

        if (equipment.getLeggings() != null)
            equip.setLeggings(equipment.getLeggings());

        if (equipment.getChestplate() != null)
            equip.setChestplate(equipment.getChestplate());

        if (equipment.getHelmet() != null)
            equip.setHelmet(equipment.getHelmet());
    }
}

