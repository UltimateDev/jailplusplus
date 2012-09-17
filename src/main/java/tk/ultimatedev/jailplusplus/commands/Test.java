package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.util.Cuboid;

public class Test implements SubCommand {
    public boolean onCommand(CommandSender player, String[] args) {
        if (args[1].equalsIgnoreCase("new")) {
            if (args[2].equalsIgnoreCase("jail")) {
                Jail newJail = new Jail("Test jail", new Cuboid(new Location(Bukkit.getWorld("world"), 1, 64, 3), new Location(Bukkit.getWorld("world"), 3, 64, 5)));
                newJail.save();
            }
        }

        if (args[1].equalsIgnoreCase("delete")) {
            if (args[2].equalsIgnoreCase("jail")) {
                Jail.removeJail("Test jail");
            }
        }
        return false;
    }

    public String help(CommandSender player) {
        return "/jail test - Run tests.";
    }

    public String permission() {
        return "jpp.test";
    }

    public String kitPermission() {
        return "jpp.default";
    }

    public boolean playerOnly() {
        return false;
    }
}
