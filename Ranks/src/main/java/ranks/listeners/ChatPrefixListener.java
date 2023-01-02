package ranks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ranks.managers.PermissionsManager;
import ranks.models.Group;

import java.util.List;

public class ChatPrefixListener implements Listener {

    @EventHandler
    public void onPlayerChat( AsyncPlayerChatEvent event) {
        // Store the player
        Player player = event.getPlayer();
        // Retrieve all groups for the player
        List<String> groups = PermissionsManager.getInstance().getGroups( player.getName() );
        // If group exists, add prefix
        if(groups.size() != 0) {
            Group group = PermissionsManager.getInstance().getGroup( groups.get( 0 ) );
            event.setFormat(group.getColor() + "[" + group.getName() + "] " + ChatColor.WHITE + player.getName() + " " + event.getMessage());
        } else {
            // Otherwise output w/o < >
            event.setFormat(ChatColor.GRAY + player.getName() + " " + ChatColor.WHITE + event.getMessage());
        }
    }

}
