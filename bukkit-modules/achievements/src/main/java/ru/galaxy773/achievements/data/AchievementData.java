package ru.galaxy773.achievements.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AchievementData {

    private int progress;
    private long completedDate;

    public AchievementData(int progress) {
        this.progress = progress;
        this.completedDate = -1;
    }

    public boolean isCompleted() {
        return this.completedDate > -1;
    }
}
