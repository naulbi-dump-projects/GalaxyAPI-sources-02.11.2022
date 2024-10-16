package ru.galaxy773.multiplatform.api.gamer.customization.tituls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.customization.Rarity;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;

import java.util.List;

@AllArgsConstructor
@Getter
public class AchievementTitul implements Titul {

    private final String titul;
    private final Rarity rarity;
    private final String loreKey;

    @Override
    public List<String> getLore() {
        return Lang.getList(this.loreKey);
    }

    @Override
    public boolean buy(BaseGamer gamer) {
        return false;
    }

    @Override
    public String getBuyErrorMessage(BaseGamer gamer) {
        return Lang.getMessage("TITUL_ONLY_ACHIEVEMENT");
    }
}
