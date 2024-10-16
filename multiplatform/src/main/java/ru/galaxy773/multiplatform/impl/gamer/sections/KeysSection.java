package ru.galaxy773.multiplatform.impl.gamer.sections;

import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

import java.util.Map;

public class KeysSection extends Section {

    private Map<KeysType, Integer> keys;

    public KeysSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        this.keys = PlayerInfoLoader.getKeys(super.baseGamer.getPlayerID());
        return true;
    }

    public int getKeys(KeysType keyType) {
        return keys.getOrDefault(keyType, 0);
    }

    public void setKeys(KeysType keyType, int value, boolean saveToMySql) {
        this.keys.put(keyType, value);
        if (saveToMySql) {
            PlayerInfoLoader.setKeys(this.baseGamer.getPlayerID(), keyType, value);
        }
    }
}
