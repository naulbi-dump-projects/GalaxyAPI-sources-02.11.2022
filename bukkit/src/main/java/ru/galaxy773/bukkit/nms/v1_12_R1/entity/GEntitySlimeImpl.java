package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntitySlime;
import net.minecraft.server.v1_12_R1.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntitySlime;

public class GEntitySlimeImpl extends GEntityLivingBase<EntitySlime> implements GEntitySlime {

    public GEntitySlimeImpl(World world) {
        super(new EntitySlime(world));
    }

    @Override
    public int getSize() {
        return entity.getSize();
    }

    @Override
    public void setSize(int size) {
        entity.setSize(size, true);
    }

	@Override
	public void setInvisible(boolean invisible) {
		entity.setInvisible(invisible);
	}
}
