package betterpets.listeners;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import betterpets.breedingmanager.BreedingManager;
import betterpets.constants.BreedingConstants;
import betterpets.utils.BreedingUtils;

public class SetBreedEventListener implements Listener {

    /**
     * Handles the player interact entity event.
     * 
     * @param event The PlayerInteractEntityEvent triggered when a player interacts with an entity.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked(); // Get the entity that the player interacted with
        EntityType entityType = entity.getType(); // Get the type of the entity
        if (BreedingConstants.VALID_ANIMALS.contains(entityType)) { // Check if the entity type is in the set of valid animals
            Player player = event.getPlayer(); // Get the player who interacted with the entity
            ItemStack itemInHand = player.getInventory().getItemInMainHand(); // Get the item in the player's main hand
            // Check if the entity is an instance of Animals and if the player has set them into love mode with this interaction
            if (entity instanceof Animals && !((Animals) entity).isLoveMode() && BreedingUtils.isBreedingItem(entityType, itemInHand)) {
                Animals animal = (Animals) entity; // Cast the entity to an Animals object
                if(animal.canBreed() && !animal.isLoveMode()) {
                    BreedingManager breedingManager = BreedingManager.getInstance(); // Get the instance of the BreedingManager
                    breedingManager.setPlayerBreedEvent(player, entity, entityType, entity.getLocation()); // Store the breed event for the player and entity
                }
            } 
        }
    }
}
