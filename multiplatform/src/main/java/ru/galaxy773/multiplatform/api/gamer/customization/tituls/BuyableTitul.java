package ru.galaxy773.multiplatform.api.gamer.customization.tituls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.customization.Rarity;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;

import java.util.List;

@AllArgsConstructor
@Getter
public class BuyableTitul implements Titul {

    private final String titul;
    private final Rarity rarity;
    private final String loreKey;
    private final int price;
    private final MoneyType moneyType;

    @Override
    public List<String> getLore() {
        return Lang.getList(this.loreKey, this.price);
    }

    @Override
    public boolean buy(BaseGamer gamer) {
        switch (this.moneyType) {
            case GOLD:
                if (gamer.getGold() >= this.price) {
                    gamer.setGold(gamer.getGold() - this.price, true);
                    return true;
                }
                break;
            case COINS:
                if (gamer.getCoins() >= this.price) {
                    gamer.setCoins(gamer.getCoins() - this.price, true);
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public String getBuyErrorMessage(BaseGamer gamer) {
        return Lang.getMessage(this.moneyType == MoneyType.GOLD ? "TITUL_NO_GOLD" : "TITUL_NO_COINS", this.price);
    }

    public enum MoneyType {
        GOLD,
        COINS
    }
}
