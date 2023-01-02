package ranks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ranks.managers.PermissionsManager;

import java.util.List;

public class InjectListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Inject player permissions on join
        PermissionsManager.getInstance().injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event) {
        // Uninject player permissions on quit
        PermissionsManager.getInstance().uninjectPlayer(event.getPlayer());
    }
}
