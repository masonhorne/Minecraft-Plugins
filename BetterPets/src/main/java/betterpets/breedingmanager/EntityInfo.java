package betterpets.breedingmanager;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class EntityInfo {
    private final EntityType entityType;
    private final Location entityLocation;

    public EntityInfo(EntityType entityType, Location entityLocation) {
        this.entityType = entityType;
        this.entityLocation = entityLocation;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Location getEntityLocation() {
        return entityLocation;
    }
}
