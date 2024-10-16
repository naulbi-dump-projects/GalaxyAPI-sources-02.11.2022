package ru.galaxy773.multiplatform.impl.gamer;

import ru.galaxy773.multiplatform.impl.gamer.sections.NetworkingSection;
import ru.galaxy773.multiplatform.impl.gamer.sections.Section;

public final class OfflineGamer extends IBaseGamerImpl {

    public OfflineGamer(String name) {
        super(name);
        this.initSection(NetworkingSection.class);
        this.getSections().values().forEach(Section::loadData);
    }

    @Override
    public boolean isOnline() {
        return false;
    }
}
