package ru.galaxy773.menu.manager;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.menu.api.menu.Menu;
import ru.galaxy773.menu.hooks.EconomyHook;
import ru.galaxy773.menu.utils.MenuUtil;
import ru.galaxy773.menu.utils.builders.MenuBuilder;
import ru.galaxy773.menu.utils.builders.MenuItemBuilder;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.sound.SoundType;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public final class MenuLoader {

    private final boolean economyEnabled = Bukkit.getPluginManager().getPlugin("Vault") != null;

    private final Random RANDOM = new Random();

    public Map<List<String>, Menu> loadMenu(JavaPlugin javaPlugin) {
        HashMap<List<String>, Menu> menu = new HashMap<List<String>, Menu>();
        for (File menuFile : Arrays.stream(javaPlugin.getDataFolder().listFiles()).filter(file -> file.getName().endsWith(".yml")).collect(Collectors.toList())) {
            if (!menuFile.getName().endsWith(".yml")) {
                throw new RuntimeException("Invalid menu file: " + menuFile.getName());
            }
            YamlConfiguration menuConfig = YamlConfiguration.loadConfiguration(menuFile);
            String menuName = menuFile.getName().split("\\.")[0];
            List<String> commands = menuConfig.getStringList("commands");
            if (commands == null || commands.isEmpty()) {
                throw new RuntimeException("Commands in menu " + menuName + " is null or empty ");
            }
            MenuBuilder menuBuilder = MenuBuilder.builder();
            if (menuConfig.getString("title") != null) {
                menuBuilder.setTitle(menuConfig.getString("title"));
            }
            if (menuConfig.getString("open_sound") != null) {
                menuBuilder.setOpenAction(player -> GamerManager.getGamer(player).playSound(SoundType.valueOf(menuConfig.getString("open_sound").toUpperCase())));
            }
            if (menuConfig.getString("close_sound") != null) {
                menuBuilder.setCloseAction(player -> GamerManager.getGamer(player).playSound(SoundType.valueOf(menuConfig.getString("close_sound").toUpperCase())));
            }
            List<String> rows = menuConfig.getStringList("rows");
            menuBuilder.setRows(rows.size());
            HashMap<Character, MenuItemBuilder> itemMap = new HashMap<Character, MenuItemBuilder>();
            ConfigurationSection itemsSection = menuConfig.getConfigurationSection("items");
            for (String key : itemsSection.getValues(false).keySet()) {
                itemMap.put(key.charAt(0), MenuLoader.buildMenuItem(menuName, itemsSection, key));
            }
            int slot = 0;
            for (String row : rows) {
                for (char item : row.toCharArray()) {
                    MenuItemBuilder menuItemBuilder;
                    if (item != '0' && (menuItemBuilder = itemMap.get(item)) != null) {
                        menuItemBuilder.setSlot(slot);
                        menuBuilder.addItem(menuItemBuilder.build());
                    }
                    ++slot;
                }
            }
            menu.put(commands, menuBuilder.build());
        }
        return menu;
    }

    private MenuItemBuilder buildMenuItem(String menuName, ConfigurationSection itemsSection, String key) {
        ConfigurationSection attributes = itemsSection.getConfigurationSection(key);
        MenuItemBuilder menuItemBuilder = MenuItemBuilder.builder();
        ItemBuilder itemBuilder = ItemBuilder.builder(attributes.getString("id"));
        itemBuilder.removeFlags();
        List<String> enchantments = attributes.getStringList("enchantments");
        if (enchantments != null) {
            enchantments.forEach(enchantment -> itemBuilder.addEnchantment(Enchantment.getByName(enchantment.split(":")[0].toUpperCase()), Integer.parseInt(enchantment.split(":")[1])));
        }
        if (attributes.getInt("amount") > 0) {
            itemBuilder.setAmount(attributes.getInt("amount"));
        }
        if (attributes.contains("color")) {
            String[] rgb = attributes.getString("color").split(",");
            itemBuilder.setColor(Color.fromRGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
        }

        String name = attributes.getString("name");
        List<String> lore = attributes.getStringList("lore");
        if (name != null && MenuUtil.containsPlaceholders(name) || lore != null && MenuUtil.containsPlaceholders(lore)) {
            menuItemBuilder.setDynamicItemStack(gamer -> {
                if (name != null) {
                    itemBuilder.setName(MenuUtil.applyPlaceholders(gamer, name));
                }
                if (lore != null) {
                    itemBuilder.setLore(MenuUtil.applyPlaceholders(gamer, lore));
                }
                return itemBuilder.build();
            });
        } else {
            if (name != null) {
                itemBuilder.setName(name);
            }
            if (lore != null) {
                itemBuilder.setLore(lore);
            }
            menuItemBuilder.setItemStack(itemBuilder.build());
        }
        List<String> commands = attributes.getStringList("commands");
        if (commands != null && !commands.isEmpty()) {
            Group group = attributes.getString("group") == null ? Group.DEFAULT : Group.getGroupByName(attributes.getString("group"));
            boolean onlyGroup = attributes.getBoolean("only_group");
            int coinsPrice = attributes.getInt("coins_price");
            int goldPrice = attributes.getInt("gold_price");
            int moneyPrice = attributes.getInt("money_price");
            int cooldown = attributes.getInt("cooldown") == 0 ? 20 : attributes.getInt("cooldown") * 20;
            String cooldownType = attributes.getString("cooldown_type") == null ? menuName.toUpperCase() + "_MENU_GLOBAL" : attributes.getString("cooldown_type");
            boolean randomCommand = attributes.getBoolean("random_command");
            menuItemBuilder.setClickAction((player, clickType, i) -> {
                BukkitGamer gamer = GamerManager.getGamer(player);
                if (gamer == null)
                    return;
                
                if (cooldown <= 20) {
                    if (Cooldown.hasCooldown(player.getName(), cooldownType)) {
                        return;
                    }
                } else if (Cooldown.hasCooldown(player.getName(), cooldownType)) {
                    gamer.sendMessageLocale("MENU_COOLDOWN", Cooldown.getCooldownLeft(player.getName(), cooldownType));
                    return;
                }
                Cooldown.addCooldown(player.getName(), cooldownType, cooldown);
                if (!onlyGroup) {
                    if (!gamer.hasChildGroup(group)) {
                        gamer.sendMessageLocale("MENU_MINIMAL_GROUP", group.getChatPrefix());
                        return;
                    }
                } else if (gamer.getGroup() != group) {
                    gamer.sendMessageLocale("MENU_ONLY_GROUP", group.getChatPrefix());
                    return;
                }
                if (economyEnabled && moneyPrice > 0) {
                    if (EconomyHook.getBalance(player) < moneyPrice) {
                        gamer.sendMessageLocale("MENU_MONEY_PRICE", moneyPrice);
                        return;
                    }
                    EconomyHook.withdrawPlayer(player, moneyPrice);
                    gamer.sendMessageLocale("MENU_MONEY_PAY", moneyPrice);
                }
                if (coinsPrice > 0) {
                    if (gamer.getCoins() < coinsPrice) {
                        gamer.sendMessageLocale("MENU_COINS_PRICE", coinsPrice);
                        return;
                    }
                    gamer.setCoins(gamer.getCoins() - coinsPrice, true);
                    gamer.sendMessageLocale("MENU_COINS_PAY", coinsPrice);
                }
                if (goldPrice > 0) {
                    if (gamer.getGold() < goldPrice) {
                        gamer.sendMessageLocale("MENU_GOLD_PRICE", goldPrice);
                        return;
                    }
                    gamer.setGold(gamer.getGold() - goldPrice, true);
                    gamer.sendMessageLocale("MENU_GOLD_PAY", goldPrice);
                }
                Cooldown.addCooldown(player.getName(), cooldownType, cooldown);
                MenuUtil.applyCommands(gamer, randomCommand ? Collections.singletonList(commands.get(RANDOM.nextInt(commands.size()))) : commands);
            });
        }
        return menuItemBuilder;
    }
}

