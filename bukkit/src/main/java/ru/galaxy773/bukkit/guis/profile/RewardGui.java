package ru.galaxy773.bukkit.guis.profile;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.leveling.LevelRewardStorage;
import ru.galaxy773.bukkit.api.gamer.leveling.Leveling;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.sound.SoundType;
import ru.galaxy773.multiplatform.impl.gamer.sections.NetworkingSection;

import java.util.List;

public class RewardGui extends AbstractGui<MultiInventory> {

    private static final Leveling LEVELING = BukkitAPI.getLeveling();

    public RewardGui(Player player) {
        super(player);
        createInventory();
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createMultiInventory(this.player,
                Lang.getMessage("REWARD_GUI_NAME"),
                5);
    }

    @Override
    protected void setStaticItems() {
        NetworkingSection section = gamer.getSection(NetworkingSection.class);
        List<LevelRewardStorage> rewards = LEVELING.getRewardsSorted();
        /*if (section == null || rewards.isEmpty()) {
            inventory.setItem(InventoryUtil.getSlotByXY(5, 3), new GItem(ItemBuilder.builder(Material.BARRIER)
                    .setName(Lang.getMessage("LEVELING_LOAD_NAME"))
                    .setLore(Lang.getList("LEVELING_LOAD_LORE"))
                    .build()));
            return;
        }*/

        int gamerLevel = gamer.getLevel();
        int giveRewardLevel = section.getLastLevelReward();

        int slot = 10;
        int page = 0;

        for (LevelRewardStorage levelReward : rewards) {
            int level = levelReward.getLevel();

            if (slot == 17) {
                slot = 19;
            } else if (slot == 26) {
                slot = 28;
            } else if (slot == 35) {
                slot = 10;
                page++;
            }

            ChatColor chatColor = level > gamerLevel || level <= giveRewardLevel ? ChatColor.RED : ChatColor.GREEN;
            inventory.setItem(page, slot, new GItem(ItemBuilder.builder(level <= giveRewardLevel ? Material.MINECART : Material.STORAGE_MINECART)
                    .setAmount(level)
                    .setName(chatColor + Lang.getMessage("LEVEL_REWARD_NAME", level))
                    .setLore(Lang.getList("LEVEL_REWARD_LORE1", level))
                    .addLore(levelReward.getLore())
                    .addLore(level <= giveRewardLevel ? Lang.getList("LEVEL_REWARD_LORE4") : (level <= gamerLevel ? Lang.getList("LEVEL_REWARD_LORE2") : Lang.getList("LEVEL_REWARD_LORE3")))
                    .build(), (player, clickType, i) -> {
                if (level > gamerLevel) {
                    gamer.sendMessage(Lang.getMessage("LEVEL_NO_LEVEL"));
                    gamer.playSound(SoundType.BREAK);
                    return;
                }

                if (level <= giveRewardLevel) {
                    gamer.sendMessage(Lang.getMessage("LEVEL_ALREADY_GIVE"));
                    gamer.playSound(SoundType.BREAK);
                    return;
                }

                if (level != giveRewardLevel + 1) {
                    gamer.sendMessage(Lang.getMessage("LEVEL_NO_OTHER_REWARD"));
                    gamer.playSound(SoundType.BREAK);
                    return;
                }

                giveReward(section, levelReward);
                gamer.playSound(SoundType.SELECTED);
                section.updateLastLevelReward();
                setStaticItems();
            }));

            slot++;
        }

        INVENTORY_API.backButton(this.inventory, (clicker, clickType, i) -> clicker.chat("/profile"), 40);
        INVENTORY_API.pageButton(inventory, 38, 42);
    }

    private void giveReward(NetworkingSection section, LevelRewardStorage levelReward) {
        if (section.getLastLevelReward() >= levelReward.getLevel()) {
            return;
        }

        levelReward.giveRewards(gamer);
    }
}
