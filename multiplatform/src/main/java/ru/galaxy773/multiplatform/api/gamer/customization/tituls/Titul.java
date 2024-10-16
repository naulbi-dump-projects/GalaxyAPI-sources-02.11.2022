package ru.galaxy773.multiplatform.api.gamer.customization.tituls;

import ru.galaxy773.multiplatform.api.gamer.customization.Rarity;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;

import java.util.List;

public interface Titul {

    String getTitul();

    Rarity getRarity();

    List<String> getLore();

    boolean buy(BaseGamer gamer);

    String getBuyErrorMessage(BaseGamer gamer);
}
