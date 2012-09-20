package tk.ultimatedev.jailplusplus.util;

//~--- non-JDK imports --------------------------------------------------------

import org.bukkit.Bukkit;

import tk.ultimatedev.jailplusplus.JailPlugin;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Logger;

/**
 * A class used for accessing the Bukkit logger.
 *
 * @author Sushi
 */
public class Log {
    private static Logger log = Bukkit.getLogger();
    private static String pluginName = JailPlugin.getPlugin().getDescription().getName();

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "severe" log
     * level.
     *
     * @param message The message to log.
     */
    public static void severe(String message) {
        log.severe("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "warning" log
     * level.
     *
     * @param message The message to log.
     */
    public static void warning(String message) {
        log.warning("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "info" log
     * level.
     *
     * @param message The message to log.
     */
    public static void info(String message) {
        log.info("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "config" log
     * level.
     *
     * @param message The message to log.
     */
    public static void config(String message) {
        log.config("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "fine" log
     * level.
     *
     * @param message The message to log.
     */
    public static void fine(String message) {
        log.fine("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "finer" log
     * level.
     *
     * @param message The message to log.
     */
    public static void finer(String message) {
        log.finer("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "finest" log
     * level.
     *
     * @param message The message to log.
     */
    public static void finest(String message) {
        log.finest("[" + pluginName + "] " + message);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
