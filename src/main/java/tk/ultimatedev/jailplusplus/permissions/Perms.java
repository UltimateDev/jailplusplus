/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package tk.ultimatedev.jailplusplus.permissions;

import org.bukkit.entity.Player;

/**
 * @author YoshiGenius
 */
public class Perms {
    private static Player player;

    public Perms(Player player) {
        Perms.player = player;
    }

    public boolean gotPermz(String permission) {
        return player.hasPermission("jpp." + permission);
    }
}
