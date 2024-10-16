package ru.galaxy773.multiplatform.impl.gamer.sections;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;

@Getter
public abstract class Section {

    protected final IBaseGamer baseGamer;

    public Section(IBaseGamer baseGamer) {
        this.baseGamer = baseGamer;
    }

    public abstract boolean loadData();
}
