package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import ru.galaxy773.multiplatform.api.utils.zip.ZipUtil;

import java.io.File;
import java.util.function.Consumer;

@UtilityClass
public class WorldUtil {

    public static void recreateWorld(WorldCreator worldCreator, String archiveName, Location fallbackLocation, Consumer<WorldCreator> worldSettings, Consumer<World> onComplete, Consumer<Exception> onFail) {
        String worldName = worldCreator.name();
        File worldsDirectory = Bukkit.getWorldContainer();
        if (!archiveName.endsWith(".zip")) {
            if (onFail != null)
                onFail.accept(new IllegalArgumentException("Файл архива мира " + worldName + " должен иметь формать .zip (дано " + archiveName + ")"));
            return;
        }
        if (!new File(worldsDirectory, archiveName).isFile()) {
            if (onFail != null)
                onFail.accept(new IllegalArgumentException("Файл архива " + archiveName + " мира " + worldName + " не найден "));
            return;
        }
        BukkitUtil.runTaskLater(5, () -> {
            World previousWorld = Bukkit.getWorld(worldName);
            if (previousWorld != null) {
                if (previousWorld == fallbackLocation.getWorld()) {
                    if (onFail != null)
                        onFail.accept(new IllegalArgumentException("Пересоздаваемый мир " + worldName + " не может являться fallback миром"));
                    return;
                }
                previousWorld.getPlayers().forEach(player -> player.teleport(fallbackLocation));
            }
            File worldDirectory = new File(worldsDirectory, worldName);
            BukkitUtil.runTaskLater(5, () -> {
                Runnable onWorldUnloaded = () -> {
                    try {
                        if (worldDirectory.isDirectory()) {
                            FileUtils.deleteDirectory(worldDirectory);
                        }
                    } catch (Exception e) {
                        if (onFail != null)
                            onFail.accept(new RuntimeException("Не удалось удалить директорию мира " + worldName, e));
                        return;
                    }
                    try {
                        ZipUtil.unzip(worldsDirectory.getAbsolutePath(), archiveName);
                        // TODO Переименовать директорию из архива в worldName
                    } catch (Exception e) {
                        if (onFail != null)
                            onFail.accept(new RuntimeException("Не удалось разархивировать файл " + archiveName + " мира " + worldName, e));
                        return;
                    }
                    if (!worldDirectory.isDirectory()) {
                        if (onFail != null)
                            onFail.accept(new RuntimeException("Не найдена директория мира " + worldName + " после разархивации " + archiveName));
                        return;
                    }
                    World actualWorld;
                    try {
                        WorldCreator creator = new WorldCreator(worldName);
                        worldSettings.accept(creator);
                        actualWorld = creator.createWorld(); // Требуется синхрон
                    } catch (Exception e) {
                        if (onFail != null)
                            onFail.accept(new RuntimeException("Не удалось создать мир " + worldName + " из архива " + archiveName, e));
                        return;
                    }
                    try {
                        if (onComplete != null) onComplete.accept(actualWorld);
                    } catch (Exception e) {
                        if (onFail != null)
                            onFail.accept(new RuntimeException("Не удалось выполнить действия после создания мира " + worldName, e));
                    }
                };

                if (previousWorld != null) {
                    try {
                        Bukkit.unloadWorld(previousWorld, false); // Требуется синхрон
                    } catch (Exception e) {
                        if (onFail != null)
                            onFail.accept(new RuntimeException("Не удалось выгрузить мир " + worldName, e));
                    }
                    BukkitUtil.runTaskLater(5, onWorldUnloaded);
                } else {
                    onWorldUnloaded.run();
                }
            });
        });
    }

    public void checkAndLoadChunk(World world, int x, int z) {
        if (world == null)
            return;

        BukkitUtil.runTask(() -> {
            if (!world.isChunkLoaded(x, z))
                world.loadChunk(x, z);
        });
    }
}
