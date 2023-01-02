package basicneeds.commands.requesttp;

import basicneeds.BasicNeeds;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RequestTpDenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand ( CommandSender sender, Command command, String s, String[] strings ) {
        // If the sender isn't a player, return false
        if(!(sender instanceof Player)) return false;
        // Store the player and keys for data
        Player player = (Player) sender;
        NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "requesttp");
        // Get the player persistent data
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        // If they don't have a request, output a message
        if(!pdc.has(key, PersistentDataType.STRING)) player.sendMessage( ChatColor.YELLOW + "Currently you have no existing teleport requests." );
        else {
            // Otherwise, deny the incoming request
            player.sendMessage( ChatColor.YELLOW + "Teleport Denied." );
            pdc.remove(key);
        }
        return true;
    }
}
