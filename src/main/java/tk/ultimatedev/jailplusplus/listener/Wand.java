package tk.ultimatedev.jailplusplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Cuboid;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sushi
 */
public class Wand implements Listener {
    private static Map<String, Cuboid> playerSelections = new HashMap<String, Cuboid>();

    @EventHandler
    public void onWandSelect(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("jpp.wand")) {
            ItemStack itemUsed = event.getPlayer().getItemInHand();

            if (itemUsed.getTypeId() == JailPlugin.getPlugin().getConfig().getInt("wand-id")) {
                switch (event.getAction()) {
                    case LEFT_CLICK_BLOCK:
                    case RIGHT_CLICK_BLOCK:
                }
            }
        }
    }

}
