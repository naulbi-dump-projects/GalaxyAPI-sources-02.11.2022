package ru.galaxy773.bukkit.api.tops.hologram;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.lines.TextHoloLine;
import ru.galaxy773.bukkit.api.tops.HologramAnimation;

import java.util.List;

public abstract class HoloTop {

	private final Hologram hologram;
	private final List<String> topHeader;
	public List<HoloTopData> topData;

	//todo \u043F\u0435\u0440\u0435\u043F\u0438\u0441\u0430\u0442\u044C \u044D\u0442\u043E\u0442 \u043E\u0431\u043E\u0441\u0441\u0430\u043D\u044B\u0439 \u043A\u0443\u0441\u043E\u043A \u0433\u043E\u0432\u043D\u0430 \u0441\u0440\u0430\u0437\u0443 \u0436\u0435 \u043A\u0430\u043A \u043F\u043E\u044F\u0432\u0438\u0442\u0441\u044F \u0432\u0440\u0435\u043C\u044F
	protected HoloTop(JavaPlugin javaPlugin, Location location, List<String> topHeader, int updateMinutes) {
		this.hologram = BukkitAPI.getHologramAPI().createHologram(javaPlugin, location);
		this.topHeader = topHeader;
		this.hologram.addTextLine(topHeader);
		for(int index = 1; index < 11; index++) {
			char color = 'e';
			if(index == 1) {
				color = 'a';
			} else
			if(index == 2) {
				color = '6';
			} else
			if(index == 3) {
				color = 'c';
			}
			this.hologram.addTextLine("\u00A7" + color + "#" + index
					+ " \u00A78| \u00A7c" + "N/A \u0414\u0430\u043D\u043D\u044B\u0435 \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u044B"
					+ " \u00A78|\u00A7f   ");
		}
		this.hologram.addAnimationLine(20, new HologramAnimation(updateMinutes));
		this.hologram.setPublic(true);
	}

	public abstract void updateData();

	public void update() {
		updateData();
		int index = 1;
		int maxPlayerName = topData.stream().map(top -> ChatColor.stripColor(top.getPlayerName()))
				.mapToInt(String::length)
				.max()
				.orElse(16) + 1;
		int maxTop = topData.stream().map(top -> ChatColor.stripColor(top.getTopString()))
				.mapToInt(String::length)
				.max()
				.orElse(5);
		for(HoloTopData topData : topData) {
			char color = 'e';
			if(index == 1) {
				color = 'a';
			} else
			if(index == 2) {
				color = '6';
			} else
			if(index == 3) {
				color = 'c';
			}
			TextHoloLine holoLine = this.hologram.getHoloLine(index + this.topHeader.size() - 1);
			holoLine.setText("\u00A7" + color + "#" + topData.getPosition()
					+ " \u00A78| \u00A7f" + topData.getPlayerName() + "\u00A7f" + StringUtils.repeat(" ", maxPlayerName - ChatColor.stripColor(topData.getPlayerName()).length())
					+ "\u00A78| \u00A7" + color + topData.getTopString() + StringUtils.repeat(" ", maxTop - ChatColor.stripColor(topData.getTopString()).length()));
			index++;
		}
	}
}
