package ru.galaxy773.multiplatform.impl.placeholder;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.placeholder.Placeholder;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderAPI;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderType;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class PlaceholderAPIImpl implements PlaceholderAPI {

    @Getter
    private final Map<PlaceholderType, List<Placeholder>> placeholders = new EnumMap<>(PlaceholderType.class);

    public PlaceholderAPIImpl() {
        Arrays.stream(PlaceholderType.values()).forEach(placeholderType -> placeholders.put(placeholderType, new ArrayList<>()));
        //regsiterPlaceholder("%online%", () -> String.valueOf(Bukkit.getOnlinePlayers().size()));
        //registerArgumentedPlaceholder("%online:", s -> StringUtil.getNumberFormat(BungeeUtil.getOnline(s.split(","))));
        //registerGamerPlaceholder("%chatprefix%", BaseGamer::getChatPrefix);
        //registerGamerPlaceholder("%tabprefix%", Gamer::getTagPrefix);
        //registerGamerPlaceholder("%player%", Gamer::getName);
        //registerGamerPlaceholder("%coins%", gamer -> StringUtil.getNumberFormat(gamer.getCoins()));
        //registerGamerPlaceholder("%gold%", gamer -> StringUtil.getNumberFormat(gamer.getGold()));
        //registerGamerPlaceholder("%level%", gamer -> String.valueOf(gamer.getLevel()));
        //registerGamerPlaceholder("%money%", gamer -> StringUtil.getNumberFormat(EconomyUtil.getBalance(gamer.getPlayer())));
    }

    @Override
    public String apply(String input) {
        for (Placeholder placeholder : this.placeholders.get(PlaceholderType.SUPPLIER)) {
            if (input.contains(placeholder.getName())) {
                input = placeholder.apply(input);
            }
        }
        return input;
    }

    @Override
    public String applyArgumented(String input) {
        for (Placeholder placeholder : this.placeholders.get(PlaceholderType.ARGUMENTED)) {
            if (input.contains(placeholder.getName())) {
                input = placeholder.applyArgumented(input.split(placeholder.getName())[1].split("%")[0], input);
            }
        }
        return input;
    }

    @Override
    public String applyGamer(IBaseGamer gamer, String input) {
        for (Placeholder placeholder : this.placeholders.get(PlaceholderType.GAMER)) {
            if (input.contains(placeholder.getName())) {
                input = placeholder.applyGamer(gamer, input);
            }
        }
        return input;
    }

    @Override
    public String applyAll(IBaseGamer gamer, String input) {
        return applyArgumented(applyGamer(gamer, apply(input)));
    }

    @Override
    public void registerPlaceholder(String name, Supplier<String> action) {
        this.placeholders.get(PlaceholderType.SUPPLIER).add(new CraftPlaceholder(name, action));
    }

    @Override
    public void registerArgumentedPlaceholder(String name, Function<String, String> action) {
        this.placeholders.get(PlaceholderType.ARGUMENTED).add(new CraftPlaceholder(name, action));
    }

    @Override
    public void registerGamerPlaceholder(String name, Function<IBaseGamer, String> action) {
        this.placeholders.get(PlaceholderType.GAMER).add(new CraftGamerPlaceholder(name, action));
    }
}
