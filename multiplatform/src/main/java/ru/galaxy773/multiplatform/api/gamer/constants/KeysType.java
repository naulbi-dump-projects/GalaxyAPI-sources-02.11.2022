package ru.galaxy773.multiplatform.api.gamer.constants;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.galaxy773.multiplatform.api.boxes.AnimationType;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum KeysType {

    DONATE(1, "Донат кейс", AnimationType.CIRCLE),
    COINS(2, "Кейс с крышками", AnimationType.CONUS),
    GOLD(3, "Кейс с золотом", AnimationType.CONUS),
    SURVIVAL_SOULS(4, "Кейс с духами", AnimationType.CIRCLE),
    TITULS(5, "Кейс с титулами", AnimationType.CONUS),
    SURVIVAL_ITEMS(6, "Кейс с предметами", AnimationType.CONUS),
    SURVIVAL_MONEY(7, "Кейс с монетами", AnimationType.CONUS),
    ONEBLOCK_ITEMS(8, "Кейс с предметами", AnimationType.CONUS),
    ONEBLOCK_MONEY(9, "Кейс с монетами", AnimationType.CONUS),
    ONEBLOCK_SOULS(10, "Кейс с духами", AnimationType.CIRCLE);

    private final int id;
    private final String name;
    private final AnimationType animationType;

    private static final TIntObjectMap<KeysType> KEYS = new TIntObjectHashMap<>();

    public static KeysType getKeyType(int id) {
        return KEYS.get(id);
    }

    public static KeysType[] getGlobalKeys() {
        return new KeysType[] {
                DONATE, COINS, GOLD, TITULS
        };
    }

    static {
        Arrays.stream(values()).forEach(keyType -> KEYS.put(keyType.getId(), keyType));
    }
}
