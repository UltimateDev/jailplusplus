package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.command.CommandSender;

/**
 * @author YoshiGenius
 */
public class List implements SubCommand {
    public String help(CommandSender player) {
        return "/jail list - Lists jails.";
    }

    public String permission() {
        return "jpp.list";
    }

    public String kitPermission() {
        return "jpp.default";
    }

    public boolean onCommand(CommandSender cs, String[] args) {
        return false;
    }

    public boolean playerOnly() {
        return false;
    }
}

