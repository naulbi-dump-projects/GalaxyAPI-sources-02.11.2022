package ru.galaxy773.multiplatform.api.gamer.constants;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Version {

    EMPTY(-1),

    MINECRAFT_1_8(47),

    MINECRAFT_1_9(107),
    MINECRAFT_1_9_1(108),
    MINECRAFT_1_9_2(109),
    MINECRAFT_1_9_3_AND_1_9_4(110),

    MINECRAFT_1_10(210),

    MINECRAFT_1_11(315),
    MINECRAFT_1_11_1_AND_1_11_2(316),

    MINECRAFT_1_12(335),
    MINECRAFT_1_12_1(338),
    MINECRAFT_1_12_2(340),

    MINECRAFT_1_13(393),
    MINECRAFT_1_13_1(401),
    MINECRAFT_1_13_2(404),

    MINECRAFT_1_14(477),
    MINECRAFT_1_14_1(480),
    MINECRAFT_1_14_2(485),
    MINECRAFT_1_14_3(490),
    MINECRAFT_1_14_4(498),

    MINECRAFT_1_15(573),
    MINECRAFT_1_15_1(575),
    MINECRAFT_1_15_2(578),

    MINECRAFT_1_16(735),
    MINECRAFT_1_16_1(736),
    MINECRAFT_1_16_2(751),
    MINECRAFT_1_16_3(753),
    MINECRAFT_1_16_45(754),

    MINECRAFT_1_17(755),
    MINECRAFT_1_17_1(756);

    @Getter
    private final int version;

    //todo: no entry value
    private static final TIntObjectMap<Version> VERSIONS = new TIntObjectHashMap<>();

    public static Version getVersion(int protocol) {
        Version version = VERSIONS.get(protocol);
        if (version == null) {
            version = EMPTY;
        }
        return version;
    }

    static {
        Arrays.stream(values()).forEach(version -> VERSIONS.put(version.getVersion(), version));
    }
}
