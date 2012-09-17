package tk.ultimatedev.jailplusplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * @author YoshiGenius
 */
public class JailStick implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEntityEvent evt) {
        Player p = evt.getPlayer();

    }

}
