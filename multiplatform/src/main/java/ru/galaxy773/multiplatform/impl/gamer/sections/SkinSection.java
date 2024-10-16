package ru.galaxy773.multiplatform.impl.gamer.sections;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;
import ru.galaxy773.multiplatform.impl.skin.Skin;

@Getter
public class SkinSection extends Section {

    private String skinName;
    private Skin skin;

    public SkinSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        Skin skin = PlayerInfoLoader.getSkin(baseGamer.getPlayerID());
        this.skinName = skin.getSkinName();
        this.skin = skin;
        return true;
    }

    public void updateSkin(Skin skin, boolean saveToMySql) {
        String skinName = skin.getSkinName();
        if (skinName == null || skinName.length() < 3)
            return;

        if (skinName.equalsIgnoreCase(this.skinName))
            return;

        this.skinName = skinName;
        this.skin = skin;
        if (saveToMySql)
            PlayerInfoLoader.setSkin(baseGamer.getPlayerID(), skin);
    }
}
