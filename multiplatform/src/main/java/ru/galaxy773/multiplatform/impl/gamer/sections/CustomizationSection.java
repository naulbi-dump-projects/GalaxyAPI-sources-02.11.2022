package ru.galaxy773.multiplatform.impl.gamer.sections;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.customization.JoinMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.PrefixColor;
import ru.galaxy773.multiplatform.api.gamer.customization.QuitMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

import java.util.List;
import java.util.Set;

@Getter
public class CustomizationSection extends Section {

    private Set<TitulType> titulTypes;
    private TitulType selectedTitulType;
    private JoinMessage joinMessage;
    private QuitMessage quitMessage;
    private PrefixColor prefixColor;

    public CustomizationSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        List<Integer> data = PlayerInfoLoader.getCustomization(super.baseGamer.getPlayerID());
        this.selectedTitulType = TitulType.getTitul(data.get(0));
        this.joinMessage = JoinMessage.getJoinMessage(data.get(1));
        this.quitMessage = QuitMessage.getQuitMessage(data.get(2));
        this.prefixColor = PrefixColor.getPrefixColor(data.get(3));
        this.titulTypes = PlayerInfoLoader.getTituls(super.baseGamer.getPlayerID());
        return true;
    }

    public void setSelectedTitulType(TitulType selectedTitulType) {
        this.selectedTitulType = selectedTitulType;
        PlayerInfoLoader.setCustomizationValue(super.baseGamer.getPlayerID(), CustomizationType.TITUL, selectedTitulType.getId());
    }

    public void addTitul(TitulType titulType) {
        titulTypes.add(titulType);
        PlayerInfoLoader.addTitul(baseGamer.getPlayerID(), titulType);
    }

    public void setJoinMessage(JoinMessage joinMessage) {
        this.joinMessage = joinMessage;
        PlayerInfoLoader.setCustomizationValue(super.baseGamer.getPlayerID(), CustomizationType.JOIN_MESSAGE, joinMessage.getID());
    }

    public void setQuitMessage(QuitMessage quitMessage) {
        this.quitMessage = quitMessage;
        PlayerInfoLoader.setCustomizationValue(super.baseGamer.getPlayerID(), CustomizationType.QUIT_MESSAGE, quitMessage.getID());
    }

    public void setPrefixColor(PrefixColor prefixColor) {
        this.prefixColor = prefixColor;
        PlayerInfoLoader.setCustomizationValue(super.baseGamer.getPlayerID(), CustomizationType.PREFIX_COLOR, prefixColor.getID());
    }
}
