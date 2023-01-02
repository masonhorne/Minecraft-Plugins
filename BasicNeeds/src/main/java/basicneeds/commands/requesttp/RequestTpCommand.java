package basicneeds.commands.requesttp;

import basicneeds.BasicNeeds;
import basicneeds.utility.InputSanitation;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.bukkit.Bukkit.getPlayer;

public class RequestTpCommand implements CommandExecutor {
    @Override
    public boolean onCommand ( CommandSender sender, Command command, String s, String[] strings ) {
        // If the sender isn't a player, return false
        if(!(sender instanceof Player)) return false;
        // Store the player and keys for data
        Player player = (Player) sender;
        NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "requesttp");
        // Initialize date and formatter
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat( "MMM dd YYYY | HH:mm:ss" );
        // If not 1 argument, display error message.
        if(strings.length != 1 || !InputSanitation.isValidUsername( strings[0] )) player.sendMessage( ChatColor.YELLOW + "Please provide a valid username to request a teleport to." );
        else {
            // Otherwise, request a tp to the player provided
            Player recipient = getPlayer(strings[0]);
            // Get the player data
            PersistentDataContainer pdc = recipient.getPersistentDataContainer();
            // Store the current players request with time
            pdc.set(key, PersistentDataType.STRING, player.getName() + "," + formatter.format(date));
            // Send a message to the requested player to notify them
            recipient.sendMessage( ChatColor.RED + player.getName() + ChatColor.YELLOW + " is requesting to teleport to you.\nUse /requesttp accept or /requesttp deny respond.\nThis request will expire in 15 seconds." );
            player.sendMessage( ChatColor.YELLOW + "Request sent." );
        }
        return true;
    }
}
