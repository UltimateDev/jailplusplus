package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Messenger;

/**
 * @author Sushi
 */

// FORMAT: /jail player time jail/cell reason
public class JailCmd implements CommandExecutor {
    String playerName;
    int time;
    String cell;
    int cellId;
    String jail;
    int jailId;
    String reason;

    ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] arg) {
        if (arg[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Jail Command Usage");
            sender.sendMessage(ChatColor.DARK_AQUA + help(sender));
            return true;
        }
        Player playerToJail = Bukkit.getPlayer(arg[0]);
        if (playerToJail == null) {
            Messenger.sendError(sender, "ERROR: Player not found.", true); 
            return true;
        }
        this.playerName = playerToJail.getName();
        
        return false;
    }

    public String help(CommandSender cs) {
        return "/jail [player] [jail-name] [cell] [time] [reason]";
    }

    public String permission() {
        return "jpp.jail";
    }

    public String kitPermission() {
        return "jpp.admin";
    }

    public boolean playerOnly() {
        return false;
    }
    
    public static String remainingWords(String[] wordArray, int startWord) {
    	String remaining = "";
    	for (int i=startWord; i<wordArray.length; i++) {
    		remaining = remaining.trim() + " " + wordArray[i];
    	}
    	return remaining.trim();
    }

}
