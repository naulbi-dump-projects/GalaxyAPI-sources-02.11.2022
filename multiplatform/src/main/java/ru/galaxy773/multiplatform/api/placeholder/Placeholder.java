package ru.galaxy773.multiplatform.api.placeholder;

import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;

public interface Placeholder {

    PlaceholderType getType();

    String getName();

    String apply(String input);

    String applyGamer(IBaseGamer gamer, String input);

    String applyArgumented(String var, String input);
}
