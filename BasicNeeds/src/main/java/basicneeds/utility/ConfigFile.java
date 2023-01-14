package basicneeds.utility;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigFile extends YamlConfiguration {

    private File configFile;
    private FileConfiguration configConfig;
    private static final String FILE_NAME = "config.yml";
    private static final String WORLD_KEY = "spawn-world";

    private static ConfigFile cf;

    public static ConfigFile getInstance(JavaPlugin plugin) {
        if(ConfigFile.cf == null) ConfigFile.cf = new ConfigFile(plugin);
        return ConfigFile.cf;
    }

    public static ConfigFile getInstanceIfInitialized() {
        return ConfigFile.cf;
    }

    public String getSpawnWorld() {
        return this.configConfig.get(WORLD_KEY).toString();
    }

    private ConfigFile(JavaPlugin plugin) {
        this.configFile = new File(plugin.getDataFolder(), FILE_NAME);
        if(!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            plugin.saveResource(FILE_NAME, false);
        }
        this.configConfig = YamlConfiguration.loadConfiguration(this.configFile);
    }

}
