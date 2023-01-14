package basicneeds.commands.back;

import basicneeds.BasicNeeds;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BackCommand implements CommandExecutor {

    @Override
    public boolean onCommand ( CommandSender sender, Command command, String s, String[] strings ) {

        if(!(sender instanceof Player )) return false;
        // Store the command, player, location, and retrieve the persistent data container
        Player player = (Player) sender;
        Location location = player.getLocation();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "back");
        Server server = Bukkit.getServer();
        if(!pdc.has(key, PersistentDataType.STRING)) sender.sendMessage( ChatColor.DARK_RED + "You have no previous location to return to." );
        else {
            String[] coords = pdc.get(key, PersistentDataType.STRING).split(",");
            Location home = new Location(server.getWorld(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]), Double.parseDouble(coords[3]));
            player.sendMessage(ChatColor.YELLOW + "Teleporting to previous location...");
            player.teleport(home);
        }
        return true;
    }
}
