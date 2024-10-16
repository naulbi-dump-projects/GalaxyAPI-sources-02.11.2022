package ru.galaxy773.bukkit.api.utils.simpleperms;

import lombok.experimental.UtilityClass;
import ru.Den_Abr.SimplePerms.Entity.SimpleGroup;
import ru.Den_Abr.SimplePerms.Entity.SimpleUser;
import ru.Den_Abr.SimplePerms.PermissionManager;
import ru.Den_Abr.SimplePerms.SimplePermsCommon;

import java.util.Objects;

@UtilityClass
public class SimplePermsUtil {

    private final PermissionManager PERMISSION_MANAGER = SimplePermsCommon.getPermissionNamager();

    public void setGroup(String playerName, String groupName) {
        SimpleGroup group = PERMISSION_MANAGER.getGroup(groupName);
        SimpleUser user = PERMISSION_MANAGER.getUser(playerName);
        if (!group.isVirtual() && !Objects.equals(group, user.getUserGroup())) {
            user.setUserGroup(group);
            user.save();
            user.updateOrUncache();
        }
    }
}
