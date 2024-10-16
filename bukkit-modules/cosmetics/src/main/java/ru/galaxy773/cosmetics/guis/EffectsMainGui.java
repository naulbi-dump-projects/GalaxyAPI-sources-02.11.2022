package ru.galaxy773.cosmetics.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.guis.profile.ProfileMainPage;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.cosmetics.api.CosmeticsAPI;
import ru.galaxy773.cosmetics.api.Effect;
import ru.galaxy773.cosmetics.api.EffectType;
import ru.galaxy773.cosmetics.api.manager.CosmeticManager;
import ru.galaxy773.cosmetics.api.player.CosmeticPlayer;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

public class EffectsMainGui extends AbstractGui<GInventory> {

    protected static final CosmeticManager COSMETIC_MANAGER = CosmeticsAPI.getCosmeticManager();
    protected static final GuiManager<AbstractGui<?>> GUI_MANAGER = BukkitAPI.getGuiManager();
    protected static final int[] SLOTS = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29};
    protected final CosmeticPlayer cosmeticPlayer;

    public EffectsMainGui(Player player) {
        super(player);
        this.cosmeticPlayer = COSMETIC_MANAGER.getCosmeticPlayer(player);
        this.createInventory();
    }

    protected void createInventory() {
        this.inventory = INVENTORY_API.createInventory(this.player, Lang.getMessage("EFFECTS_GUI_NAME"), 5);
    }

    protected void setStaticItems() {
        INVENTORY_API.backButton(this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(ProfileMainPage.class, this.player).open(), 40);
        this.inventory.setItem(new GItem(ItemBuilder.builder(Material.TIPPED_ARROW).removeFlags().setName(Lang.getMessage("ARROWS_EFFECTS_NAME")).setLore(Lang.getList("ARROWS_EFFECTS_LORE", this.cosmeticPlayer.getParticles().get(EffectType.ARROWS).size(), Effect.values().length, StringUtil.onPercent(this.cosmeticPlayer.getParticles().get(EffectType.ARROWS).size(), Effect.values().length) + "%")).build(), (player, clickType, i) -> (GUI_MANAGER.getGui(ArrowsEffectGui.class, player)).open()), 3, 3);
        this.inventory.setItem(new GItem(ItemBuilder.builder(Material.REDSTONE).setName(Lang.getMessage("CRITS_EFFECTS_NAME")).setLore(Lang.getList("CRITS_EFFECTS_LORE", this.cosmeticPlayer.getParticles().get(EffectType.CRITS).size(), Effect.values().length, StringUtil.onPercent(this.cosmeticPlayer.getParticles().get(EffectType.CRITS).size(), Effect.values().length) + "%")).build(), (player, clickType, i) -> (GUI_MANAGER.getGui(CritsEffectGui.class, player)).open()), 5, 3);
        this.inventory.setItem(new GItem(ItemBuilder.builder(Material.DIAMOND_SWORD).setName(Lang.getMessage("KILLS_EFFECTS_NAME")).setLore(Lang.getList("KILLS_EFFECTS_LORE", this.cosmeticPlayer.getParticles().get(EffectType.KILLS).size(), Effect.values().length, StringUtil.onPercent(this.cosmeticPlayer.getParticles().get(EffectType.KILLS).size(), Effect.values().length) + "%")).build(), (player, clickType, i) -> (GUI_MANAGER.getGui(KillsEffectGui.class, player)).open()), 7, 3);
    }
}

