package basicneeds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import basicneeds.utility.ConfigFile;

public class SpawnGuardListener implements Listener {

    /** Dimensions for the spawn region */
    final static int SPAWN_DIMENSION = 20;

    /** Permission to edit the spawn region */
    final static String SPAWN_PERMISSION = "basicneeds.admin";

    /** Spawn World Name */
    public static String spawnWorld = "world";

    boolean inSpawn(Location blockLocation, Location spawnLocation){
        return spawnLocation.getWorld().getName().equals( blockLocation.getWorld().getName())
                && blockLocation.getX() >= spawnLocation.getX() - (SPAWN_DIMENSION / 2)
                && blockLocation.getX() <= spawnLocation.getX() + (SPAWN_DIMENSION / 2)
                && blockLocation.getZ() >= spawnLocation.getZ() - (SPAWN_DIMENSION / 2)
                && blockLocation.getZ() <= spawnLocation.getZ() + (SPAWN_DIMENSION / 2);
    }

    String getWorldName() {
        String worldName = "world";
        if(ConfigFile.getInstanceIfInitialized() != null) {
            worldName = ConfigFile.getInstanceIfInitialized().getSpawnWorld();
        }
        return worldName;
    }

    Location getWorldSpawnLocation() {
        return Bukkit.getServer().getWorld(getWorldName()).getSpawnLocation();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Retrieve the spawn location for over world
        Location spawnLocation = getWorldSpawnLocation();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();
        // If the block is in the over world, check if it conflicts with the spawn region
        if(inSpawn(blockLocation, spawnLocation) && !event.getPlayer().hasPermission( SPAWN_PERMISSION )){
            event.setCancelled( true );
            event.getPlayer().sendMessage( ChatColor.DARK_RED + "You do not have permission to edit spawn." );
        }
    }

    @EventHandler
    public void onBlockExplosion( BlockExplodeEvent event ) {
        // Retrieve the spawn location for over world
        Location spawnLocation = getWorldSpawnLocation();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();
        // If the block is in the over world, check if it conflicts with the spawn region and cancel if so
        if(inSpawn(blockLocation, spawnLocation) ) event.setCancelled( true );
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // Retrieve the spawn location for over world
        Location spawnLocation = getWorldSpawnLocation();
        Block block = event.getBlock();
        Location blockLocation = block.getLocation();
        // If the block is in the over world, check if it conflicts with the spawn region
        if(inSpawn(blockLocation, spawnLocation) && !event.getPlayer().hasPermission( SPAWN_PERMISSION )){
            event.setCancelled( true );
            event.getPlayer().sendMessage( ChatColor.DARK_RED + "You do not have permission to edit spawn." );
        }
    }

    @EventHandler
    public void onEntityDamageByEntity( EntityDamageByEntityEvent event ) {
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();
        if(damager instanceof Player && damaged instanceof  Player && inSpawn(damaged.getLocation(), getWorldSpawnLocation())) {
            event.setCancelled( true );
            damager.sendMessage(ChatColor.DARK_RED + "You cannot attack a player in the spawn region.");
        }
    }

    @EventHandler
    public void onPlayerInteract( PlayerInteractEvent event ) {
        Block block = event.getClickedBlock();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && inSpawn(block.getLocation(), getWorldSpawnLocation()) && !event.getPlayer().hasPermission( SPAWN_PERMISSION )) {
            event.setCancelled(true);
        }
    }
}
