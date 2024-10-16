package ru.galaxy773.bukkit.api.entity.npc.types;

import org.bukkit.ChatColor;
import ru.galaxy773.bukkit.api.entity.npc.NPC;
import ru.galaxy773.multiplatform.impl.skin.Skin;

public interface HumanNPC extends NPC {

	Skin getSkin();
	
    void changeSkin(String value, String signature);
    void changeSkin(Skin skin);
    
    void setBed(boolean bed);
    boolean isLeavedBed();

    void setGlowing(ChatColor chatColor);
    
}
