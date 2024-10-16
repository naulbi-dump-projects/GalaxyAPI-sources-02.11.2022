import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Object value = getMethodReturn(new CraftFurnace(), "getTileEntity");
        System.out.println((String) value);
    }

    public static Object getMethodReturn(Object classObject, String methodName, Object... parameters) {
        try {
            Method method = classObject.getClass().getDeclaredMethod(methodName,
                    Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
            method.setAccessible(true);
            return method.invoke(classObject, parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void read() throws IOException {
        TypeToken<Map<String, Jedis>> typeToken = new TypeToken<Map<String, Jedis>>() {};
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(typeToken.getType(), new JedisDeserializer());
        Gson gson = gsonBuilder.create();
        File file = new File("C:/test.json");
        Map<String, Jedis> redisMap = new HashMap<>();
        JsonReader jsonReader = gson.newJsonReader(new FileReader(file));
        redisMap = gson.fromJson(jsonReader, new TypeToken<Map<String, Jedis>>() {}.getType());
        redisMap.keySet().forEach(System.out::println);
    }

    private static class JedisDeserializer implements JsonDeserializer<Map<String, Jedis>> {

        @Override
        public Map<String, Jedis> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Map<String, Jedis> jedisMap = new HashMap<>();
            return jedisMap;
            /* new Jedis(
                    (String) jedisData.get("host"),
                    (int) jedisData.get("port"),
                    (int) jedisData.get("connection_timeout"),
                    (int) jedisData.get("so_timeout"),
                    (boolean) jedisData.get("ssl"));*/
        }
    }
}
