package ru.galaxy773.cosmetics.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.cosmetics.api.Effect;
import ru.galaxy773.cosmetics.api.EffectType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

public class CritsEffectGui extends EffectsMainGui {

    public CritsEffectGui(Player player) {
        super(player);
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createInventory(this.player, Lang.getMessage("CRITS_EFFECTS_GUI_NAME"), 5);
    }

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(EffectsMainGui.class, this.player).open(), 40);
        int index = 0;
        for (Effect effect : Effect.values()) {
            this.inventory.setItem(SLOTS[index], new GItem(ItemBuilder.builder(effect.getIcon()).setName(Lang.getMessage("EFFECT_NAME", new Object[]{Lang.getMessage(("EFFECT_" + effect.name() + "_NAME"))})).setLore(Lang.getList((this.cosmeticPlayer.getSelectedParticle(EffectType.CRITS) == effect.getParticleEffect() ? "EFFECT_IS_SELECTED_LORE" : (this.cosmeticPlayer.getParticles().get(EffectType.CRITS).contains(effect.getParticleEffect()) ? "EFFECT_IS_BUYED_LORE" : "EFFECT_NOT_BUYED_LORE")), new Object[]{effect.getPrice()})).build(), (player, clickType, i) -> {
                if (this.cosmeticPlayer.getSelectedParticle(EffectType.CRITS) == effect.getParticleEffect()) {
                    this.gamer.sendMessageLocale("EFFECT_IS_SELECTED");
                    return;
                }
                if (this.cosmeticPlayer.getParticles().get(EffectType.CRITS).contains(effect.getParticleEffect())) {
                    if (Cooldown.hasCooldown(this.gamer.getName(), "EFFECT_SELECT")) {
                        this.gamer.sendMessageLocale("EFFECT_SELECT_COOLDOWN", Cooldown.getCooldownLeft(this.gamer.getName(), "EFFECT_SELECT"));
                        return;
                    }
                    Cooldown.addCooldown(this.gamer.getName(), "EFFECT_SELECT", 60L);
                    this.cosmeticPlayer.setSelectedParticle(EffectType.CRITS, effect.getParticleEffect());
                    this.setStaticItems();
                    this.gamer.sendMessageLocale("EFFECT_SELECT", Lang.getMessage(("EFFECT_" + effect.name() + "_NAME")), "\u043a\u0440\u0438\u0442\u043e\u0432");
                    return;
                }
                if (this.gamer.getCoins() < effect.getPrice()) {
                    this.gamer.sendMessageLocale("EFFECTS_NO_COINS", effect.getPrice());
                    return;
                }
                this.gamer.setCoins(this.gamer.getCoins() - effect.getPrice(), true);
                this.cosmeticPlayer.addParticle(EffectType.CRITS, effect.getParticleEffect());
                this.setStaticItems();
                this.gamer.sendMessageLocale("EFFECT_BUY", Lang.getMessage(("EFFECT_" + effect.name() + "_NAME")), "\u043a\u0440\u0438\u0442\u043e\u0432", effect.getPrice());
            }));
            ++index;
        }
        this.inventory.setItem(new GItem(ItemBuilder.builder(Material.BARRIER).setName(Lang.getMessage("EFFECT_CLEAR_NAME")).setLore(Lang.getList("EFFECT_CLEAR_LORE")).build(), (player, clickType, i) -> {
            if (this.cosmeticPlayer.getSelectedParticle(EffectType.CRITS) == null) {
                this.gamer.sendMessageLocale("EFFECT_NOT_SELECTED", "\u043a\u0440\u0438\u0442\u044b");
                return;
            }
            this.cosmeticPlayer.setSelectedParticle(EffectType.CRITS, null);
            this.setStaticItems();
            this.gamer.sendMessageLocale("EFFECT_CLEAR", "\u043a\u0440\u0438\u0442\u043e\u0432");
        }), 6, 5);
    }
}

