package ru.galaxy773.cosmetics.api;

import lombok.experimental.UtilityClass;
import ru.galaxy773.cosmetics.api.manager.CosmeticManager;
import ru.galaxy773.cosmetics.manager.CraftCosmeticManager;

@UtilityClass
public final class CosmeticsAPI {

    private final CosmeticManager COSMETIC_MANAGER = new CraftCosmeticManager();

    public CosmeticManager getCosmeticManager() {
        return COSMETIC_MANAGER;
    }
}

