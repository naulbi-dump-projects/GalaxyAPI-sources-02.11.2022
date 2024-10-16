package ru.galaxy773.boxes.gui;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.boxes.Boxes;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.boxes.api.ItemManager;
import ru.galaxy773.boxes.data.Box;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.game.SubType;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.InventoryAPI;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemUtil;
import ru.galaxy773.bukkit.api.utils.reflection.BukkitReflectionUtil;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.api.locale.Lang;

import java.util.*;

public class BoxGui {

    private static final InventoryAPI INVENTORY_API = BukkitAPI.getInventoryAPI();
    private static final ItemManager ITEM_MANAGER = Boxes.getInstance().getItemManager();
    private static final Map<KeysType, ItemStack> KEYS_ICONS = new EnumMap<>(KeysType.class);
    private static final int[] SLOTS = new int[] {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
    };
    private final Player player;
    private final Box box;
    private final GInventory inventory;

    public BoxGui(Player player, Box box) {
        this.player = player;
        this.box = box;
        this.inventory = INVENTORY_API.createInventory(Lang.getMessage("BOXES_GUI_NAME"), 5);
        this.inventory.setCloseAction(player1 -> box.getPlayersOpenGui().remove(player1));
        ItemStack glassPane = null;
        if (BukkitReflectionUtil.getServerVersionNumber() == 12) {
            glassPane = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 15);
        } else {
            glassPane = new ItemStack(Material.getMaterial("GREEN_STAINED_GLASS_PANE"));
        }
        this.inventory.fill(new GItem(glassPane));
    }

    public void open() {
        if (this.box.isOpen()) {
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(this.player);
        int index = 0;
        Set<KeysType> keysTypes = Sets.newHashSet(KeysType.getGlobalKeys());
        Arrays.stream(KeysType.values())
                .filter(keysType -> keysType.name().startsWith(SubType.getCurrent().name()))
                .forEach(keysTypes::add);
        for (KeysType keysType : keysTypes) {
            List<BoxReward> rewards = ITEM_MANAGER.getRewards(keysType);
            if (rewards == null || rewards.isEmpty()) continue;
            this.inventory.setItem(SLOTS[index], new GItem(ItemBuilder.builder(KEYS_ICONS.get(keysType))
                    .setName(Lang.getMessage("BOXES_" + keysType.name() + "_NAME"))
                    .setLore(Lang.getList("BOXES_" + keysType.name() + "_LORE", gamer.getKeys(keysType)))
                    .build(),
                    (clicker, clickType, slot) -> {
                if (this.box.isOpen()) {
                    this.player.closeInventory();
                    return;
                }
                if (gamer.getKeys(keysType) == 0) {
                    gamer.sendMessageLocale("BOXES_NO_KEYS");
                    return;
                }
                gamer.setKeys(keysType, gamer.getKeys(keysType) - 1, true);
                this.box.open(gamer, keysType);
            }));
            ++index;
        }
        this.box.getPlayersOpenGui().add(this.player);
        this.inventory.openInventory(this.player);
    }

    static {
        KEYS_ICONS.put(KeysType.DONATE, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY0YTkxNDMzZDY2YmZjNmVmMWM5YWM0ZTM3MWE4ODZhMWM0NjJjOTc1ZmY4MTI3NzYxZDcwM2UwYTNlNzMwNiJ9fX0="));
        KEYS_ICONS.put(KeysType.COINS, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA0NzE5YjNiOTdkMTk1YTIwNTcxOGI2ZWUyMWY1Yzk1Y2FmYTE2N2U3YWJjYTg4YTIxMDNkNTJiMzdkNzIyIn19fQ=="));
        KEYS_ICONS.put(KeysType.GOLD, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxMGQzZmQ0Mjk3NzhmM2U5NzIxZGY4NjVlNTY2ZTU0NjE3ZDU5MDZhODkzY2EwZTdhZmQ3NzE3MWZkOTAifX19"));
        KEYS_ICONS.put(KeysType.SURVIVAL_SOULS, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVlMWIzODM1ZTM2ZWMwYmIyZTMxZTFjZDJiODAzNjU2M2ZkNTNlMzc3NDM3ZTM2OGJhZTUxMmY2MTU2NmIwIn19fQ=="));
        KEYS_ICONS.put(KeysType.ONEBLOCK_SOULS, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVlMWIzODM1ZTM2ZWMwYmIyZTMxZTFjZDJiODAzNjU2M2ZkNTNlMzc3NDM3ZTM2OGJhZTUxMmY2MTU2NmIwIn19fQ=="));
        KEYS_ICONS.put(KeysType.TITULS, new ItemStack(Material.NAME_TAG));
        KEYS_ICONS.put(KeysType.SURVIVAL_ITEMS, new ItemStack(Material.END_CRYSTAL));
        KEYS_ICONS.put(KeysType.SURVIVAL_MONEY, new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")));
        KEYS_ICONS.put(KeysType.ONEBLOCK_ITEMS, new ItemStack(Material.END_CRYSTAL));
        KEYS_ICONS.put(KeysType.ONEBLOCK_MONEY, new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")));
    }
}

