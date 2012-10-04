package tk.ultimatedev.jailplusplus.commands;

//~--- non-JDK imports --------------------------------------------------------

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Messenger;

//~--- JDK imports ------------------------------------------------------------

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author YoshiGenius
 */
public class CommandHandler implements CommandExecutor {
    private HashMap<String, SubCommand> commands;
    private ExceptionHandler exceptionHandler;
    private JailPlugin plugin;

    public CommandHandler(JailPlugin plugin) {
        plugin.getCommand("jail").setExecutor(new JailCmd());

        this.plugin = plugin;
        this.exceptionHandler = new ExceptionHandler(this.plugin);
        this.commands = new HashMap<String, SubCommand>();
        loadCommands();
    }

    private void loadCommands() {
        commands.put("wand", new Wand());
        commands.put("about", new About());
        commands.put("jailstick", new JailStick());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd1, String commandLabel, String[] args) {
        String cmd = cmd1.getName();
        PluginDescriptionFile pdfFile = plugin.getDescription();

        if (cmd.equalsIgnoreCase("jpp")) {
            if (!sender.hasPermission("jpp.main")) {
                Messenger.sendNoPermissionError(sender);

                return true;
            }

            if ((args == null) || (args.length < 1)) {
                Messenger.sendMessage(sender, "Jail++ v" + pdfFile.getVersion());
                Messenger.sendMessage(sender, "Need help? Type /jail help for assistance.");

                return true;
            }

            if (args[0].equalsIgnoreCase("help")) {
                help(sender);

                return true;
            }

            String sub = args[0];

            if (!commands.containsKey(sub)) {
                Messenger.sendMessage(sender, "That's not a valid command!", true);

                return true;
            }

            Vector<String> l = new Vector<String>();

            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = l.toArray(new String[l.size()]);

            if (commands.get(sub).playerOnly()) {
                if (!(sender instanceof Player)) {
                    Messenger.sendError(sender, "This command is only allowed for players!", true);

                    return true;
                }

                Player player = (Player) sender;

                try {
                    if (player.hasPermission(commands.get(sub).permission())
                            || player.hasPermission(commands.get(sub).kitPermission())
                            || player.hasPermission("jail.*") || player.hasPermission("jail.admin")) {
                        commands.get(sub).onCommand(player, args);

                        return true;
                    } else {
                        Messenger.sendNoPermissionError(sender);

                        return true;
                    }
                } catch (Exception e) {
                    exceptionHandler.logException(e);
                }
            } else {
                try {
                    if (sender.hasPermission(commands.get(sub).permission())
                            || sender.hasPermission(commands.get(sub).kitPermission()) || sender.hasPermission("jpp.*")) {
                        commands.get(sub).onCommand(sender, args);

                        return true;
                    } else {
                        Messenger.sendNoPermissionError(sender);

                        return true;
                    }
                } catch (Exception e) {
                    exceptionHandler.logException(e);
                }
            }
        }

        return false;
    }

    public void help(CommandSender sender) {
        Messenger.sendMessage(sender, "Jail++: Available Commands");

        if (sender instanceof Player) {
            for (SubCommand command : commands.values()) {
                if (sender.hasPermission(command.permission()) || sender.hasPermission(command.kitPermission())
                        || sender.hasPermission("jpp.*")) {
                    Messenger.sendMessage(sender, ChatColor.GRAY + "    - " + command.help(sender));
                }
            }
        } else {
            for (SubCommand command : commands.values()) {
                if (!command.playerOnly()) {
                    Messenger.sendMessage(sender, ChatColor.GRAY + "    - " + command.help(sender));
                }
            }
        }
    }
}
