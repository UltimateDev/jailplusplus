package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.Log;

import java.util.List;

public class Test implements SubCommand {
    public boolean onCommand(CommandSender player, String[] args) {
<<<<<<< HEAD
        if (args[1].equalsIgnoreCase("new")) {
            if (args[2].equalsIgnoreCase("jail")) {
                Log.info("debug");
                Jail newJail = new Jail("Test jail", new Cuboid(new Location(Bukkit.getWorld("world"), 1, 64, 3), new Location(Bukkit.getWorld("world"), 3, 64, 5)));
                switch (newJail.save()) {
                    case ALREADY_EXISTS:
                        Log.info("Already exists");
                        break;
                    case ALREADY_SAVED:
                        Log.info("Already saved");
                        break;
                    case SUCCESS:
                        Log.info("Success");
                        break;
                    case FAILURE:
                        Log.info("Failure");
                        break;
                    default:
                        Log.info("Default");
                        break;
                }
=======
        if (args[0].equalsIgnoreCase("mkjail")) {
            Jail newJail = new Jail("Test jail", new Cuboid(new Location(Bukkit.getWorld("world"), 1, 64, 3), new Location(Bukkit.getWorld("world"), 3, 64, 5)));

            switch (newJail.save()) {
                case ALREADY_EXISTS:
                    Log.info("Already exists");
                    break;
                case ALREADY_SAVED:
                    Log.info("Already saved");
                    break;
                case SUCCESS:
                    Log.info("Success");
                    break;
                case FAILURE:
                    Log.info("Failure");
                    break;
                default:
                    Log.info("Default");
                    break;
>>>>>>> 440a1ace20d24a1a2f81fd91fcd765d0b99321d4
            }
        } else if (args[0].equalsIgnoreCase("deljail")) {
            Jail.removeJail("Test jail");
            Log.info("OK");
        } else if (args[0].equalsIgnoreCase("getjail")) {
            Jail gottenJail = Jail.getJail("Test jail");
            Log.info(gottenJail.toString());
        } else if (args[0].equalsIgnoreCase("getjails")) {
            List<Jail> jails = Jail.getAllJails();

            for (Jail jail: jails) {
                Log.info(jail.toString());
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
