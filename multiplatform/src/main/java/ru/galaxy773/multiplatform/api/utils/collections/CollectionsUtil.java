package ru.galaxy773.multiplatform.api.utils.collections;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class CollectionsUtil {

    public <K, V> Map<K, V> shuffle(Map<K, V> map) {
        List<K> keys = new ArrayList<>(map.keySet());
        Collections.shuffle(keys);
        Map<K, V> shuffledMap = new LinkedHashMap<>();
        keys.forEach(key -> shuffledMap.put(key, map.get(key)));
        return shuffledMap;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
