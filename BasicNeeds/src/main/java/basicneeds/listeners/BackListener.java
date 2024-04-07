package basicneeds.listeners;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import basicneeds.BasicNeeds;
public class BackListener implements Listener {

    @EventHandler
    public void onTeleport( PlayerTeleportEvent event ) {
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
            // Get the location they are coming from
            Location location = event.getFrom();
            // Get the player and their data
            Player player = event.getPlayer();
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            // Initialize the key for the previous location
            NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "back" );
            // Store the location in the players data
            pdc.set( key, PersistentDataType.STRING, location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location
                    .getZ() );
        }
    }

    @EventHandler
    public void onJoinListener( PlayerJoinEvent event){
        // Get the player
        Player player = event.getPlayer();
        // Get the players persistent data
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        // Initialize the key for previous location
        NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "back");
        // Remove the entry
        pdc.remove(key);
    }

}
