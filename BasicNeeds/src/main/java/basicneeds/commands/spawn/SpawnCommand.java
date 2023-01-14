package basicneeds.commands.spawn;

import basicneeds.utility.ConfigFile;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    public static String spawnWorld = "world";

    @Override
    public boolean onCommand ( CommandSender sender, Command command, String s, String[] strings ) {
        // If the sender isn't a player, return false
        if(!(sender instanceof Player)) return false;
        // Initialize world to TP to default and update from config if available
        String worldName = spawnWorld;
        if(ConfigFile.getInstanceIfInitialized() != null) {
            worldName = ConfigFile.getInstanceIfInitialized().getSpawnWorld();
        }
        // Store the command, player, location, and retrieve the persistent data container
        Player player = (Player) sender;
        player.teleport(Bukkit.getServer().getWorld(worldName).getSpawnLocation());
        player.sendMessage( ChatColor.YELLOW + "Teleporting to spawn..." );
        return true;
    }
}
