package betterpets.breedingmanager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import betterpets.utils.BreedingUtils;

public class BreedingTaskManager {
    private final Plugin plugin;
    private BukkitTask breedingTask;
    private final Set<Location> breedingLocations = new HashSet<>();

    public BreedingTaskManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void startBreedingScheduler(Runnable checkBreedingProximity) {
        breedingTask = new BukkitRunnable() {
            @Override
            public void run() {
                checkBreedingProximity.run();
            }
        }.runTaskTimer(plugin, 5L, 5L);
    }

    public void cancelBreedingTask() {
        if (breedingTask != null) {
            breedingTask.cancel();
        }
        breedingTask = null;
    }

    public void scheduleLocationRemoval(Location location) {
        new BukkitRunnable() {
            @Override
            public void run() {
                removeLocation(location);
            }
        }.runTaskLater(plugin, 600L); // 30 seconds (time love mode lasts for)
    }

    private void removeLocation(Location location) {
        breedingLocations.remove(location);
    }

    public boolean isBreedingLocation(Location location) {
        for (Location breedingLocation : breedingLocations) {
            if (BreedingUtils.areLocationsCloseEnough(breedingLocation, location)) {
                removeLocation(breedingLocation);
                return true;
            }
        }
        return false;
    }

    public void addBreedingLocation(Location location) {
        breedingLocations.add(location);
    }

    public boolean isBreedingTaskNull() {
        return breedingTask == null;
    }
}
