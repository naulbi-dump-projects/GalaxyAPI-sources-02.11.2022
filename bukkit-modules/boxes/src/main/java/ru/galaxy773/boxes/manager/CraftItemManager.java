package ru.galaxy773.boxes.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.boxes.api.ItemManager;
import ru.galaxy773.boxes.type.CoinsBoxReward;
import ru.galaxy773.boxes.type.DonateBoxReward;
import ru.galaxy773.boxes.type.GoldBoxReward;
import ru.galaxy773.boxes.type.TitulsBoxReward;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemUtil;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.api.gamer.customization.Rarity;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulsCategory;

import java.util.*;

public class CraftItemManager implements ItemManager {

    private final Map<KeysType, List<BoxReward>> rewards = new HashMap<>();

    public CraftItemManager() {
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGNiZjU1ODY4MzZiN2IzNDI5MzJlMWQyM2VmYzI0OTBjZjU5YzY5YWNjZjFlMDVlOWVkNTc2Y2FlZDhiNzg3NyJ9fX0="), 15.0, Group.WARRIOR));
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxNDQ5NTdjMmM1YmJmZTQ0N2Y0YjJkMzZhMjQ2ZWExYjAyM2RhNGNiZDFhYTJkYmJiMTVlOTQ5ODEyNDgifX19"), 18.0, Group.LORD));
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU3MTFkOTU0YmQ1NjIzNmQxYTlmOTliZTg4MGM1ZDM4OTkwZGY2YmVmNzJlNzNmNzQ1YjA0OTk1ZGJmNiJ9fX0="), 20.0, Group.KING));
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIxY2RlYzJjZjRlYmVlZjM1ZDU4YjE4NGI4MzI1OThiYzg5MGEwYWU1YzJkNTRlZTliZTU4NmQwIn19fQ=="), 17.0, Group.LEGEND));
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTk5OTQwNDA0MzNhZjBmMDE1YmU0ZDY5NjhjM2Q1NWUwNDRjOThkYWMyYzRjNmE2ZWEwZWZhYzdhNmRkYiJ9fX0="), 11.0, Group.WITHER));
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQzMDk2NmQ1Y2ViYmQ3ODcxNDc2OTlhMjk3NDM3NTFiM2NlNGJiODE0ZTJkYjU2NGZlOTIxNDJkMTE5Y2QxIn19fQ=="), 9.0, Group.DRAGON));
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlmZTI5MTY3NmM3YjNmOTZmM2NkZjYzYzQ3YjUzZmI0NWQ3M2JkNmZlMmNlMjJkZTEwNzQ5ZWIxNDI2YSJ9fX0="), 5.0, Group.ARES));
        this.addBoxReward(KeysType.DONATE, new DonateBoxReward(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjUzNGM4YWZiNDgwOTZlZDg2ZjU3MjU1MDk3ZWFkN2EwMzkyN2Y4NTFlNTZiNTQ5NGI1YjcwM2UzNWIwYzA4NyJ9fX0="), 3.0, Group.CHRONOS));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 20.0, 10));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 20.0, 25));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 20.0, 50));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 15.0, 75));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 9.0, 100));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 7.0, 150));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 6.0, 200));
        this.addBoxReward(KeysType.COINS, new CoinsBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 3.0, 250));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 20.0, 500));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 20.0, 750));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 20.0, 1000));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 15.0, 1500));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 9.0, 2000));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 7.0, 2500));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 6.0, 5000));
        this.addBoxReward(KeysType.GOLD, new GoldBoxReward(new ItemStack(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT")), 3.0, 10000));
        Map<TitulsCategory, ItemStack> icons = new EnumMap<>(TitulsCategory.class);
        icons.put(TitulsCategory.BASE, new ItemStack(Material.KNOWLEDGE_BOOK));
        icons.put(TitulsCategory.SPECIAL, new ItemStack(Material.NETHER_STAR));
        icons.put(TitulsCategory.NEW_YEAR, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JiZThmZDFhYTM5ZjE1MDc2ZTg4NGRmZTZkZGI5YTNmMzc2MWRiMzFlMmIxZjk5NDBiNWRmZDM0ZDFjNGQifX19"));
        icons.put(TitulsCategory.ANIME, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM1MTRlY2EwMzVkOTc0MThlZGQ3NWVmMTBkNDZhYzQ2ZjQzY2Y0YmQ2ZmJlOTI1M2U3YTY2ZWRiOTIwMzk2In19fQ=="));
        icons.put(TitulsCategory.MARVEL, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjUyY2E5NzgzNGUwMTQ1ODY5MWM5MDU0NDU3ODZkMTI2MmQ4NDM2NjNkNjEyOGYzYmMwYzUzMTliOGE2OWU0In19fQ=="));
        Arrays.stream(TitulType.values())
                .skip(1)
                .filter(titul -> titul.getTitul().getRarity() == Rarity.LEGENDARY)
                .forEach(titul -> this.addBoxReward(KeysType.TITULS,
                        new TitulsBoxReward(icons.get(titul.getCategory()), 5.88, titul)));
    }

    @Override
    public List<BoxReward> getRewards(KeysType keysType) {
        return this.rewards.get(keysType);
    }

    @Override
    public void addBoxReward(KeysType keysType, BoxReward boxReward) {
        if (!this.rewards.containsKey(keysType)) {
            this.rewards.put(keysType, new ArrayList<>());
        }
        this.rewards.get(keysType).add(boxReward);
    }

    @Override
    public void removeBoxReward(KeysType keysType, BoxReward boxReward) {
        if (this.rewards.containsKey(keysType)) {
            this.rewards.get(keysType).remove(boxReward);
            if (this.rewards.get(keysType).isEmpty()) {
                this.rewards.remove(keysType);
            }
        }
    }

    @Override
    public void removeBoxRewards(KeysType keysType) {
        this.rewards.remove(keysType);
    }
}

