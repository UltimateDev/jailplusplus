package tk.ultimatedev.jailplusplus.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import tk.ultimatedev.jailplusplus.handlers.JailStickHandler;

/**
 * @author YoshiGenius
 */
public class JailStick implements Listener {
    
    @EventHandler
    public void onClick(PlayerInteractEntityEvent evt) {
        Player clicker = evt.getPlayer();
        Entity eclicked = evt.getRightClicked();
        if (eclicked instanceof Player) {
            Player clicked = (Player) eclicked;
            if (clicker.hasPermission("jpp.jailstick.use") && clicked.hasPermission("jpp.jailstick.canbe")) {
                if (JailStickHandler.hasEnabled(clicker)) {
                    
                }
            }
        }
    }

}
