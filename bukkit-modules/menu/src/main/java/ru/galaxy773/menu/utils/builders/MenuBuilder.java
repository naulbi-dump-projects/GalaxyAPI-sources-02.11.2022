package ru.galaxy773.menu.utils.builders;

import org.bukkit.entity.Player;
import ru.galaxy773.menu.api.menu.Menu;
import ru.galaxy773.menu.api.menu.MenuItem;
import ru.galaxy773.menu.object.CraftMenu;
import ru.galaxy773.multiplatform.api.utils.builders.Builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MenuBuilder implements Builder<Menu> {

    private String title = "";
    private int rows = 5;
    private Consumer<Player> openAction;
    private Consumer<Player> closeAction;
    private Set<MenuItem> items = new HashSet<>();

    public static MenuBuilder builder() {
        return new MenuBuilder();
    }

    public MenuBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MenuBuilder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public MenuBuilder setOpenAction(Consumer<Player> openAction) {
        this.openAction = openAction;
        return this;
    }

    public MenuBuilder setCloseAction(Consumer<Player> closeAction) {
        this.closeAction = closeAction;
        return this;
    }

    public MenuBuilder addItem(MenuItem item) {
        this.items.add(item);
        return this;
    }

    public MenuBuilder addItems(Collection<MenuItem> items) {
        items.addAll(items);
        return this;
    }

    public MenuBuilder clone() {
        MenuBuilder menuBuilder = MenuBuilder.builder();
        menuBuilder.title = this.title;
        menuBuilder.items = new HashSet<MenuItem>(menuBuilder.items);
        menuBuilder.rows = this.rows;
        menuBuilder.openAction = this.openAction;
        menuBuilder.closeAction = this.closeAction;
        return menuBuilder;
    }

    @Override
    public Menu build() {
        CraftMenu menu = new CraftMenu(this.title, this.rows, this.items);
        if (this.openAction != null) {
            menu.setOpenAction(this.openAction);
        }
        if (this.closeAction != null) {
            menu.setCloseAction(this.closeAction);
        }
        return menu;
    }
}

