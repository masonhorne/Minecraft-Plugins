package basicneeds.listeners;

import basicneeds.BasicNeeds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class SilkSpawnerListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        // Get the item and block in hand and that was placed
        ItemStack item = event.getItemInHand();
        Block block = event.getBlock();
        // If it was a spawner, update its type
        if(block.getType().equals(Material.SPAWNER)){
            // Get the item meta type
            BlockStateMeta blockMeta = (BlockStateMeta) item.getItemMeta();
            CreatureSpawner itemSpawner = (CreatureSpawner) blockMeta.getBlockState();
            // Then update the spawner type that was placed
            Bukkit.getScheduler().runTaskLater( BasicNeeds.getPlugin(), new Runnable()  {

                @Override public void run () {
                    CreatureSpawner spawner = (CreatureSpawner) block.getState();
                    spawner.setSpawnedType( itemSpawner.getSpawnedType() );
                    spawner.update();
                }
            }, 1L);
        }
    }


    @EventHandler
    public void onBlockBreak( BlockBreakEvent event ) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        // If a spawner was mined with silk touch, drop the spawner
        if(block.getType().equals( Material.SPAWNER) && player.getInventory().getItemInMainHand().containsEnchantment(
                Enchantment.SILK_TOUCH )) {
            // Get the creature spawner information
            CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
            EntityType spawnerType = creatureSpawner.getSpawnedType();
            ItemStack spawner = new ItemStack(Material.SPAWNER, 1);
            // Get the block meta and update the spawner item
            BlockStateMeta blockMeta = (BlockStateMeta) spawner.getItemMeta();
            CreatureSpawner newCreatureSpawner = (CreatureSpawner) blockMeta.getBlockState();
            NamespacedKey key = new NamespacedKey( BasicNeeds.getPlugin(), "spawntype");
            newCreatureSpawner.setSpawnedType( spawnerType );
            newCreatureSpawner.update();
            blockMeta.setBlockState( newCreatureSpawner );
            // Update the item name and store the type on the item
            blockMeta.setDisplayName( (ChatColor.ITALIC + "" + spawnerType.toString().charAt(0) + spawnerType.toString().toLowerCase().substring(1) + " Spawner").replace("_", " ") );
            spawner.setItemMeta( blockMeta );
            // Drop the block in the world
            block.getWorld().dropItemNaturally( block.getLocation(), spawner );
        }
    }
}
