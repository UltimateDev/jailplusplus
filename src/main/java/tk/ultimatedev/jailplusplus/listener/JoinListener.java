package tk.ultimatedev.jailplusplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.ultimatedev.jailplusplus.models.Prisoner;

/**
 * @author YoshiGenius
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        Prisoner prisoner = Prisoner.getPrisoner(player.getName());
        if (prisoner == null) return;

    }

}
