package ru.galaxy773.bukkit.api.utils.chat;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;
import java.util.regex.Pattern;

@UtilityClass
public class ChatUtil {

    private final Pattern CHAT_COLOR_PATTERN = Pattern.compile("(?i)&([0-9A-F])");
    private final Pattern CHAT_OTHER_PATTERN = Pattern.compile("(?i)&([K-R])");

    public BaseComponent getComponentFromList(List<String> list) {
        int size = list.size();
        TextComponent components = new TextComponent();
        for (int i = 0; i < size; i++) {
            components.addExtra(list.get(i));
            if (i + 1 < size) {
                components.addExtra("\n");
            }
        }
        return components;
    }

    public String translateColorCodes(String text) {
        return CHAT_COLOR_PATTERN.matcher(text).replaceAll("§$1");
    }

    public String translateOtherCodes(String text) {
        return CHAT_OTHER_PATTERN.matcher(text).replaceAll("§$1");
    }
}

