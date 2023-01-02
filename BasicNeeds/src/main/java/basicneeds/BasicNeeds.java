package basicneeds;

import basicneeds.commands.home.HomeCommand;
import basicneeds.commands.home.SetHomeCommand;
import basicneeds.commands.requesttp.RequestTpAcceptCommand;
import basicneeds.commands.requesttp.RequestTpCommand;
import basicneeds.commands.requesttp.RequestTpDenyCommand;
import basicneeds.commands.spawn.SpawnCommand;
import basicneeds.listeners.*;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class BasicNeeds extends JavaPlugin {

    private static BasicNeeds plugin;

    @Override
    public void onDisable () {
        super.onDisable();
    }

    @Override
    public void onEnable () {
        super.onEnable();
        // Store plugin for access in other areas
        plugin = this;
        // Set the command executors
        plugin.getCommand("requesttp").setExecutor(new RequestTpCommand());
        plugin.getCommand("rtpaccept").setExecutor(new RequestTpAcceptCommand());
        plugin.getCommand("rtpdeny").setExecutor(new RequestTpDenyCommand());
        plugin.getCommand("sethome").setExecutor(new SetHomeCommand());
        plugin.getCommand("home").setExecutor(new HomeCommand());
        plugin.getCommand("spawn").setExecutor(new SpawnCommand());
        // Retrieve the server
        Server server = getServer();
        // Register event listeners
        server.getPluginManager().registerEvents(new WelcomeListener(), this);
        server.getPluginManager().registerEvents(new SpawnGuardListener(), this);
        server.getPluginManager().registerEvents(new SilkSpawnerListener(), this);
        server.getPluginManager().registerEvents(new HeadDropListener(), this);
        server.getPluginManager().registerEvents(new DeathBanListener(), this);
        // Log successful load to the server console
        this.getLogger().log( Level.INFO, "BasicNeeds successfully loaded.");
    }

    public static BasicNeeds getPlugin () {
        return plugin;
    }
}
