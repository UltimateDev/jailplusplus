package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.entity.Player;

/**
 * @author YoshiGenius
 */
public class List implements SubCommand {
    public boolean onCommand(Player player, String[] args) {
        
        return false;
    }

    public String help(Player player) {
        return "/jail list - Lists jails.";
    }

    public String permission() {
        return "jpp.list";
    }

    public String kitPermission() {
        return "jpp.default";
    }
}
