package tk.ultimatedev.jailplusplus.util;

//~--- non-JDK imports --------------------------------------------------------

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A class that contains methods to easily
 * send messages to the console and to players
 * on a Bukkit server. Includes support for prefixes.
 *
 * @author Sushi
 */
public class Messenger {
    private static String chatPrefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "Jail++" + ChatColor.GRAY + "] "
            + ChatColor.RESET;

    /**
     * Send a message to a CommandSender (can be
     * either a console or a player).
     *
     * @param sender   The CommandSender to send the message to.
     * @param message  The message that is to be sent.
     * @param prefixed Whether the message should be prefixed
     *                 with a tag that is the name of your plugin
     *                 or not.
     */
    public static void sendMessage(CommandSender sender, String message, boolean prefixed) {
        if (sender == null) {
            throw new NullPointerException("I tried to send a message to a null player. Oops.");
        } else {
            if (prefixed) {
                sender.sendMessage(chatPrefix + message);
            } else {
                sender.sendMessage(message);
            }
        }
    }

    /**
     * Send a message to a CommandSender (can be
     * either a console or a player). This message is
     * automatically prefixed.
     *
     * @param sender  The CommandSender to send the message to.
     * @param message The message that is to be sent.
     */
    public static void sendMessage(CommandSender sender, String message) {
        sendMessage(sender, message, true);
    }

    /**
     * Send a message to the console.
     *
     * @param message  The message that is to be sent.
     * @param prefixed Whether the message should be prefixed
     *                 with a tag that is the name of your plugin
     *                 or not.
     */
    public static void sendMessageToConsole(String message, boolean prefixed) {
        sendMessage(Bukkit.getConsoleSender(), message, prefixed);
    }

    /**
     * Send a message to the console. This message is
     * automatically prefixed.
     *
     * @param message The message that is to be sent.
     */
    public static void sendMessageToConsole(String message) {
        sendMessage(Bukkit.getConsoleSender(), message, true);
    }

    /**
     * Send an error message to the console (the message
     * is red colored).
     *
     * @param message  The message that is to be sent.
     * @param prefixed Whether the message should be prefixed
     *                 with a tag that is the name of your plugin
     *                 or not.
     */
    public static void sendErrorToConsole(String message, boolean prefixed) {
        sendError(Bukkit.getConsoleSender(), message, prefixed);
    }

    /**
     * Send an error message to the console (the message
     * is red colored). This message is automatically
     * prefixed.
     *
     * @param message The message that is to be sent.
     */
    public static void sendErrorToConsole(String message) {
        sendError(Bukkit.getConsoleSender(), message, true);
    }

    /**
     * Send an error (the message is red colored) message
     * to a CommandSender (can be a player or console).
     *
     * @param sender   The CommandSender to send the message to.
     * @param message  The message that is to be sent.
     * @param prefixed Whether the message should be prefixed
     *                 with a tag that is the name of your plugin
     *                 or not.
     */
    public static void sendError(CommandSender sender, String message, boolean prefixed) {
        sendMessage(sender, ChatColor.RED + "Error: " + ChatColor.RESET + message, prefixed);
    }

    /**
     * Send an error (the message is red colored) message
     * to a CommandSender (can be a player or console).
     * This message is automatically prefixed.
     *
     * @param sender  The CommandSender to send the message to.
     * @param message The message that is to be sent.
     */
    public static void sendError(CommandSender sender, String message) {
        sendError(sender, message, true);
    }

    /**
     * Send an error (the message is red colored) message
     * to a CommandSender (can be a player or console). The
     * message is automatically prefixed and explains that
     * they do not have permission to perform a certain
     * action.
     *
     * @param sender The CommandSender to send the message to.
     */
    public static void sendNoPermissionError(CommandSender sender) {
        sendError(sender, "You don't have enough permission to do this!");
    }

    /**
     * Broadcast a message to all players on the server.
     *
     * @param message  The message that is to be sent.
     * @param prefixed Whether the message should be prefixed
     *                 with a tag that is the name of your plugin
     *                 or not.
     */
    public static void broadcastMessage(String message, boolean prefixed) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            sendMessage(p, message, prefixed);
        }
    }

    /**
     * Broadcast a message to all players on the server.
     * This message is automatically prefixed.
     *
     * @param message The message that is to be sent.
     */
    public static void broadcastMessage(String message) {
        broadcastMessage(message, true);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
