package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Messenger;

/**
 * @author Sushi
 */
public class Wand implements SubCommand {
    public boolean onCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;

        if (player.hasPermission("jpp.wand")) {
            Material m = Material.getMaterial(JailPlugin.getPlugin().getConfig().getInt("wand-id"));
            if (m == null) {
                Messenger.sendError(cs, "The Jail++ wand's ID is not a real material!");
                return true;
            }
            Messenger.sendMessage(player, "You are receiving a Jail++ wand (#" + JailPlugin.getPlugin().getConfig().getInt("wand-id") + ", " + m.name().toLowerCase().replaceAll("_", " ") + ").");
            player.getInventory().addItem(new ItemStack(Material.getMaterial(JailPlugin.getPlugin().getConfig().getInt("wand-id")), 1));
            return true;
        } else {
            Messenger.sendNoPermissionError(player);
        }

        return false;
    }

    public String help(CommandSender player) {
        return "/jpp wand - Gives you a jail wand.";
    }

    public String permission() {
        return "jpp.wand";
    }

    public String kitPermission() {
        return "jpp.*";
    }

    public boolean playerOnly() {
        return true;
    }
}
