package ru.galaxy773.achievements.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.galaxy773.achievements.api.AchievementCategory;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.api.event.GamerAchievementCompleteEvent;
import ru.galaxy773.achievements.data.AchievementData;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AchievementPlayer {

    @Getter
    private final String name;
    @Getter
    private final Map<AchievementType, AchievementData> achievements;
    private final List<AchievementType> changedProgress = new ArrayList<>();

    /**
     * Добавляет прогресс, получает достижение если прогресс набирается
     * @param type - тип достижения
     * @param progress - сколько прогресса добавить
     */
    public void addProgress(AchievementType type, int progress) {
        AchievementData data = this.achievements.get(type);
        if (data != null && data.isCompleted())
            return;

        int maxProgress = type.getAchievement().getProgress();

        if (data == null) {
            data = new AchievementData(progress >= maxProgress ? -1 : progress);
            this.achievements.put(type, data);
        } else {
            data.setProgress(data.getProgress() + progress >= maxProgress ? -1 : data.getProgress() + progress);
        }

        if (data.getProgress() == -1) {
            data.setCompletedDate(System.currentTimeMillis());

            BukkitGamer gamer = GamerManager.getGamer(this.name);
            if (gamer == null)
                return;

            BukkitUtil.callEvent(new GamerAchievementCompleteEvent(gamer, type));
            type.getAchievement().getComplete().accept(gamer);
        }

        if (!this.changedProgress.contains(type))
            this.changedProgress.add(type);
    }

    public void setProgress(AchievementType type, int progress) {
        AchievementData data = this.achievements.get(type);
        if (data != null && data.isCompleted())
            return;

        int maxProgress = type.getAchievement().getProgress();
        if (data == null) {
            data = new AchievementData(progress >= maxProgress ? -1 : progress);
            this.achievements.put(type, data);
        } else {
            data.setProgress(progress >= maxProgress ? -1 : progress);
        }

        if (data.getProgress() == -1) {
            data.setCompletedDate(System.currentTimeMillis());

            BukkitGamer gamer = GamerManager.getGamer(this.name);
            if (gamer == null)
                return;

            BukkitUtil.callEvent(new GamerAchievementCompleteEvent(gamer, type));
            type.getAchievement().getComplete().accept(gamer);
        }

        if (!this.changedProgress.contains(type))
            this.changedProgress.add(type);
    }

    /**
     * Получить прогресс достижения
     * @param type - тип достижения
     * @return прогресс достижения, если -1 - значит выполнено
     */
    public AchievementData getData(AchievementType type) {
        return this.achievements.get(type);
    }

    /**
     * Узнать, выполнено ли достижение
     * @param type - тип достижения
     * @return выполнено ли достижение
     */
    public boolean isCompleted(AchievementType type) {
        return this.achievements.get(type) != null && this.achievements.get(type).isCompleted();
    }

    public List<AchievementType> getCompletedAchievements() {
        return this.achievements.entrySet()
                .stream()
                .filter((entry) -> entry.getValue().isCompleted())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<AchievementType> getCompletedAchievements(AchievementCategory category) {
        return this.achievements.entrySet()
                .stream()
                .filter((entry) -> entry.getValue().isCompleted() && entry.getKey().getCategory() == category)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Map<AchievementType, AchievementData> getNotCompletedAchievements() {
        Map<AchievementType, AchievementData> notCompleted = new EnumMap<>(AchievementType.class);

        for (Map.Entry<AchievementType, AchievementData> entry : this.achievements.entrySet()) {
            if (!entry.getValue().isCompleted())
                notCompleted.put(entry.getKey(), entry.getValue());
        }

        return notCompleted;
    }

    public Map<AchievementType, AchievementData> getToSave() {
        if (this.changedProgress.isEmpty())
            return null;

        Map<AchievementType, AchievementData> toSave = new EnumMap<>(AchievementType.class);

        for (AchievementType type : this.changedProgress)
            toSave.put(type, this.achievements.get(type));

        return toSave;
    }
}
