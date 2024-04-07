package basicneeds.listeners;

import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import basicneeds.BasicNeeds;
import basicneeds.utility.ConfigFile;


public class DeathBanListener implements Listener {

    /** The total number of minutes to be banned after death */
    final static int BAN_TIME_IN_MINUTES = 15;

    /** Default world name for respawns */
    final static String spawnWorld = "world";

    /** Permission to edit the spawn region */
    final static String ADMIN_PERMISSION = "basicneeds.admin";

    @EventHandler
    public void onPlayerDeath( PlayerDeathEvent event ) {
        // Get the player and timestamp to unban
        Player player = event.getEntity();
        // Initialize world to TP to default and update from config if available
        int banTimeInMinutes = BAN_TIME_IN_MINUTES;
        if(ConfigFile.getInstanceIfInitialized() != null) {
            banTimeInMinutes = ConfigFile.getInstanceIfInitialized().getDeathBanTime();
        }
        Date date = new Date(System.currentTimeMillis() + banTimeInMinutes * 60 * 1000);
        final int banTimeLabel = banTimeInMinutes;
        if(!player.hasPermission(ADMIN_PERMISSION)) {
            Bukkit.getScheduler().runTaskLater( BasicNeeds.getPlugin(), new Runnable() {
                @Override public void run () {
                    // Ban and kick the player who died
                    Bukkit.getBanList( BanList.Type.NAME ).addBan( player.getName(), ChatColor.DARK_RED + "Death ban for " + banTimeLabel + " minutes.", date, null );
                    player.kickPlayer( ChatColor.DARK_RED + "You died. Try rejoining in " + banTimeLabel + " minutes." );
                }
            }, 1L );
        }
    }

    @EventHandler
    public void onPlayerRespawn( PlayerRespawnEvent event ){
        String worldName = spawnWorld;
        if(ConfigFile.getInstanceIfInitialized() != null) {
            worldName = ConfigFile.getInstanceIfInitialized().getSpawnWorld();
        }
        // Set players spawn location to the world spawn on death
        event.setRespawnLocation( Bukkit.getServer().getWorld( worldName ).getSpawnLocation() );
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        // If the player is not an admin, their gamemode shouldn't be updating
        // This is to prevent deaths from putting players into spectator mode
        if(!event.getPlayer().hasPermission(ADMIN_PERMISSION)) {
            event.setCancelled(true);
        }
    }
}
