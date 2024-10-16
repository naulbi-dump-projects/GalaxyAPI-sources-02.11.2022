package ru.galaxy773.bukkit.api.utils.reflection;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@SuppressWarnings("unchecked")
@UtilityClass
public class BukkitReflectionUtil {

	private final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().substring(23);
	private final int SERVER_VERSION_NUMBER = Integer.parseInt(SERVER_VERSION.split("_")[1]);
    
	public String getServerVersion() {
        return SERVER_VERSION;
    }
	
	public int getServerVersionNumber() {
		return SERVER_VERSION_NUMBER;
	}

	public Class<?> getNMSClass(String className) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + SERVER_VERSION + "." + className);
	}
	
    public Class<?> getCraftBukkitClass(String className) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + SERVER_VERSION + "." + className);
    }
}
