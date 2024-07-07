package betterpets.utils;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import betterpets.constants.BreedingConstants;

public class BreedingUtils {

    public static boolean isBreedingItem(EntityType entityType, ItemStack item) {
        Set<Material> items = BreedingConstants.BREEDING_ITEMS.get(entityType);
        return items != null && items.contains(item.getType());
    }

    public static boolean areLocationsCloseEnough(Location loc1, Location loc2, int distance) {
        return loc1.distance(loc2) <= distance;
    }

    public static Location getBreedingLocation(Location loc1, Location loc2) {
        double midX = (loc1.getX() + loc2.getX()) / 2;
        double midY = (loc1.getY() + loc2.getY()) / 2;
        double midZ = (loc1.getZ() + loc2.getZ()) / 2;
        return new Location(loc1.getWorld(), midX, midY, midZ);
    }
}
