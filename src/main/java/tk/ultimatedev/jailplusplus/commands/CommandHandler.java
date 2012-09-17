package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.util.Messenger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author YoshiGenius
 */
public class CommandHandler implements CommandExecutor {
    private Plugin plugin;
    private HashMap<String, SubCommand> commands;
    private ExceptionHandler exceptionHandler;

    public CommandHandler(Plugin plugin) {
        this.plugin = plugin;
        this.exceptionHandler = new ExceptionHandler(this.plugin);
        commands = new HashMap<String, SubCommand>();
        loadCommands();
    }

    private void loadCommands() {
        // help is automatically included.
        // commands.put("list", new JailList());
        commands.put("wand", new Wand());
        commands.put("about", new About());

        /* COMMENT THE FOLLOWING LINE OUT DURING RELEASE VERSIONS: */
        commands.put("test", new Test());
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd1, String commandLabel, String[] args) {
        String cmd = cmd1.getName();
        PluginDescriptionFile pdfFile = plugin.getDescription();
        if (cmd.equalsIgnoreCase("jail")) {
            if (!cs.hasPermission("jpp.main")) {
                cs.sendMessage("You don't have permission!");
                return true;
            }
            if (args == null || args.length < 1) {
                cs.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Jail++ " + ChatColor.RESET + ChatColor.YELLOW + " Version: " + pdfFile.getVersion());
                cs.sendMessage(ChatColor.GOLD + "Type /jail help for help");

                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                help(cs);
                return true;
            }
            String sub = args[0];
            if (!commands.containsKey(sub)) {
                Messenger.sendMessage(cs, "That's not a valid command!", true);
                return true;
            }
            Vector<String> l = new Vector<String>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = l.toArray(new String[l.size()]);
            if (commands.get(sub).playerOnly()) {
                if (!(cs instanceof Player)) {
                    Messenger.sendError(cs, "This command is only allowed for players!", true);
                    return true;
                }
                Player player = (Player) cs;
                try {
                    if (player.hasPermission(commands.get(sub).permission()) || player.hasPermission(commands.get(sub).kitPermission()) || player.hasPermission("jail.*") || player.hasPermission("jail.admin")) {
                        commands.get(sub).onCommand(player, args);
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have access to that command.");
                        return true;
                    }
                } catch (Exception e) {
                    exceptionHandler.logException(e);
                }
            } else {
                try {
                    if (cs.hasPermission(commands.get(sub).permission()) || cs.hasPermission(commands.get(sub).kitPermission()) || cs.hasPermission("jpp.*")) {
                        commands.get(sub).onCommand(cs, args);
                        return true;
                    } else {
                        Messenger.sendNoPermissionError(cs);
                        return true;
                    }
                } catch (Exception e) {
                    exceptionHandler.logException(e);
                }
            }
        }
        return false;
    }

    public void help(CommandSender cs) {
        Messenger.sendMessage(cs, "Jail++: Available Commands");

        if (cs instanceof Player) {
            for (SubCommand command : commands.values()) {
                if (cs.hasPermission(command.permission()) || cs.hasPermission(command.kitPermission()) || cs.hasPermission("jpp.*")) {
                    Messenger.sendMessage(cs, ChatColor.GRAY + "    - " + command.help(cs));
                }
            }
        } else {
            for (SubCommand command : commands.values()) {
                if (!command.playerOnly()) {
                    Messenger.sendMessage(cs, ChatColor.GRAY + "    - " + command.help(cs));
                }
            }
        }
    }

}
