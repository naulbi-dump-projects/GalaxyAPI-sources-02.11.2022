package ru.galaxy773.bukkit.api.utils.bukkit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@UtilityClass
public class HeadUtil {

    private final ItemStack BACK_HEAD = HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE2ZWEzNGE2YTZlYzVjMDUxZTY5MzJmMWM0NzFiNzAxMmIyOThkMzhkMTc5ZjFiNDg3YzQxM2Y1MTk1OWNkNCJ9fX0=");
    private final ItemStack FORWARD_HEAD = HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5ZWM3MWMxMDY4ZWM2ZTAzZDJjOTI4N2Y5ZGE5MTkzNjM5ZjNhNjM1ZTJmYmQ1ZDg3YzJmYWJlNjQ5OSJ9fX0=");
    private final ItemStack ACCEPT_HEAD = HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjJkMTQ1YzkzZTVlYWM0OGE2NjFjNmYyN2ZkYWZmNTkyMmNmNDMzZGQ2MjdiZjIzZWVjMzc4Yjk5NTYxOTcifX19");
    private final ItemStack DENY_HEAD = HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZkZTNiZmNlMmQ4Y2I3MjRkZTg1NTZlNWVjMjFiN2YxNWY1ODQ2ODRhYjc4NTIxNGFkZDE2NGJlNzYyNGIifX19");
    private final ItemStack DISABLED_HEAD = HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODJiMjJiZGFlNzk5MDgzYWQ4N2Q5ZDI1MWRlYzhiZDVkNTc0NGZiZTNiOGE1YmE0MDkxYmE1NTk3NGI4NWIifX19");
    private final ItemStack YELLOW_HEAD = HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY0MTY4MmY0MzYwNmM1YzlhZDI2YmM3ZWE4YTMwZWU0NzU0N2M5ZGZkM2M2Y2RhNDllMWMxYTI4MTZjZjBiYSJ9fX0=");

    public ItemStack getBackHead() {
        return BACK_HEAD.clone();
    }

    public ItemStack getForwardHead() {
        return FORWARD_HEAD.clone();
    }

    public ItemStack getAcceptHead() {
        return ACCEPT_HEAD.clone();
    }

    public ItemStack getDenyHead() {
        return DENY_HEAD.clone();
    }

    public ItemStack getYellowHead() {
        return YELLOW_HEAD.clone();
    }

    public ItemStack getDisabledHead() {
        return DISABLED_HEAD.clone();
    }

    public ItemStack getEmptyPlayerHead() {
        Material material = Material.getMaterial("PLAYER_HEAD");
        if (material == null) {
            return new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        }
        return new ItemStack(material);
    }

    public ItemStack getHead(String value) {
        ItemStack skull = getEmptyPlayerHead();
        if (value == null || value.length() <= 16) {
            return getEmptyPlayerHead();
        }
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(skull,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
        );
    }

    /*public ItemStack getHeadAsync(String value) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                return getHead(value);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
     */

    public String getHeadValue(String name) {
        try {
            String result = getURLContent("https://api.mojang.com/users/profiles/minecraft/" + name);
            Gson g = new Gson();
            JsonObject obj = g.fromJson(result, JsonObject.class);
            String uid = obj.get("id").toString().replace("\"","");
            String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
            obj = g.fromJson(signature, JsonObject.class);
            String value = obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
            String decoded = new String(Base64.getDecoder().decode(value));
            obj = g.fromJson(decoded, JsonObject.class);
            String skinURL = obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
            byte[] skinByte = ("{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}").getBytes();
            return new String(Base64.getEncoder().encode(skinByte));
        } catch (Exception ignored) {}
        return null;
    }

    private String getURLContent(String urlStr) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            @Cleanup
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            while(in.readLine() != null) {
                sb.append(in.readLine());
            }
        } catch (Exception ignored) {}
        return sb.toString();
    }
}
