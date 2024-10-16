package ru.galaxy773.bukkit.commands.donate;

import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.effect.ParticleAPI;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class FireworkCommand implements CommandIssuer {

    private final ParticleAPI particleAPI = BukkitAPI.getParticleAPI();

    public FireworkCommand(BukkitPlugin javaPlugin) {
        CommandSource commandSource = BukkitAPI.getCommandsAPI().register(javaPlugin, "firework", this, "fw");
        commandSource.setCooldown("COMMAND_FIREWORK", 15);
        commandSource.setOnlyPlayers(true);
        commandSource.setMinimalGroup(Group.LEGEND);
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        Player player = (Player) sender;
        Location first = player.getLineOfSight(Sets.newHashSet(Material.AIR), 1).get(1).getLocation().clone().add(0.5, 0, 0.5);
        this.particleAPI.shootRandomFirework(first);
        sender.sendMessage(Lang.getMessage("COMMAND_FIREWORK"));
    }
}
