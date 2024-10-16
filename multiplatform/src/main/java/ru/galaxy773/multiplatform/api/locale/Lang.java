package ru.galaxy773.multiplatform.api.locale;

import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.*;

@UtilityClass
public class Lang {

    private final Map<String, String> MESSAGES = new HashMap<>();
    private final Yaml YAML;

    public void load() {
        MESSAGES.clear();
        File file = new File("/home/owixmine/build/lang.yml");
        if (!file.exists())
            throw new RuntimeException("Lang file in path /home/owixmine/build/lang.yml not found");

        Map<Object, Object> map = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(file.toPath()));
            map = YAML.loadAs(inputStreamReader, LinkedHashMap.class);
            inputStreamReader.close();
        } catch (IOException e) {
            throw new RuntimeException("Error reading lang.yml file");
        }
        if (map == null) {
            throw new RuntimeException("Lang loading error");
        }
        map.forEach((key, value) -> MESSAGES.put(key.toString(),
                value instanceof List ? StringUtil.join((List<String>) value, "\n") : value.toString()));

        /*URL oracle = null;
        try {
            oracle = new URL("https://raw.githubusercontent.com/NotexTeam/owixmine/main/lang.yml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) oracle.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlConnection.setUseCaches(false);
        Map<Object, Object> map = null;
        try {
            @Cleanup
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            map = YAML.loadAs(inputStreamReader, LinkedHashMap.class);
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (map == null) {
            throw new RuntimeException("Lang loading error");
        }
        urlConnection.disconnect();
        map.forEach((key, value) -> MESSAGES.put(key.toString(),
                value instanceof List ? StringUtil.join((List<String>) value, "\n") : value.toString()));*/
    }

    public List<String> getList(String key, Object... replace) {
        String message = MESSAGES.get(key);
        if (message == null) {
            new IllegalArgumentException("\u041D\u0435 \u0443\u0434\u0430\u043B\u043E\u0441\u044C \u043D\u0430\u0439\u0442\u0438 \u0441\u043E\u043E\u0431\u0449\u0435\u043D\u0438\u0435 \u0441 \u043A\u043B\u044E\u0447\u043E\u043C " + key).printStackTrace();
            return Collections.singletonList("\u0441\u043E\u043E\u0431\u0449\u0435\u043D\u0438\u0435 " + key + " \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u043E");
        }
        return Arrays.asList(String.format(message, replace).split("\n"));
    }

    public String getMessage(String key, Object... replace) {
        String message = MESSAGES.get(key);
        if (message == null) {
            new IllegalArgumentException("\u041D\u0435 \u0443\u0434\u0430\u043B\u043E\u0441\u044C \u043D\u0430\u0439\u0442\u0438 \u0441\u043E\u043E\u0431\u0449\u0435\u043D\u0438\u0435 \u0441 \u043A\u043B\u044E\u0447\u043E\u043C " + key).printStackTrace();
            return "\u0441\u043E\u043E\u0431\u0449\u0435\u043D\u0438\u0435 " + key + " \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u043E";
        }
        return String.format(message, replace);
    }

    static {
        Constructor constructor = new Constructor();
        PropertyUtils propertyUtils = new PropertyUtils();
        propertyUtils.setSkipMissingProperties(true);
        constructor.setPropertyUtils(propertyUtils);
        YAML = new Yaml(constructor);
        load();
    }
}
