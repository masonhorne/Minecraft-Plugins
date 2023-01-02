package scubaglass.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import scubaglass.ScubaGlass;

public class ScubaCommand implements CommandExecutor {

    @Override
    public boolean onCommand ( CommandSender commandSender, Command command, String s, String[] strings ) {
        Server server = Bukkit.getServer();
        Player player = server.getPlayer(commandSender.getName());
        if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType().equals( Material.GLASS ) && player.getInventory().getItemInMainHand().getAmount() == 1) {
            // Get the current helmet item
            ItemStack helmet = player.getInventory().getHelmet();
            // Swap the item with the glass in hand
            player.getInventory().setHelmet( player.getInventory().getItemInMainHand() );
            player.getInventory().setItemInMainHand( helmet );
            // Apply the scuba effects
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 2);
            PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2);
            PotionEffect waterBreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 2);
            player.addPotionEffect( nightVision );
            player.addPotionEffect( slow );
            player.addPotionEffect( waterBreathing );
            // Mark the player as currently using scuba
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey( ScubaGlass.getPlugin(), "scuba");
            pdc.set(key, PersistentDataType.INTEGER, 1);
            commandSender.sendMessage( ChatColor.YELLOW + "You are ready for the ocean!" );
        } else {
            commandSender.sendMessage( ChatColor.YELLOW + "You must be holding a single piece of glass to use this command." );
        }
        return true;
    }
}
