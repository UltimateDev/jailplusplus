package tk.ultimatedev.jailplusplus.api;

import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.models.Prisoner;

/**
 * @author YoshiGenius
 */
public class JailAPI {

    public boolean isJailed(Player player) {
        Prisoner prisoner = Prisoner.getPrisoner(player.getName());
        if (prisoner == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setJailed(Player player, boolean jailed) {
        Prisoner prisoner = Prisoner.getPrisoner(player.getName());
        if (isJailed(player) && jailed) {
            return;
        }
        if (!(isJailed(player)) && !(jailed)) {
            return;
        }
    }

}
