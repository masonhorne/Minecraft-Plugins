package scubaglass;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import scubaglass.commands.ScubaCommand;
import scubaglass.listeners.ScubaListener;

import java.util.logging.Level;

public class ScubaGlass extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable () {
        super.onEnable();
        // Store plugin for access in other areas
        plugin = this;
        // Set the command executors
        plugin.getCommand("scuba").setExecutor(new ScubaCommand());
        // Add the inventory listeners
        Bukkit.getServer().getPluginManager().registerEvents(new ScubaListener(), this);
        // Log successful load to the server console
        this.getLogger().log( Level.INFO, "ScubaGlass successfully loaded.");
    }

    public static Plugin getPlugin() { return plugin; }
}
