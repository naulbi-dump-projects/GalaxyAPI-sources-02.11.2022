package ru.galaxy773.multiplatform.api.gamer.customization;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PrefixColor {

    CHRONOS_1(1, "\u00A74", "\u041A\u0440\u0430\u0441\u043D\u044B\u0439"),
    CHRONOS_2(2, "\u00A7e", "\u0416\u0435\u043B\u0442\u044B\u0439"),
    CHRONOS_3(3, "\u00A7b", "\u0410\u043A\u0432\u0430"),
    CHRONOS_4(4, "\u00A7a", "\u0417\u0435\u043B\u0435\u043D\u044B\u0439"),
    CHRONOS_5(5, "\u00A79", "\u0413\u043E\u043B\u0443\u0431\u043E\u0439"),
    CHRONOS_6(6, "\u00A72", "\u0422\u0435\u043C\u043D\u043E \u0437\u0435\u043B\u0435\u043D\u044B\u0439"),
    CHRONOS_7(7, "\u00A76", "\u041E\u0440\u0430\u043D\u0436\u0435\u0432\u044B\u0439"),
    CHRONOS_8(8, "\u00A73", "\u0411\u0438\u0440\u044E\u0437\u043E\u0432\u044B\u0439"),
    CHRONOS_9(9, "\u00A7d", "\u0420\u043E\u0437\u043E\u0432\u044B\u0439"),
    CHRONOS_10(10, "\u00A75", "\u0424\u0438\u043E\u043B\u0435\u0442\u043E\u0432\u044B\u0439");

    private final int id;
    private final String colorCode;
    private final String name;

    public int getID() {
        return this.id;
    }

    private static final TIntObjectMap<PrefixColor> PREFIX_COLORS = new TIntObjectHashMap<>();
    public static PrefixColor getPrefixColor(int id) {
        return PREFIX_COLORS.get(id);
    }

    static {
        Arrays.stream(values()).forEach(prefixColor -> PREFIX_COLORS.put(prefixColor.getID(), prefixColor));
    }
}
