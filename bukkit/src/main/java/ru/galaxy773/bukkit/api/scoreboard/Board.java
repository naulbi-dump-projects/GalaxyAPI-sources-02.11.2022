package ru.galaxy773.bukkit.api.scoreboard;

import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.Map;

public interface Board {
    void showTo(Player player);

    Map<Integer, BoardLine> getLines();
    int getSize();
    Player getOwner();

    void setDisplayName(String name);
    void setDynamicDisplayName(String name);
    void setDynamicDisplayName(Collection<String> lines, long speed);

    void setDynamicLine(int number, String notChangeText, String change);
    void setLine(int number, String text);

    void updater(Runnable runnable, long speed);
    void updater(Runnable runnable);
    void removeUpdater(Runnable runnable);

    void removeLine(int number);

    void remove();
}
