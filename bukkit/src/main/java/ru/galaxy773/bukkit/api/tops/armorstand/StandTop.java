package ru.galaxy773.bukkit.api.tops.armorstand;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.entity.EntityEquip;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;
import ru.galaxy773.bukkit.api.hologram.lines.TextHoloLine;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.api.utils.math.CraftVector;

@Getter
public final class StandTop {

    private static final HologramAPI HOLOGRAM_API = BukkitAPI.getHologramAPI();
    private static final EntityAPI ENTITY_API = BukkitAPI.getEntityAPI();
    private final int position;
    private final Top topType;
    private final CustomStand customStand;
    private final Hologram hologram;
    private final Location location;
    private StandTopData standTopData;

    public StandTop(JavaPlugin javaPlugin, Top topType, Location location, int position) {
        this.position = position;
        this.topType = topType;
        this.location = location;
        this.customStand = ENTITY_API.createStand(javaPlugin, location);
        this.customStand.setArms(true);
        this.customStand.setSmall(true);
        this.customStand.setBasePlate(false);
        setEquipment();
        hologram = HOLOGRAM_API.createHologram(javaPlugin, location.clone().add(0.0, 1.2, 0.0));
        hologram.addTextLine("\u00A7" + (position == 1 ? "a" : position < 4 ? "e" : "c") + position + " \u043C\u0435\u0441\u0442\u043E");
        hologram.addTextLine("\u00A7cN/A");
        hologram.addTextLine("\u00A7c\u0414\u0430\u043D\u043D\u044B\u0435 \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u044B");
    }

    public void setStandTopData(StandTopData standTopData) {
        this.standTopData = standTopData;
        update();
    }

    public void update() {
        if (standTopData == null) {
            return;
        }
        TextHoloLine holoLine1 = hologram.getHoloLine(1);
        TextHoloLine holoLine2 = hologram.getHoloLine(2);
        holoLine1.setText(standTopData.getBaseGamer().getDisplayName());
        holoLine2.setText(standTopData.getTopString());
        customStand.getEntityEquip().setHelmet(HeadUtil.getHead(standTopData.getBaseGamer().getSkin().getValue()));
    }

    public Location getLocation() {
        return location.clone();
    }

    private void setEquipment() {
        ItemStack helmet = null;
        ItemStack chestplate = null;
        ItemStack leggings = null;
        ItemStack boots = null;
        ItemStack itemInHand = null;

        switch (position) {
            case 1:
                helmet = new ItemStack(Material.DIAMOND_HELMET);
                chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                boots = new ItemStack(Material.DIAMOND_BOOTS);
                itemInHand = ItemBuilder.builder(Material.DIAMOND_SWORD)
                        .addEnchantment(Enchantment.DURABILITY, 1)
                        .build();
                customStand.setLeftArmPose(new CraftVector(-20,0,-120));
                customStand.setRightArmPose(new CraftVector(-40,50,90));
                customStand.setRightLegPose(new CraftVector(-10,70,40));
                customStand.setLeftLegPose(new CraftVector(-10,-60,-40));
                customStand.setHeadPose(new CraftVector(15,0,0));
                break;
            case 2:
                helmet = new ItemStack(Material.GOLD_HELMET);
                chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
                leggings = new ItemStack(Material.GOLD_LEGGINGS);
                boots = new ItemStack(Material.GOLD_BOOTS);
                itemInHand = ItemBuilder.builder(Material.BOW)
                        .addEnchantment(Enchantment.DURABILITY, 1)
                        .build();
                customStand.setLeftArmPose(new CraftVector(-20,0,-140));
                customStand.setRightArmPose(new CraftVector(-50,20,10));
                customStand.setRightLegPose(new CraftVector(-5,-10,10));
                customStand.setLeftLegPose(new CraftVector(-10,-10,-6));
                customStand.setHeadPose(new CraftVector(5,0,5));
                break;
            case 3:
                helmet = new ItemStack(Material.IRON_HELMET);
                chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                leggings = new ItemStack(Material.IRON_LEGGINGS);
                boots = new ItemStack(Material.IRON_BOOTS);
                itemInHand = new ItemStack(Material.SHIELD);
                customStand.setLeftArmPose(new CraftVector(50,15,-7));
                customStand.setRightArmPose(new CraftVector(-50,10,5));
                customStand.setRightLegPose(new CraftVector(-20,0,5));
                customStand.setLeftLegPose(new CraftVector(20,0,-5));
                customStand.setHeadPose(new CraftVector(0,0,2));
                break;
            case 4:
                helmet = new ItemStack(Material.CHAINMAIL_HELMET);
                chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                boots = new ItemStack(Material.CHAINMAIL_BOOTS);
                itemInHand = new ItemStack(Material.IRON_AXE);
                customStand.setLeftArmPose(new CraftVector(-10,0,-60));
                customStand.setRightArmPose(new CraftVector(-10,0,130));
                customStand.setRightLegPose(new CraftVector(0,0,60));
                customStand.setHeadPose(new CraftVector(0,0,10));
                break;
            case 5:
                helmet = new ItemStack(Material.LEATHER_HELMET);
                chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                boots = new ItemStack(Material.LEATHER_BOOTS);
                itemInHand = new ItemStack(Material.DIAMOND_PICKAXE);
                customStand.setLeftArmPose(new CraftVector(-90,-20,-30));
                customStand.setRightArmPose(new CraftVector(50,30,90));
                customStand.setRightLegPose(new CraftVector(50,0,15));
                customStand.setLeftLegPose(new CraftVector(-7,-6,-5));
                customStand.setHeadPose(new CraftVector(30,10,10));
                customStand.setBodyPose(new CraftVector(6,6,0));
                break;
        }

        EntityEquip equip = customStand.getEntityEquip();
        equip.setChestplate(chestplate);
        equip.setLeggings(leggings);
        equip.setBoots(boots);
        equip.setHelmet(helmet);
        equip.setItemInMainHand(itemInHand);
    }

    public void removeTo(Player gamer, boolean hideStand) {
        if (gamer == null)
            return;

        if (hologram != null)
            hologram.removeTo(gamer);

        if (hideStand)
            customStand.removeTo(gamer);
    }

    public void showTo(Player gamer, boolean showStand) {
        if (gamer == null)
            return;

        if (hologram != null)
            hologram.showTo(gamer);

        if (showStand)
            customStand.showTo(gamer);
    }
}
