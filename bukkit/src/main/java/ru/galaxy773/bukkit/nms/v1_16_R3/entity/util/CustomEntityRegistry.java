package ru.galaxy773.bukkit.nms.v1_16_R3.entity.util;

import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.RegistryBlocks;
import net.minecraft.server.v1_16_R3.RegistryMaterials;

public class CustomEntityRegistry extends RegistryBlocks {

    private final RegistryMaterials<EntityTypes<?>> wrapped;

    public CustomEntityRegistry(RegistryBlocks<EntityTypes<?>> wrapped) {
        super(wrapped.a().getNamespace(), null, wrapped.b());
        this.wrapped = wrapped;
    }

    public int a(Object o) {
        return this.wrapped.a((EntityTypes<?>)o);
    }
}
