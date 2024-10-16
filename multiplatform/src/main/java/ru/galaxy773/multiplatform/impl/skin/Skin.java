package ru.galaxy773.multiplatform.impl.skin;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.galaxy773.multiplatform.api.skin.SkinType;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Skin {

    public static final Skin DEFAULT_SKIN = createSkin("ewogICJ0aW1lc3RhbXAiIDogMTYyMzc1NDg3MzMzOCwKICAicHJvZmlsZUlkIiA6ICI3YjdkNjc2ZGExZWQ0NDEwODE4MjA5NTg1ODdhYWU0NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaXhNaW5lX3J1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc2YmQxZDExOTRlNzg0ZWE2YmIxOWRmOTg3MTk5YmU0N2ZhMjA3MDBkZTBiODUyMGNhZmU0ZTFiOTRmMWMyNDMiCiAgICB9CiAgfQp9", "CqPqUoe0Z7CVkzW6xS0zxuVfyYOIqkm1qn36tpfdX14bD1oWh+CXyfzqusMWqTVzmZwCQjUEhUAVZAOGKbXOdCerSeG+q+WHeWQL47iToUqpM6G3higoL+iG6RTcl9S/FHOvlbQ7/jX1lZT0IPRhob86ka5evkdKAFOVKNt8rknLWK2LGolHeQ427ix+uOSLArJVhsIYaBms5FfmXdLD8nCIFvG0XDbeS87TBFabrHE92t6zLonQMLd97Ps6lFMSmVVahKl5NgboM8Cz26v5CVxWZUdbFQEJ0xJHiyOD1Y9VLc/Dj4TEXhswZMDVx4+13Xv6WcClWOSrs8QDwrgTgH+uEB1vfFNHxujl+eIRC2fwhDC4RobzYfFw4Etat2Eth5Saan9ypwZa13dxnerv9nC2dbNZZDpgThced19p027t3kZ2RCmpB5IVOgejsm3P6s9nDH6i5pfRgvSMfwwJaljbqiy93UY2SGfhqe9sHjreu2uEac+KuRXcSpnSyTtyo/omyVYEHDci8pcpR+Wp24h8VG8Wkz9l5oGoFd0kY2DIY7pzV+wCi1myrDQ4epgOn73BYMp7dZFFvVr9rs8+0a+EctIMYxo1swi8NjYXEINGM4nNGB8mJkCfOTY50G4z7jI88+4N/fAxFVglPbWJOt2IhJ6sRQ1JVrQFs8tDN08=");

    private final String skinName;
    private final String value;
    private final String signature;
    private final SkinType skinType; //todo сделать, чтобы в бд хранился тип скина игрока

    public static Skin createSkin(String value, String signature) {
        return new Skin("SKIN_DEFAULT", value, signature, SkinType.CUSTOM);
    }
}
