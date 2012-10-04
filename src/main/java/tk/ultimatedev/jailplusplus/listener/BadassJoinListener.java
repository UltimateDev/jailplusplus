package tk.ultimatedev.jailplusplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Messenger;

public class BadassJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!JailPlugin.getPlugin().getConfig().getBoolean("this-server-is-really-lame")) {
            if (player.getName().equalsIgnoreCase("yoshigenius") || player.getName().equalsIgnoreCase("goldblattster")) {
                Messenger.broadcastMessage("Jail developer " + event.getPlayer().getName() + " has joined!");
            }
        }
    }
}
