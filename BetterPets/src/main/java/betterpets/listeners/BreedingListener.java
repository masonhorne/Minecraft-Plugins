package betterpets.listeners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;

import betterpets.breedingmanager.BreedingManager;
import betterpets.constants.BreedingConstants;

public class BreedingListener implements Listener {

    /**
     * Handles the CreatureSpawnEvent when a creature is spawned.
     * Checks if the spawned entity is a valid animal and was spawned due to breeding.
     * If the breeding location is already occupied, cancels the event.
     *
     * @param event The CreatureSpawnEvent that occurred.
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // Check if the spawned entity is a valid animal and was spawned due to breeding
        if (BreedingConstants.VALID_ANIMALS.contains(event.getEntityType()) && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
            BreedingManager breedingManager = BreedingManager.getInstance(); // Get the instance of the BreedingManager
            if (breedingManager != null) {
                Location entityLocation = event.getLocation();// Get the location of the spawned entity
                // Check if the entity's breeding location is already occupied
                if(breedingManager.removeIfBreedingLocation(entityLocation)) {
                    event.setCancelled(true); // Cancel the event if the breeding location is occupied
                }
            }
        }
    }

    /**
     * Handles the EntityBreedEvent when two entities breed.
     * Checks if both parents are managed by BreedingManager.
     * If the player is breeding the animals, spawns a tamed animal at the breeding location.
     *
     * @param event The EntityBreedEvent that occurred.
     */
    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        // Check if both parents are managed by BreedingManager
        if (event.getFather() instanceof Tameable && event.getMother() instanceof Tameable) {
            BreedingManager breedingManager = BreedingManager.getInstance(); // Get the instance of the BreedingManager
            Tameable father = (Tameable) event.getFather(); // Cast the father entity to Tameable
            Tameable mother = (Tameable) event.getMother(); // Cast the mother entity to Tameable
            UUID fatherUUID = father.getUniqueId(); // Get the UUID of the father entity
            UUID motherUUID = mother.getUniqueId(); // Get the UUID of the mother entity
            UUID playerUUID = breedingManager.getPlayerUUID(fatherUUID); // Get the UUID of the player who owns the father entity
            UUID secondPlayerUUID = breedingManager.getPlayerUUID(motherUUID); // Get the UUID of the player who owns the mother entity
            // Check if the player is breeding the animals
            if (playerUUID != null && playerUUID != secondPlayerUUID && event.getBreeder().getUniqueId().equals(playerUUID)) {
                Location breedingLocation = event.getEntity().getLocation(); // Get the location of the breeding event entity
                EntityType babyType = event.getEntityType(); // Get the type of the baby entity
                breedingManager.spawnTamedAnimal(babyType, breedingLocation, playerUUID, fatherUUID); // Spawn a tamed animal at the breeding location, owned by the player
                breedingManager.removeLoveMode(playerUUID, fatherUUID); // Remove the father entity from the breeding manager mappings
                breedingManager.removeLoveMode(playerUUID, motherUUID); // Remove the mother entity from the breeding manager mappings
            }
        }
    }
}
