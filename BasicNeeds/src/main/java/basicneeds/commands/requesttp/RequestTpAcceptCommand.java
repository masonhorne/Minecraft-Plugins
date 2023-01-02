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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getPlayer;

public class RequestTpAcceptCommand implements CommandExecutor {
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
        // Get the player persistent data
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        // If they don't have a request, output a message
        if(!pdc.has(key, PersistentDataType.STRING)) player.sendMessage( ChatColor.YELLOW + "Currently you have no existing teleport requests." );
        else {
            // Otherwise parse the requests timestamp
            Date timestamp = null;
            String[] request = pdc.get( key, PersistentDataType.STRING ).split( "," );
            try { timestamp = formatter.parse( request[1] ); }
            catch ( ParseException e ) { e.printStackTrace(); }
            // If the timestamp is valid, accept the teleport request
            long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(date.getTime() - timestamp.getTime()) % 60;
            if ( timestamp != null && elapsedSeconds <= 15 ) {
                Player user = getPlayer( request[0] );
                player.sendMessage( ChatColor.YELLOW + "Teleport Accepted." );
                user.teleport( player.getLocation() );
            }
            else {
                // Otherwise, output that the request has expired and remove the request
                player.sendMessage( ChatColor.YELLOW + "Your previous teleport request has expired." );
                pdc.remove( key );
            }
        }
        return true;
    }
}
