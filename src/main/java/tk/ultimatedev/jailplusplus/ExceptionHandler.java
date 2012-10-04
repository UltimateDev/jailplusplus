package tk.ultimatedev.jailplusplus;

import org.bukkit.plugin.Plugin;
import tk.ultimatedev.jailplusplus.util.Log;

import java.io.File;

/**
 * @author YoshiGenius
 */
public class ExceptionHandler {

    Plugin plugin;

    public ExceptionHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void logException(Exception exception) {
        Log.info("ERROR: Post all of this on the " + plugin.getName() + " BukkitDev page!");
        Log.info("ERROR TYPE: " + exception.getCause());
        Log.info("ERROR MSG: " + exception.getMessage());
        Log.info("---STACK TRACE START---");
        exception.printStackTrace();
        Log.info("---STACK TRACE FINISH---");
        Log.info("ERROR: Post all of this on the " + plugin.getName() + " Bukkit Dev page in a ticket or a comment.");
    }

    public boolean makeFile(File f) {
        if (!f.exists()) {
            try {
                f.createNewFile();
                return true;
            } catch (Exception e) {
                this.logException(e);
                return false;
            }
        } else {
            return true;
        }
    }

}
