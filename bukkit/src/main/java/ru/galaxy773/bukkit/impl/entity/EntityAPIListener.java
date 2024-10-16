package ru.galaxy773.bukkit.impl.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginDisableEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.bukkit.impl.entity.customstand.StandManager;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;

public class EntityAPIListener extends GListener<BukkitPlugin> {

	private final StandManager standManager;
	private final NPCManager npcManager;
	
	protected EntityAPIListener(StandManager standManager, NPCManager npcManager) {
		super(BukkitPlugin.getInstance());
		this.standManager = standManager;
		this.npcManager = npcManager;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPluginDisable(PluginDisableEvent e) {
		standManager.getStands().values().forEach(stand -> {
			//не уверен, поэтому проверка, хоть и бесполезная по сути то
			if(stand != null) {
				if(stand.getPlugin().getName().equals(e.getPlugin().getName())) {
					stand.remove();
				}
			}
		});
		npcManager.getNPCs().values().forEach(npc -> {
			if(npc != null) {
				if(npc.getPlugin().getName().equals(e.getPlugin().getName())) {
					npc.remove();
				}
			}
		});
	}
}
