package scubaglass.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import scubaglass.ScubaGlass;

public class ScubaListener implements Listener {

    private boolean removedScubaGear(Player player){
        // Access the player data and the required key
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey( ScubaGlass.getPlugin(), "scuba");
        // Return if the helmet was removed
        return pdc.has(key, PersistentDataType.INTEGER) && (player.getInventory().getHelmet() == null || !player.getInventory().getHelmet().getType().equals(Material.GLASS)) && player.hasPotionEffect( PotionEffectType.NIGHT_VISION ) && player.hasPotionEffect( PotionEffectType.SLOW ) && player.hasPotionEffect( PotionEffectType.WATER_BREATHING );
    }

    @EventHandler
    public void onInventoryClose( InventoryCloseEvent event ){
        Player player = (Player) event.getPlayer();
        if(removedScubaGear( player )) {
            player.removePotionEffect( PotionEffectType.NIGHT_VISION );
            player.removePotionEffect( PotionEffectType.SLOW );
            player.removePotionEffect( PotionEffectType.WATER_BREATHING );
        }
    }
}
