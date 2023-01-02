package basicneeds.commands.home;

import basicneeds.BasicNeeds;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand ( CommandSender sender, Command command, String s, String[] strings ) {
        // If the sender isn't a player, return false
        if(!(sender instanceof Player)) return false;
        // Store the command, player, location, and retrieve the persistent data container
        Player player = (Player) sender;
        Server server = Bukkit.getServer();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "home");
        // If home is set, teleport to location
        if(pdc.has(key, PersistentDataType.STRING)) {
            String[] coords = pdc.get(key, PersistentDataType.STRING).split(",");
            Location home = new Location(server.getWorld(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]), Double.parseDouble(coords[3]));
            player.sendMessage(ChatColor.YELLOW + "Teleporting to home...");
            player.teleport(home);
            return true;
        }
        // Return false since the command couldn't be handled
        player.sendMessage( ChatColor.YELLOW + "You must first set your home with the /sethome command." );
        return true;
    }
}
