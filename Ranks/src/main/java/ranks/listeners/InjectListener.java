package ranks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ranks.managers.PermissionsManager;

public class InjectListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionsManager permissionsManager = PermissionsManager.getInstance();
        // Inject player permissions on join
        permissionsManager.injectPlayer(player);
    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event) {
        // Uninject player permissions on quit
        PermissionsManager.getInstance().uninjectPlayer(event.getPlayer());
    }
}
