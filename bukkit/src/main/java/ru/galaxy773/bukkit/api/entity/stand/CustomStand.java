package ru.galaxy773.bukkit.api.entity.stand;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import ru.galaxy773.bukkit.api.entity.PacketEntityLiving;
import ru.galaxy773.bukkit.api.utils.math.CraftVector;

public interface CustomStand extends PacketEntityLiving {
	
	JavaPlugin getPlugin();

    boolean isSmall();
    boolean hasArms();
    boolean hasBasePlate();
    boolean isInvisible();

    boolean hasPassenger();
    void removePassenger();
    void setItemPassenger(ItemStack itemPassenger);

    void setSmall(boolean small);
    void setArms(boolean arms);
    void setBasePlate(boolean basePlate);
    void setInvisible(boolean invisible);

    void setCustomName(String name);

    void setHeadPose(CraftVector vector);
    void setBodyPose(CraftVector vector);
    void setLeftArmPose(CraftVector vector);
    void setRightArmPose(CraftVector vector);
    void setRightLegPose(CraftVector vector);
    void setLeftLegPose(CraftVector vector);

    void setHeadPose(EulerAngle pose);
    void setBodyPose(EulerAngle pose);
    void setLeftArmPose(EulerAngle pose);
    void setRightArmPose(EulerAngle pose);
    void setRightLegPose(EulerAngle pose);
    void setLeftLegPose(EulerAngle pose);

    EulerAngle getHeadPose();
    EulerAngle getBodyPose();
    EulerAngle getLeftArmPose();
    EulerAngle getRightArmPose();
    EulerAngle getRightLegPose();
    EulerAngle getLeftLegPose();
}
