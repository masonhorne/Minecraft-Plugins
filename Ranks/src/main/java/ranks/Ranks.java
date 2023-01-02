package ranks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ranks.listeners.ChatPrefixListener;
import ranks.listeners.InjectListener;
import ranks.managers.CommandManager;
import ranks.managers.PermissionsManager;

import java.util.logging.Level;

public class Ranks extends JavaPlugin {

    @Override
    public void onEnable () {
        super.onEnable();
        // Initialize the permission manager and register the event listeners
        PermissionsManager.getInstance().setup(this);
        this.getCommand("ranks").setExecutor(new CommandManager());
        Bukkit.getServer().getPluginManager().registerEvents(new ChatPrefixListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InjectListener(), this);
        // Log that the plugin was loaded successfully
        this.getLogger().log( Level.INFO, "Ranks successfully loaded.");
    }
}
