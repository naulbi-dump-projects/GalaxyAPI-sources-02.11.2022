package ru.galaxy773.lobby.bossbar;

import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.function.Supplier;

import lombok.Getter;
import org.bukkit.boss.BossBar;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class BossBarReplaced
implements Supplier<String> {

    @Getter
    private final BossBar bossBar;
    private final Iterator<String> strings;

    public BossBarReplaced(BossBar bossBar) {
        this.bossBar = bossBar;
        this.strings = Iterators.cycle(Lang.getList("BOSS_BAR_LOBBY"));
    }

    @Override
    public String get() {
        return this.strings.next();
    }
}

