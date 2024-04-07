package basicneeds;

import java.util.logging.Level;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import basicneeds.commands.back.BackCommand;
import basicneeds.commands.home.HomeCommand;
import basicneeds.commands.home.SetHomeCommand;
import basicneeds.commands.requesttp.RequestTpAcceptCommand;
import basicneeds.commands.requesttp.RequestTpCommand;
import basicneeds.commands.requesttp.RequestTpDenyCommand;
import basicneeds.commands.seen.SeenCommand;
import basicneeds.commands.spawn.SpawnCommand;
import basicneeds.listeners.BackListener;
import basicneeds.listeners.DeathBanListener;
import basicneeds.listeners.HeadDropListener;
import basicneeds.listeners.SilkSpawnerListener;
import basicneeds.listeners.SpawnGuardListener;
import basicneeds.listeners.StarvationListener;
import basicneeds.listeners.WelcomeListener;
import basicneeds.utility.ConfigFile;

public class BasicNeeds extends JavaPlugin {

    private static BasicNeeds plugin;

    private void loadCommands() {
        // Set the command executors
        plugin.getCommand("requesttp").setExecutor(new RequestTpCommand());
        plugin.getCommand("rtpaccept").setExecutor(new RequestTpAcceptCommand());
        plugin.getCommand("rtpdeny").setExecutor(new RequestTpDenyCommand());
        plugin.getCommand("sethome").setExecutor(new SetHomeCommand());
        plugin.getCommand("home").setExecutor(new HomeCommand());
        plugin.getCommand("spawn").setExecutor(new SpawnCommand());
        plugin.getCommand("back").setExecutor(new BackCommand());
        plugin.getCommand("seen").setExecutor(new SeenCommand());
    }

    private void loadListeners() {
        // Retrieve the server
        Server server = getServer();
        // Register event listeners
        server.getPluginManager().registerEvents(new WelcomeListener(), this);
        server.getPluginManager().registerEvents(new SpawnGuardListener(), this);
        server.getPluginManager().registerEvents(new SilkSpawnerListener(), this);
        server.getPluginManager().registerEvents(new HeadDropListener(), this);
        server.getPluginManager().registerEvents(new DeathBanListener(), this);
        server.getPluginManager().registerEvents(new StarvationListener(), this);
        server.getPluginManager().registerEvents(new BackListener(), this);
    }

    @Override
    public void onEnable () {
        super.onEnable();
        // Initialize the configuration file
        ConfigFile.getInstance(this);
        // Store plugin for access in other areas
        plugin = this;
        loadCommands();
        loadListeners();
        // Log successful load to the server console
        this.getLogger().log( Level.INFO, "BasicNeeds successfully loaded.");
    }

    public static BasicNeeds getPlugin () {
        return plugin;
    }
}
