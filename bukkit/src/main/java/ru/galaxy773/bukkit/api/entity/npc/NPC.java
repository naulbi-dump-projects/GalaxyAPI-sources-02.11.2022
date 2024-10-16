package ru.galaxy773.bukkit.api.entity.npc;

import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.PacketEntityLiving;

public interface NPC extends PacketEntityLiving {

	JavaPlugin getPlugin();
	
	//getInteractAction();
	
    String getName();

    boolean isHeadLook();

    void setHeadLook(boolean flag);

    void animation(AnimationNpcType animation);
    
    //void setInteractAction(ClickAction clickAction);
    
    NpcType getType();

    boolean isGlowing();

    void setGlowing(boolean glowing);
}
