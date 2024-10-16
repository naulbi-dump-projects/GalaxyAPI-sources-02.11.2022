package ru.galaxy773.bukkit.impl.usableItem;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.usableitem.ClickType;
import ru.galaxy773.bukkit.api.usableitem.UsableItem;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;
import ru.galaxy773.bukkit.api.utils.bukkit.SoundUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UsablesManager extends GListener<BukkitPlugin> {

    @Getter
    private final Map<String, CraftUsableItem> usableItems = new ConcurrentHashMap<>();

    UsablesManager(BukkitPlugin BukkitPlugin) {
        super(BukkitPlugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack itemHand = e.getItem();
        Player player = e.getPlayer();
        if (itemHand == null) {
            return;
        }

        ItemMeta itemMeta = itemHand.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        String displayName = itemMeta.getDisplayName();
        if (displayName == null) {
            return;
        }

        CraftUsableItem usableItem = usableItems.get(displayName);
        if (usableItem == null) {
            return;
        }

        e.setCancelled(true);

        Action action = e.getAction();
        boolean left = action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;

        if (Cooldown.hasCooldown(player.getName(), "usables")) {
            return;
        }

        Cooldown.addCooldown(player.getName(), "usables", 20L);
        ClickType clickType = left ? ClickType.LEFT : ClickType.RIGHT;
        usableItem.getClickAction().onClick(player, clickType, e.getClickedBlock());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().removeIf(item ->
                item != null &&
                item.hasItemMeta() &&
                item.getItemMeta().hasDisplayName() &&
                usableItems.get(item.getItemMeta().getDisplayName()) != null &&
                !usableItems.get(item.getItemMeta().getDisplayName()).isDropOnDeath());
    }

    @EventHandler
    public void onPlayerClickOwnInv(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null
                || !e.getClickedInventory().equals(player.getInventory())
                || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        ItemStack clicked = e.getCurrentItem();
        if (clicked == null)
            return;

        ItemMeta itemMeta = clicked.getItemMeta();
        if (itemMeta == null)
            return;

        String displayName = itemMeta.getDisplayName();
        if (displayName == null) {
            return;
        }

        CraftUsableItem usableItem = usableItems.get(displayName);
        if (usableItem != null && usableItem.isInvClick()) {
            e.setCancelled(true);
            if (e.getClick() != org.bukkit.event.inventory.ClickType.CREATIVE) {
                SoundUtil.play(player, Sound.BLOCK_NOTE_BASS);
                usableItem.getClickAction().onClick(player, ClickType.RIGHT, null);
            }
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e) {
        disable(e.getOffHandItem(), e);
        disable(e.getMainHandItem(), e);
    }

    private void disable(ItemStack itemStack, Cancellable e) {
        if (itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null) {
            String displayName = itemStack.getItemMeta().getDisplayName();
            if (displayName == null) {
                return;
            }

            if (usableItems.get(displayName) != null) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        ItemStack dropItem = e.getItemDrop().getItemStack();
        if (!dropItem.hasItemMeta()) {
            return;
        }

        String displayName = dropItem.getItemMeta().getDisplayName();
        if (displayName == null) {
            return;
        }

        UsableItem usableItem = usableItems.get(displayName);
        if (usableItem == null) {
            return;
        }
        if (!usableItem.isDrop()) {
             e.setCancelled(true);
        } else {
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onQuitPlayer(PlayerQuitEvent e) {
        String name = e.getPlayer().getName();
        for (UsableItem usableItem : usableItems.values()) {
            Player owner = usableItem.getOwner();
            if (owner != null && owner.getName().equalsIgnoreCase(name)) {
                usableItem.remove();
            }
        }
    }
}
