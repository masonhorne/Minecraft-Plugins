package ranks.models;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ranks.managers.PermissionsManager;
import ranks.utility.String2Color;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String name;
    private ChatColor color;
    private List<String> permissions;

    public Group(String name, ChatColor color) {
        // Initialize the group name
        this.name = name;
        this.color = color;
        // Get the section of the configuration file
        ConfigurationSection cs = PermissionsManager.getInstance().getGroupSection(name);
        // Set its keys if they aren't already
        String key = "permissions";
        if(!cs.contains(key)) cs.set(key, new ArrayList());
        this.permissions = new ArrayList(cs.getStringList(key));
    }

    public String getName() {
        return name;
    }

    public boolean hasPermission(String permission){
        return permissions.contains(permission);
    }

    public void addPermission(String permission) {
        // Add the permissions to the group
        permissions.add(permission);
        PermissionsManager.getInstance().getGroupSection(name).set("permissions", permissions);
        PermissionsManager.getInstance().save();
        // Update all online players with the permission
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            PermissionsManager.getInstance().injectPlayer( player );
        }
    }

    public void setColor(String color){
        // Save the color to the group
        PermissionsManager.getInstance().getGroupSection( name ).set("color", color);
        PermissionsManager.getInstance().save();
        // Update all online players with the group
        PermissionsManager.getInstance().setupGroups();
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            PermissionsManager.getInstance().injectPlayer( player );
        }
    }

    public void removePermission(String permission) {
        // Remove the permissions to the group
        permissions.remove(permission);
        PermissionsManager.getInstance().getGroupSection(name).set("permissions", permissions);
        PermissionsManager.getInstance().save();
        // Update all online players with the permission
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            PermissionsManager.getInstance().injectPlayer( player );
        }
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public ChatColor getColor() {
        return color;
    }
}
