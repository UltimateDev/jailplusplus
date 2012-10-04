package tk.ultimatedev.jailplusplus.api;

import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.models.DBCommon;
import tk.ultimatedev.jailplusplus.models.Prisoner;

/**
 * Provides API methods that can be used in order
 * to manipulate various parts of Jail++.
 *
 * @author Sushi
 */
public class JailAPI {
    public boolean isJailed(Player player) {
        Prisoner prisoner = Prisoner.getPrisoner(player.getName());
        return prisoner != null;
    }

    public DBCommon.DBResponse setJailed(Player player, String jailer, int sentence, int cell, String reason) {
        Prisoner target = new Prisoner(player.getName(), cell, ((int) (System.currentTimeMillis() / 1000L)), sentence, 0, reason, jailer);
        return target.save();
    }

}
