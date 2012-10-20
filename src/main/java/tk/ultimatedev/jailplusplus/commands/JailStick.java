package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.listener.JailStickListener;

/**
 * @author YoshiGenius
 */
public class JailStick implements SubCommand {
    public boolean onCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;

        if (args.length == 1) {
            JailStickListener.toggleEnabled(p);
        } else {
            if (args[1].equalsIgnoreCase("enable")) {
                JailStickListener.setEnabled(p, true);
            } else if (args[1].equalsIgnoreCase("disable")) {
                JailStickListener.setEnabled(p, false);
            }
        }
        return true;
    }

    public String help(CommandSender cs) {
        return "/jpp jailstick - Enable jailstick and get a jailstick.";
    }

    public String permission() {
        return "jpp.jailstick";
    }

    public String kitPermission() {
        return "jpp.mod";
    }

    public boolean playerOnly() {
        return true;
    }
}
