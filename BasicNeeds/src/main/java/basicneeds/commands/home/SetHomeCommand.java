package basicneeds.commands.home;

import basicneeds.BasicNeeds;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand ( CommandSender sender, Command command, String s, String[] strings ) {
        // If the sender isn't a player, return false
        if(!(sender instanceof Player)) return false;
        // Store the command, player, location, and retrieve the persistent data container
        Player player = (Player) sender;
        Location location = player.getLocation();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "home");
        // Set the home location
        pdc.set(key, PersistentDataType.STRING, location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ());
        player.sendMessage( ChatColor.YELLOW + "Your home has been set." );
        return true;
    }
}
