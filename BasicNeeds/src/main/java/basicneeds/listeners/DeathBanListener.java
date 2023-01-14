package basicneeds.listeners;

import basicneeds.BasicNeeds;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Date;


public class DeathBanListener implements Listener {

    /** The total number of minutes to be banned after death */
    final static int BAN_TIME_IN_MINUTES = 15;

    /** Permission to edit the spawn region */
    final static String ADMIN_PERMISSION = "basicneeds.admin";

    @EventHandler
    public void onPlayerDeath( PlayerDeathEvent event ) {
        // Get the player and timestamp to unban
        Player player = event.getEntity();
        Date date = new Date(System.currentTimeMillis() + BAN_TIME_IN_MINUTES * 60 * 1000);
        if(!player.hasPermission(ADMIN_PERMISSION)) {
            Bukkit.getScheduler().runTaskLater( BasicNeeds.getPlugin(), new Runnable() {

                @Override public void run () {
                    // Ban and kick the player who died
                    Bukkit.getBanList( BanList.Type.NAME ).addBan( player.getName(), ChatColor.DARK_RED + "Death ban for " + BAN_TIME_IN_MINUTES + " minutes.", date, null );
                    player.kickPlayer( ChatColor.DARK_RED + "You died. Try rejoining in " + BAN_TIME_IN_MINUTES + " minutes." );
                }
            }, 1L );
        }
    }

    @EventHandler
    public void onPlayerRespawn( PlayerRespawnEvent event ){
        // Set players spawn location to the world spawn on death
        event.setRespawnLocation( Bukkit.getServer().getWorld( "world" ).getSpawnLocation() );
    }
}
