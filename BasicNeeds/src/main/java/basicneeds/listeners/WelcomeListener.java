package basicneeds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.bukkit.Bukkit.getServer;

public class WelcomeListener implements Listener {

    @EventHandler
    public void onJoinListener(PlayerJoinEvent event){
        // Get the player and server object
        Player player = event.getPlayer();
        Server server = getServer();
        // If a new player has joined, broadcast a welcome message
        if(!player.hasPlayedBefore()) server.broadcastMessage( ChatColor.BOLD + "Welcome to the server, " + player.getName() + "!");
        // Log the local server time for the user with players online
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat( "MMM dd | HH:mm" );
        player.sendMessage(ChatColor.GRAY + "Local Time: " + formatter.format(date) + "\nCurrently " + server.getOnlinePlayers().size() + " players are online.");
    }

    @EventHandler
    public void onPlayerSpawn( PlayerSpawnLocationEvent event ){
        // Set the players spawn location to the world spawn on initial join
        if(!event.getPlayer().hasPlayedBefore()) event.setSpawnLocation( Bukkit.getServer().getWorld( "world" ).getSpawnLocation() );
    }

}
