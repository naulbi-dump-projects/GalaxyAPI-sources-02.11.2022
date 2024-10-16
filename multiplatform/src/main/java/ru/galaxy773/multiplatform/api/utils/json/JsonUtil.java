package ru.galaxy773.multiplatform.api.utils.json;

import com.google.gson.*;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class JsonUtil {

    public Object handlePrimitive(JsonPrimitive json) {
        if (json.isBoolean()) {
            return json.getAsBoolean();
        } else if(json.isString()) {
            return json.getAsString();
        } else {
            BigDecimal bigDecimal = json.getAsBigDecimal();
            bigDecimal.toBigIntegerExact();
            return bigDecimal.doubleValue();
        }
    }

    public Object handleArray(JsonArray json, JsonDeserializationContext context) {
        Object[] array = new Object[json.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = context.deserialize(json.get(i), Object.class);
        }

        return array;
    }
    
    public Map<String, Object> handleMap(JsonObject json, JsonDeserializationContext context) {
        Map<String, Object> map = new HashMap<String, Object>();
        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            map.put(entry.getKey(), context.deserialize(entry.getValue(), Object.class));
        }

        return map;
    }
}
