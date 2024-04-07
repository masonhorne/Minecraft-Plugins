package ranks.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import ranks.models.Group;
import ranks.utility.String2Color;

public class PermissionsManager {

    /** Singleton instance of manager */
    private static PermissionsManager instance = new PermissionsManager();
    /** Reference to plugin */
    private Plugin plugin;
    /** Config file for storing permissions */
    private FileConfiguration config;
    /** Yaml file for storing config */
    private File yml;
    /** Attachments for connecting to Bukkit permissions */
    private HashMap<String, PermissionAttachment> attachments = new HashMap();
    /** Group list of all permission groups */
    private List<Group>                           groups      = new ArrayList();

    private PermissionsManager () {}

    public static PermissionsManager getInstance() { return instance; }

    public void setup(Plugin plugin){
        // Initialize the plugin reference
        this.plugin = plugin;
        // If the directory doesn't exist, create it
        if(!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        // Create the yaml file and make on disc if it doesn't exist
        yml = new File(plugin.getDataFolder(), "permissions.yml");
        if(!yml.exists()){
            try { yml.createNewFile(); }
            catch(IOException e) { e.printStackTrace(); }
        }
        // Load the current configuration from the yaml
        config = YamlConfiguration.loadConfiguration( yml );
        // If no group section exists, add it to the file
        if(!config.contains("groups")) {
            config.createSection( "groups" );
            // Initialize the default user permissions section
            config.set("groups.Default", new ArrayList());
        }
        // Initialize all groups in the system
        setupGroups();
    }

    public void setupGroups() {
        // Clear previously existing groups
        groups.clear();
        // Add all config groups to memory
        for(String group : config.getConfigurationSection("groups").getKeys(false)) {
            // Start with white prefix
            String assignColor = "&f";
            // If group color is assigned, assign prefix color
            if(config.contains("groups." + group + ".color")) assignColor = config.getString( "groups." + group + ".color" );
            ChatColor groupColor = String2Color.string2Color(assignColor);
            groups.add( new Group( group, groupColor ));
        }
    }

    public List<String> getGroups(String player) {
        // Normalize player name
        player = player.toLowerCase();
        // If no config exists, initialize group list
        if(!config.contains("user." + player + ".groups")) config.set("user." + player + ".groups", new ArrayList());
        // Return the list of groups for the user
        return config.getStringList("user." + player + ".groups");
    }

    public void createGroup(String group) {
        // Set the permissions or the new group to empty
        config.getConfigurationSection("groups").set(group + ".permissions", new ArrayList());
        // Save the group to the file
        save();
        // Reload groups into memory
        setupGroups();
    }

    public Group getGroup(String name) {
        // If the group exists, return it
        for(Group group : groups)
            if(group.getName().equals(name)) return group;
        return null;
    }

    public void addGroup(String player, String group){
        // Normalize the player name
        player = player.toLowerCase();
        // Get the group for the player
        List<String> groups = getGroups(player);
        // Add the new group to the player
        groups.add(group);
        config.set("user." + player + ".groups", groups);
        save();
        // Inject new permissions if present on server
        Player pl = Bukkit.getServer().getPlayer(player);
        if(pl != null) injectPlayer(pl);
    }

    public void removeGroup(String player, String group){
        // Normalize the player name
        player = player.toLowerCase();
        // Get the group for the player
        List<String> groups = getGroups(player);
        groups.remove(group);
        config.set("user." + player + ".groups", groups);
        save();
        // Inject new permissions if present on server
        Player pl = Bukkit.getServer().getPlayer(player);
        if(pl != null) injectPlayer(pl);
    }

    public void save(){
        // Save the YML file to disc
        try { config.save(yml); }
        catch(IOException e) { e.printStackTrace(); }
    }

    public void addPermission(String player, String permission){
        // Normalize the player name
        player = player.toLowerCase();
        // Verify that the player exists before injecting
        Player pl = Bukkit.getServer().getPlayer(player);
        if(pl != null) {
            injectPlayer(pl);
            attachments.get(pl.getName()).setPermission(permission, true);
        }
        // Get the player permissions and add the provided permission
        List<String> permissions = getPermissions( player );
        permissions.add(permission);
        // Save the updated permissions list
        config.set("user." + player + ".permissions", permissions);
        save();
    }

    public void removePermission(String player, String permission){
        // Normalize the player name
        player = player.toLowerCase();
        // Verify that the player exists before injecting
        Player pl = Bukkit.getServer().getPlayer(player);
        if(pl != null) {
            injectPlayer(pl);
            attachments.get(pl.getName()).setPermission(permission, false);
        }
        // Get the player permissions and remove the provided permission
        List<String> permissions = getPermissions( player );
        permissions.remove(permission);
        // Save the updated permissions list
        config.set("user." + player + ".permissions", permissions);
        save();
    }

    public List<String> getPermissions(String player) {
        // Normalize the player name
        player = player.toLowerCase();
        // If the player has no permissions, set them to empty
        if(!config.contains("user." + player + ".permissions")) config.set("user." + player + ".permissions", new ArrayList());
        // Return the resulting list
        return config.getStringList("user." + player + ".permissions");
    }

    public void injectPlayer(Player player) {
        // If no attachments exist for this player, add the players attachments
        if(attachments.get(player.getName()) == null) attachments.put( player.getName(), player.addAttachment( plugin ) );
        // If the player has a permission, set it on the attachment, otherwise set to false
        for( Map.Entry<String, Boolean> permission : attachments.get(player.getName()).getPermissions().entrySet()) {
            if(getPermissions(player.getName()).contains(permission.getKey())) attachments.get(player.getName()).setPermission(permission.getKey(), true);
            else attachments.get(player.getName()).setPermission(permission.getKey(), false);
        }
        // If the config doesn't contain the users groups, add that section
        if(!config.contains("user." + player.getName().toLowerCase() + ".groups")) config.set("user." + player.getName().toLowerCase() + ".groups", new ArrayList());
        // Store all groups in the users group section
        Group prefix = null;
        List<String> userGroups = config.getStringList( "user." + player.getName().toLowerCase() + ".groups" );
        // Add default permissions for this user
        userGroups.add("Default");
        for (int i = userGroups.size() - 1; i >= 0; i--) {
            String groupName = userGroups.get(i);
            for ( Group group : groups ) {
                if ( group.getName().equals( "Default" ) || group.getName().equals( groupName ) ) {
                    if ( prefix == null && !group.getName().equals( "Default" ) )
                        prefix = group;
                    for ( String permission : group.getPermissions() )
                        attachments.get( player.getName() ).setPermission( permission, true );
                }
            }
        }
        if(prefix != null) player.setPlayerListName(prefix.getColor() + "[" + prefix.getName() + "] " + ChatColor.WHITE + player.getName());
    }

    public void uninjectPlayer(Player player) {
        // If the player has no attachments, return
        if(attachments.get(player.getName()) == null) return;
        // Otherwise remove the attachments from the player
        player.removeAttachment(attachments.get(player.getName()));
        attachments.remove(player.getName());
    }

    public ConfigurationSection getGroupSection(String group) {
        // Return the group section from the configuration yaml
        return config.getConfigurationSection("groups." + group);
    }

    public Plugin getPlugin() { return plugin; }

}
