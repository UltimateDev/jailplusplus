package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Messenger;

/**
 * @author Sushi
 */
public class About implements SubCommand {
    public boolean onCommand(Player player, String[] args) {
        String delimiter = "";
        StringBuilder stringBuilder = new StringBuilder();

        for (String author : JailPlugin.getPlugin().getDescription().getAuthors()) {
            stringBuilder.append(delimiter).append(author);
            delimiter = ",";
        }

        Messenger.sendMessage(player, "Jail++ v" + JailPlugin.getPlugin().getDescription().getVersion());
        Messenger.sendMessage(player, "Created with <3 by " + stringBuilder.toString() + "!");
        Messenger.sendMessage(player, null, false);
        Messenger.sendMessage(player, "For help using this plugin, type /jail help", false);
        return false;
    }

    public String help(Player player) {
        return "/jail about - See information about the plugin.";
    }

    public String permission() {
        return "jpp.about";
    }

    public String kitPermission() {
        return "jpp.default";
    }
}
