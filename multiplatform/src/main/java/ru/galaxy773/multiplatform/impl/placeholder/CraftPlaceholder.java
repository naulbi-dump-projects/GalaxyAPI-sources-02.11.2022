package ru.galaxy773.multiplatform.impl.placeholder;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.placeholder.Placeholder;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderType;

import java.util.function.Function;
import java.util.function.Supplier;

public class CraftPlaceholder implements Placeholder {

    @Getter
    private final PlaceholderType type;
    @Getter
    private final String name;
    private Supplier<String> supplier;
    private Function<String, String> function;

    public CraftPlaceholder(String name, Supplier<String> action) {
        this.type = PlaceholderType.SUPPLIER;
        this.name = name;
        this.supplier = action;
    }

    public CraftPlaceholder(String name, Function<String, String> action) {
        this.type = PlaceholderType.ARGUMENTED;
        this.name = name;
        this.function = action;
    }

    @Override
    public String apply(String input) {
        return input.replace(this.name, this.supplier.get());
    }

    @Override
    public String applyGamer(IBaseGamer gamer, String input) {
        return input;
    }

    @Override
    public String applyArgumented(String var, String input) {
        return input.replace(this.name + var + "%", this.function.apply(var));
    }
}
