package basicneeds.commands.seen;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class SeenCommand implements CommandExecutor {

    @Override
    public boolean onCommand ( CommandSender commandSender, Command command, String s, String[] strings ) {
        if(strings.length != 1) commandSender.sendMessage( ChatColor.YELLOW + "You must provide a single player name to view their last seen time." );
        OfflinePlayer player = getOfflinePlayer( strings[0] );
        if(!player.hasPlayedBefore()) commandSender.sendMessage( ChatColor.DARK_RED + "The player you provided doesn't exist!" );
        else {
            if(player.isOnline()) commandSender.sendMessage( ChatColor.YELLOW + strings[0] + " is currently online." );
            else {
                Date date = new Date();
                long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(date.getTime() - player.getLastPlayed());
                int elapsedMinutes = (int) (elapsedSeconds / 60);
                int elapsedHours = elapsedMinutes / 60;
                int elapsedDays = elapsedHours / 24;
                int elapsedYears = elapsedDays / 365;
                if(elapsedYears > 0) commandSender.sendMessage(ChatColor.YELLOW + strings[0] + " was last online " + elapsedYears + " years ago.");
                else if(elapsedDays > 0) commandSender.sendMessage( ChatColor.YELLOW + strings[0] + " was last online " + elapsedDays + " days ago.");
                else commandSender.sendMessage( ChatColor.YELLOW + strings[0] + " was last online " + elapsedHours + " hours and " + (elapsedMinutes % 60) + " minutes ago." );
            }
        }
        return true;
    }
}
