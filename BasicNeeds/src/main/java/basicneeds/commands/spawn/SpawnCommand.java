package basicneeds.commands.spawn;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand ( CommandSender sender, Command command, String s, String[] strings ) {
        // If the sender isn't a player, return false
        if(!(sender instanceof Player)) return false;
        // Store the command, player, location, and retrieve the persistent data container
        Player player = (Player) sender;
        player.teleport(Bukkit.getServer().getWorld(player.getWorld().getName()).getSpawnLocation());
        player.sendMessage( ChatColor.YELLOW + "Teleporting to spawn..." );
        return true;
    }
}
