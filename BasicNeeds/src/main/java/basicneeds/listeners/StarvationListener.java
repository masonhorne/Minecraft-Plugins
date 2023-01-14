package basicneeds.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class StarvationListener implements Listener {

    @EventHandler
    public void onEntityDamage( EntityDamageEvent event ) {
        // If the damaged entity was a player, check to make sure they aren't starving to death
        if(event.getEntity() instanceof Player) {
            // Get the entity that was damaged
            LivingEntity entity = (LivingEntity) event.getEntity();
            // Get the damage being dealt
            double damage = event.getFinalDamage();
            // Get the cause of the damage
            EntityDamageEvent.DamageCause cause = event.getCause();
            // If lethal damage is dealt from starvation, cancel the event
            if ( entity.getHealth() - damage <= 0 && cause == EntityDamageEvent.DamageCause.STARVATION )
                event.setCancelled( true );
        }
    }
}
