package tk.ultimatedev.jailplusplus;

import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

/**
 * @author YoshiGenius
 */
public class ExceptionHandler {
    
    Plugin plugin;
    final Logger log = plugin.getLogger();
    
    public ExceptionHandler(Plugin plugin) {
        this.plugin = plugin;
    }
    
    public void logException(Exception exception) {
        log.info("ERROR: Post all of this on the " + plugin.getName() + " BukkitDev page!");
        log.info("ERROR TYPE: " + exception.getCause());
        log.info("ERROR MSG: " + exception.getMessage());
        log.info("---STACK TRACE START---"); 
        exception.printStackTrace();
        log.info("---STACK TRACE FINISH---");
        log.info("ERROR: Post all of this on the " + plugin.getName() + " Bukkit Dev page in a ticket or a comment.");
    }

}
