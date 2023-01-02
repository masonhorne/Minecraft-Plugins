package basicneeds.listeners;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Date;


public class HeadDropListener implements Listener {

    public ItemStack getPlayerHead(Player player) {
        Material type = Material.PLAYER_HEAD;
        ItemStack item = new ItemStack(type, 1);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwnerProfile( player.getPlayerProfile() );
        item.setItemMeta( skullMeta );
        return item;
    }

    @EventHandler
    public void onPlayerDeath( PlayerDeathEvent event ) {
        Player player = (Player) event.getEntity();
        player.getWorld().dropItemNaturally( player.getLocation(), getPlayerHead( player ));
    }
}
