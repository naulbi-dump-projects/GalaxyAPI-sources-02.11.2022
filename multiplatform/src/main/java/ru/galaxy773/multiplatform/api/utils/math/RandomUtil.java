package ru.galaxy773.multiplatform.api.utils.math;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUtil {

    private final Random RANDOM = new Random();

    public double getRandom(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }
}
