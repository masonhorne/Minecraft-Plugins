package basicneeds.listeners;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;


public class DeathBanListener implements Listener {

    /** The total number of minutes to be banned after death */
    final static int BAN_TIME_IN_MINUTES = 15;

    @EventHandler
    public void onPlayerDeath( PlayerDeathEvent event ) {
        // Get the player and timestamp to unban
        Player player = event.getEntity();
        Date date = new Date(System.currentTimeMillis() + BAN_TIME_IN_MINUTES * 60 * 1000);
        // Ban and kick the player who died
        Bukkit.getBanList( BanList.Type.NAME ).addBan(player.getName(), ChatColor.DARK_RED + "Death ban for " + BAN_TIME_IN_MINUTES + " minutes.", date, null);
        player.kickPlayer( ChatColor.DARK_RED + "You died. Try rejoining in " + BAN_TIME_IN_MINUTES + " minutes." );
    }
}
