package betterpets;

import java.util.logging.Level;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import betterpets.breedingmanager.BreedingManager;
import betterpets.listeners.BreedingListener;
import betterpets.listeners.SetBreedEventListener;

public class BetterPets extends JavaPlugin {

    private void loadListeners() {
        // Retrieve the server
        Server server = getServer();
        // Register event listeners
        server.getPluginManager().registerEvents(new SetBreedEventListener(), this);
        server.getPluginManager().registerEvents(new BreedingListener(), this);
    }

    @Override
    public void onEnable () {
        super.onEnable();
        BreedingManager.initializeInstance(this);
        loadListeners();
        // Log successful load to the server console
        this.getLogger().log( Level.INFO, "BetterPets successfully loaded.");
    }
}
