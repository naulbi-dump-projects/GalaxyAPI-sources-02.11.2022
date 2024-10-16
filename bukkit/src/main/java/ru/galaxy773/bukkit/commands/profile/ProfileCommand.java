package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.ProfileMainPage;

public class ProfileCommand extends GamerCommand {

    public ProfileCommand() {
        super("profile", "profil", "профиль");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(ProfileMainPage.class, (Player) sender);
    }
}
