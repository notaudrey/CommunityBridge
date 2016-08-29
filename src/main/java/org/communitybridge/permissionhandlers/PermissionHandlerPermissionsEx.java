package org.communitybridge.permissionhandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.tehkode.permissions.PermissionEntity;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PermissionHandlerPermissionsEx extends PermissionHandler {
    public PermissionHandlerPermissionsEx() throws IllegalStateException {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");

        validate(plugin, "PermissionsEx", "1.21.4");
    }

    @Override
    public boolean addToGroup(final Player player, final String groupName) {
        final PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
        final PermissionUser user = getPermissionUser(player);
        if(group == null || user == null) {
            return false;
        } else {
            user.addGroup(group);
            return true;
        }
    }

    @Override
    public List<String> getGroups(final Player player) {
        final PermissionUser permissionUser = getPermissionUser(player);
        if(permissionUser == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.stream(permissionUser.getGroups()).map(PermissionEntity::getName).collect(Collectors.toList()));
    }

    @Override
    public List<String> getGroupsPure(final Player player) {
        final List<String> groups = getGroups(player);

        if(groups.size() == 1 && groups.get(0).equalsIgnoreCase("default")) {
            return new ArrayList<>();
        }

        return groups;
    }

    @Override
    public String getPrimaryGroup(final Player player) {
        final List<String> groups = getGroupsPure(player);
        if(groups.isEmpty()) {
            return "";
        }

        return groups.get(0);
    }

    @Override
    public boolean isMemberOfGroup(final Player player, final String groupName) {
        final PermissionUser permissionUser = getPermissionUser(player);

        return permissionUser != null && permissionUser.inGroup(groupName, false);
    }

    @Override
    public boolean isPrimaryGroup(final Player player, final String groupName) {
        final String primaryGroup = getPrimaryGroup(player);
        return primaryGroup != null && groupName.equalsIgnoreCase(primaryGroup);
    }

    @Override
    public boolean removeFromGroup(final Player player, final String groupName) {
        final PermissionUser permissionUser = getPermissionUser(player);
        if(permissionUser == null) {
            return false;
        }

        permissionUser.removeGroup(groupName);
        return true;
    }

    @Override
    public boolean setPrimaryGroup(final Player player, final String groupName, final String formerGroupName) {
        final boolean result;
        result = formerGroupName == null || removeFromGroup(player, formerGroupName);
        return result && addToGroup(player, groupName);
    }

    @Override
    public boolean supportsPrimaryGroups() {
        return false;
    }

    private PermissionUser getPermissionUser(final Player player) {
        final PermissionUser user = PermissionsEx.getUser(player);
        user.getName();
        return user;
    }
}
