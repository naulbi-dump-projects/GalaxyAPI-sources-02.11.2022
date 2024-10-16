package ru.galaxy773.multiplatform.api.placeholder;

import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public interface PlaceholderAPI {

    Map<PlaceholderType, List<Placeholder>> getPlaceholders();

    String apply(String input);

    String applyArgumented(String input);

    String applyGamer(IBaseGamer baseGamer, String input);

    String applyAll(IBaseGamer baseGamer, String input);

    void registerPlaceholder(String name, Supplier<String> action);

    void registerArgumentedPlaceholder(String name, Function<String, String> action);

    void registerGamerPlaceholder(String name, Function<IBaseGamer, String> action);
}
