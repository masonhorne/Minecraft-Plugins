package betterpets.breedingmanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityManager {
    private final Plugin plugin;
    private final Map<UUID, Map<UUID, EntityInfo>> playerToEntityMap = new HashMap<>();
    private final Map<UUID, UUID> entityToPlayerMap = new HashMap<>();
    private final Map<UUID, Entity> entityObjectsMap = new HashMap<>();

    public EntityManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setPlayerBreedEvent(Player player, Entity entity, EntityType entityType, Location entityLocation) {
        UUID playerUUID = player.getUniqueId();
        UUID entityUUID = entity.getUniqueId();

        Map<UUID, EntityInfo> entityMap = playerToEntityMap.computeIfAbsent(playerUUID, k -> new HashMap<>());
        entityMap.put(entityUUID, new EntityInfo(entityType, entityLocation));
        entityToPlayerMap.put(entityUUID, playerUUID);
        entityObjectsMap.put(entityUUID, entity);

        scheduleLoveModeRemoval(playerUUID, entityUUID);
    }

    public void removeLoveMode(UUID playerUUID, UUID entityUUID) {
        Map<UUID, EntityInfo> entityMap = playerToEntityMap.get(playerUUID);
        if (entityMap != null) {
            entityMap.remove(entityUUID);
        }
        entityToPlayerMap.remove(entityUUID);
        entityObjectsMap.remove(entityUUID);
    }

    public UUID getPlayerUUID(UUID entityUUID) {
        return entityToPlayerMap.get(entityUUID);
    }

    public Entity getEntity(UUID entityUUID) {
        return entityObjectsMap.get(entityUUID);
    }

    public Set<UUID> getPlayerUUIDs() {
        return playerToEntityMap.keySet();
    }

    public Set<UUID> getEntityUUIDs(UUID playerUUID) {
        Map<UUID, EntityInfo> entityMap = playerToEntityMap.get(playerUUID);
        return entityMap != null ? entityMap.keySet() : null;
    }

    public EntityInfo getEntityInfo(UUID playerUUID, UUID entityUUID) {
        Map<UUID, EntityInfo> entityMap = playerToEntityMap.get(playerUUID);
        return entityMap != null ? entityMap.get(entityUUID) : null;
    }

    public boolean isEmpty() {
        return entityToPlayerMap.isEmpty();
    }

    private void scheduleLoveModeRemoval(UUID playerUUID, UUID entityUUID) {
        new BukkitRunnable() {
            @Override
            public void run() {
                removeLoveMode(playerUUID, entityUUID);
            }
        }.runTaskLater(plugin, 600L); // 30 seconds for animal to be in love mode
    }
}
