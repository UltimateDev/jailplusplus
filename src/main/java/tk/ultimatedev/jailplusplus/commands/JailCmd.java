package tk.ultimatedev.jailplusplus.commands;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.Cell;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.models.Prisoner;
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

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return false;
    }

    /*
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
    */

}
