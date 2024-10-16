package ru.galaxy773.multiplatform.impl.placeholder;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.placeholder.Placeholder;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderType;

import java.util.function.Function;
import java.util.function.Supplier;

public class CraftGamerPlaceholder implements Placeholder {

    @Getter
    private final PlaceholderType type;
    @Getter
    private final String name;
    private Supplier<String> supplier;
    private final Function<IBaseGamer, String> function;

    public CraftGamerPlaceholder(String name, Function<IBaseGamer, String> action) {
        this.type = PlaceholderType.GAMER;
        this.name = name;
        this.function = action;
    }

    @Override
    public String apply(String input) {
        return input.replace(this.name, this.supplier.get());
    }

    @Override
    public String applyGamer(IBaseGamer gamer, String input) {
        return input.replace(this.name, this.function.apply(gamer));
    }

    @Override
    public String applyArgumented(String var, String input) {
        return input;
    }
}
