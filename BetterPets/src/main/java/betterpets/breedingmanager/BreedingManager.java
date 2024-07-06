package betterpets.breedingmanager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.Plugin;

import betterpets.utils.BreedingUtils;

public class BreedingManager {
    private static BreedingManager instance;
    private final EntityManager entityManager;
    private final BreedingTaskManager taskManager;

    private BreedingManager(Plugin plugin) {
        entityManager = new EntityManager(plugin);
        taskManager = new BreedingTaskManager(plugin);
    }

    public static synchronized BreedingManager initializeInstance(Plugin plugin) {
        if (instance == null) {
            instance = new BreedingManager(plugin);
        }
        return instance;
    }

    public static synchronized BreedingManager getInstance() {
        return instance;
    }

    public void setPlayerBreedEvent(Player player, Entity entity, EntityType entityType, Location entityLocation) {
        entityManager.setPlayerBreedEvent(player, entity, entityType, entityLocation);
        if (taskManager.isBreedingTaskNull()) {
            taskManager.startBreedingScheduler(this::checkBreedingProximity);
        }
    }

    public void removeLoveMode(UUID playerUUID, UUID entityUUID) {
        entityManager.removeLoveMode(playerUUID, entityUUID);
        if (entityManager.isEmpty()) {
            taskManager.cancelBreedingTask();
        }
    }

    public UUID getPlayerUUID(UUID entityUUID) {
        return entityManager.getPlayerUUID(entityUUID);
    }

    public boolean removeIfBreedingLocation(Location location) {
        return taskManager.isBreedingLocation(location);
    }

    public void spawnTamedAnimal(EntityType entityType, Location location, UUID playerUUID, UUID parentUUID) {
        Tameable babyAnimal = (Tameable) location.getWorld().spawnEntity(location, entityType);
        taskManager.addBreedingLocation(location);
        babyAnimal.setBaby();
        Entity parentEntity = entityManager.getEntity(parentUUID);
        if (parentEntity != null && parentEntity instanceof Tameable) {
            Tameable parentTameable = (Tameable) parentEntity;
            if (babyAnimal instanceof Wolf && parentTameable instanceof Wolf) {
                Wolf babyWolf = (Wolf) babyAnimal;
                Wolf parentWolf = (Wolf) parentTameable;
                babyWolf.setVariant(parentWolf.getVariant());
            } else if (babyAnimal instanceof Cat && parentTameable instanceof Cat) {
                Cat babyCat = (Cat) babyAnimal;
                Cat parentCat = (Cat) parentTameable;
                babyCat.setCatType(parentCat.getCatType());
            }
        }
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            babyAnimal.setTamed(true);
            babyAnimal.setOwner(player);
        }
    }

    private void checkBreedingProximity() {
        Set<UUID> playerUUIDs = new HashSet<>(entityManager.getPlayerUUIDs());
    
        for (UUID playerUUID : playerUUIDs) {
            Set<UUID> entityUUIDs = entityManager.getEntityUUIDs(playerUUID);
            if (entityUUIDs == null) {
                continue;
            }
    
            Set<UUID> processedAnimals = new HashSet<>();
            Set<UUID> animalsToRemove = new HashSet<>();
    
            for (UUID entityUUID : entityUUIDs) {
                if (processedAnimals.contains(entityUUID)) {
                    continue;
                }
    
                EntityInfo entityInfo = entityManager.getEntityInfo(playerUUID, entityUUID);
                if (entityInfo == null) {
                    continue;
                }
    
                EntityType entityType = entityInfo.getEntityType();
                Location entityLocation = entityInfo.getEntityLocation();
    
                for (UUID otherEntityUUID : entityUUIDs) {
                    if (!entityUUID.equals(otherEntityUUID) && !processedAnimals.contains(otherEntityUUID)) {
                        EntityInfo otherEntityInfo = entityManager.getEntityInfo(playerUUID, otherEntityUUID);
                        if (otherEntityInfo == null) {
                            continue;
                        }
    
                        Location otherEntityLocation = otherEntityInfo.getEntityLocation();
    
                        if (BreedingUtils.areLocationsCloseEnough(entityLocation, otherEntityLocation)) {
                            Location breedingLocation = BreedingUtils.getBreedingLocation(entityLocation, otherEntityLocation);
                            spawnTamedAnimal(entityType, breedingLocation, playerUUID, entityUUID);
                            taskManager.addBreedingLocation(breedingLocation);
                            taskManager.scheduleLocationRemoval(breedingLocation);
    
                            // Mark both entities as processed for this iteration
                            processedAnimals.add(entityUUID);
                            processedAnimals.add(otherEntityUUID);
                            animalsToRemove.add(entityUUID);
                            animalsToRemove.add(otherEntityUUID);
                        }
                    }
                }
            }
    
            for (UUID uuid : animalsToRemove) {
                entityManager.removeLoveMode(playerUUID, uuid);
            }
        }
    }    
}
