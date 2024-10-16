package ru.galaxy773.multiplatform.api.gamer.constants;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Group {

    DEFAULT(950, 1,1.0, 3, 5, 0, "default", "\u00A78\u0418\u0433\u0440\u043E\u043A \u00A7f", "\u00A78\u0418\u0433\u0440\u043E\u043A \u00A7f"),
    WARRIOR(940, 2,1.25, 5, 10, 25, "warrior", "\u00A7e\u00A7l\u0412\u041E\u0418\u041D \u00A7f", "\u00A7e\u00A7l\u0412\u041E\u0418\u041D \u00A7f"),
    LORD(90, 3,1.5, 7, 15, 49, "lord", "\u00A7a\u00A7l\u041B\u041E\u0420\u0414 \u00A7f", "\u00A7a\u00A7l\u041B\u041E\u0420\u0414 \u00A7f"),
    KING(800, 4,1.75, 10, 20, 99, "king", "\u00A76\u00A7l\u041A\u041E\u0420\u041E\u041B\u042C \u00A7f", "\u00A76\u00A7l\u041A\u041E\u0420\u041E\u041B\u042C \u00A7f"),
    LEGEND(700, 5,2.0, 15, 25, 199, "legend", "\u00A7b\u00A7l\u041B\u0415\u0413\u0415\u041D\u0414\u0410 \u00A7f", "\u00A7b\u00A7l\u041B\u0415\u0413\u0415\u041D\u0414\u0410 \u00A7f"),
    WITHER(600, 6,2.25, 20, 30, 289, "wither", "\u00A7c\u00A7l\u0412\u0418\u0417\u0415\u0420 \u00A7f", "\u00A7c\u00A7l\u0412\u0418\u0417\u0415\u0420 \u00A7f"),
    DRAGON(500, 7,2.5, 30, 35, 479, "dragon", "\u00A79\u00A7l\u0414\u0420\u0410\u0413\u041E\u041D \u00A7f", "\u00A79\u00A7l\u0414\u0420\u0410\u0413\u041E\u041D \u00A7f"),
    ARES(400, 8,2.75, 40, 40, 949, "ares", "\u00A7d\u00A7l\u0410\u0420\u0415\u0421 \u00A7f", "\u00A7d\u00A7l\u0410\u0420\u0415\u0421 \u00A7f"),
    CHRONOS(300, 9,3.0, 50, 50, 1479, "chronos", "\u00A74\u00A7l\u0425\u0420\u041E\u041D\u041E\u0421 \u00A7f", "\u00A74\u00A7l\u0425\u0420\u041E\u041D\u041E\u0421 \u00A7f"),
    HELPER(200, 10,1.0, 50,50, -1, "helper", "\u00A72\u00A7l\u0425\u0415\u041B\u041F\u0415\u0420 \u00A7f", "\u00A72\u00A7l\u0425\u0415\u041B\u041F\u0415\u0420 \u00A7f"),
    MODER(100, 11,1.0, 50, 50, -1, "moder", "\u00A73\u00A7l\u041C\u041E\u0414\u0415\u0420 \u00A7f", "\u00A73\u00A7l\u041C\u041E\u0414\u0415\u0420 \u00A7f"),
    ADMIN(0, 12,1.0, 50, 50, -1, "admin", "\u00A74\u00A7l\u0410\u0414\u041C\u0418\u041D \u00A7f", "\u00A74\u00A7l\u0410\u0414\u041C\u0418\u041D \u00A7f");


    private final int priority;
    private final int level;
    private final double multiple;
    private final int friendsLimit;
    private final int ignoresLimit;
    private final int price;
    private final String groupName;
    private final String tagPrefix;
    private final String chatPrefix;

    private static final TIntObjectMap<Group> GROUPS = new TIntObjectHashMap<>();

    public static Group getGroupByName(String groupName) {
        return Arrays.stream(values())
                .filter(group1 -> group1.getGroupName().equals(groupName))
                .findFirst().orElse(DEFAULT);
    }

    public static Group getGroupByLevel(int level) {
        return GROUPS.size() < level ? GROUPS.get(GROUPS.size()) : GROUPS.get(level);
    }

    static {
        Arrays.stream(values()).forEach(group -> GROUPS.put(group.getLevel(), group));
    }
}
