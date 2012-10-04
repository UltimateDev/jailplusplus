package tk.ultimatedev.jailplusplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.models.file.UserdataYAML;
import tk.ultimatedev.jailplusplus.util.Messenger;

import java.util.HashMap;

/**
 * @author YoshiGenius
 */
public class JailStickListener implements Listener {
    private static HashMap<Player, Boolean> jailstick = new HashMap<Player, Boolean>();
    private static HashMap<Player, Jail> jail = new HashMap<Player, Jail>();
    private static HashMap<Player, String> time = new HashMap<Player, String>();
    private static HashMap<Player, String> reason = new HashMap<Player, String>();

    public static boolean hasEnabled(Player player) {
        return JailStickListener.jailstick.get(player);
    }

    public static void setEnabled(Player player, boolean bool) {
        JailStickListener.jailstick.put(player, bool);

        if (bool == true) {
            Messenger.sendMessage(player, "[JailStick] enabled!", false);
        } else {
            Messenger.sendMessage(player, "[JailStick] disabled!", false);
        }
    }

    public static void toggleEnabled(Player player) {
        if (JailStickListener.hasEnabled(player)) {
            JailStickListener.setEnabled(player, false);
        } else {
            JailStickListener.setEnabled(player, true);
        }
    }

    public static void setOptions(Player player, Jail xjail, String xtime, String xreason) {
        jail.put(player, xjail);
        time.put(player, xtime);
        reason.put(player, xreason);
    }

    private void addOptions(Player player) {
        UserdataYAML data = new UserdataYAML(player);

        jail.put(player, data.getJailStickJail());
        time.put(player, data.getJailStickTime());
        reason.put(player, data.getJailStickReason());
    }

    private void removeOptions(Player player) {
        jail.remove(player);
        time.remove(player);
        reason.remove(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        Player p = evt.getPlayer();

        JailStickListener.setEnabled(p, false);
        this.addOptions(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        Player p = evt.getPlayer();

        JailStickListener.jailstick.remove(p);
        this.removeOptions(p);
    }

    @EventHandler
    public void onKick(PlayerKickEvent evt) {
        Player p = evt.getPlayer();

        JailStickListener.jailstick.remove(p);
        this.removeOptions(p);
    }
}
