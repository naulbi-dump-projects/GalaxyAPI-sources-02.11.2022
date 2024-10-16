package ru.galaxy773.multiplatform.api.utils.time;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtil {

    public String getTimeLeft(long seconds) {
        if (seconds > 86400) {
            return (seconds / 86400) + " \u0434\u043D. " + getTimeLeft(seconds % 86400);
        }
        if (seconds > 3600) {
            return (seconds / 3600) + " \u0447. " + getTimeLeft(seconds % 3600);
        }
        if (seconds > 60) {
            return seconds / 60 + " \u043C\u0438\u043D. " + seconds % 60 + " \u0441\u0435\u043A.";
        }
        return seconds + " \u0441\u0435\u043A.";
    }

    public String getTimeLeft(long seconds, TimeLeftFormat timeLeftFormat) {
        switch (timeLeftFormat) {
            case SECONDS:
                return getTimeLeft(seconds, TimeLeftFormat.MINUTES) + " " + seconds % 60 + " \u0441\u0435\u043A.";
            case MINUTES:
                return getTimeLeft(seconds, TimeLeftFormat.HOURS) + " " + (seconds % 3600) / 60 + " \u043C\u0438\u043D.";
            case HOURS:
                return getTimeLeft(seconds, TimeLeftFormat.DAYS) + " " + (seconds % 86400) / 3600 + " \u0447.";
            default:
                return seconds / 86400 + " \u0434\u043D.";
        }
    }

    public enum TimeLeftFormat {

        SECONDS,
        MINUTES,
        HOURS,
        DAYS;
    }
}
