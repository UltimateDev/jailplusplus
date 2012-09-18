package tk.ultimatedev.jailplusplus.commands;

//~--- non-JDK imports --------------------------------------------------------

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.ultimatedev.jailplusplus.handlers.JailStickHandler;

/**
 * @author YoshiGenius
 */
public class JailStick implements SubCommand {
    public boolean onCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;

        if (args.length == 1) {
            JailStickHandler.toggleEnabled(p);
        } else {
            if (args[1].equalsIgnoreCase("enable")) {
                JailStickHandler.setEnabled(p, true);
            } else if (args[1].equalsIgnoreCase("disable")) {
                JailStickHandler.setEnabled(p, false);
            }
        }

        return true;
    }

    public String help(CommandSender cs) {
        return "/jail jailstick - Enable jailstick and get a jailstick.";
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


//~ Formatted by Jindent --- http://www.jindent.com
