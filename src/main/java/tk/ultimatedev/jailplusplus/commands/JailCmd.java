package tk.ultimatedev.jailplusplus.commands;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.models.Cell;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.models.Prisoner;
import tk.ultimatedev.jailplusplus.util.Messenger;

/**
 * @author YoshiGenius
 */
public class JailCmd implements SubCommand {

    public boolean onCommand(CommandSender cs, String[] arg) {
        // FORMAT: /jail player time jail/cell reason
        // FORMAT:  cmd     0     1    2        3
        String playerName = arg[0];
        int sentence = 0;
        long createdtime = System.currentTimeMillis() / 1000;
        ExceptionHandler eh = new ExceptionHandler(jp);
        try {
            sentence = Integer.parseInt(arg[1]);
        } catch (NumberFormatException e) {
            eh.logException(e);
            Messenger.sendError(cs, sentence + " is not a valid number!", true);
            return true;
        }
        int served = 0;
        String jailer;
        if (cs == Bukkit.getConsoleSender()) {
            jailer = "**CONSOLE**";
        } else {
            jailer = cs.getName();
        }
        int created = (int) createdtime;
        String jailcell = arg[2];
        int cellid;
        try {
            cellid = Integer.parseInt(arg[3]);
        } catch (NumberFormatException e) {
            Jail jail = Jail.getJail(jailcell);
            if (jail == null) {
                Messenger.sendError(cs, jailcell + " is not an existing jail!", true);
                return true;
            }
            ArrayList<Cell> cells = new ArrayList<Cell>();
            for (Cell cell : Cell.getAllCells()) {
                if (cell.getJail() == jail) {
                    cells.add(cell);
                }
            }
            ArrayList<Cell> badcells = new ArrayList<Cell>();
            for (Cell cell : cells) {
                for (Prisoner prisoner : Prisoner.getAllPrisoners()) {
                    if (prisoner.getCell() == cell.getID()) {
                        badcells.add(cell);
                    }
                }
            }
            for (Cell cell : badcells) {
                cells.remove(cell);
            }
            badcells.clear();
            int cellamount = cells.size();
            int celltouse = cells.get((new Random()).nextInt(cellamount)).getID();
            cellid = celltouse;
        }
        String reason = JailCmd.remainingWords(arg, 3);
        Prisoner prisoner = new Prisoner(playerName, cellid, created, sentence, served, reason, jailer);
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
