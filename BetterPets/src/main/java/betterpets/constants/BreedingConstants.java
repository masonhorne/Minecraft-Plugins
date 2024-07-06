package betterpets.constants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class BreedingConstants {

    public static final Set<EntityType> VALID_ANIMALS = EnumSet.of(
        EntityType.WOLF,
        EntityType.CAT
    );

    public static final Map<EntityType, Set<Material>> BREEDING_ITEMS = new HashMap<EntityType, Set<Material>>() {{
        put(EntityType.WOLF, EnumSet.of(Material.BEEF, Material.COOKED_BEEF, Material.PORKCHOP, Material.COOKED_PORKCHOP, Material.CHICKEN, Material.COOKED_CHICKEN, Material.RABBIT, Material.COOKED_RABBIT, Material.ROTTEN_FLESH, Material.MUTTON, Material.COOKED_MUTTON));
        put(EntityType.CAT, EnumSet.of(Material.COD, Material.SALMON));
    }};
}
