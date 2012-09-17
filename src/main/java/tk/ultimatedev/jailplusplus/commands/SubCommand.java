package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.JailPlugin;

/**
 * @author YoshiGenius
 */
public interface SubCommand {
    
    JailPlugin jp = new JailPlugin();
    
    /**
     * Executes a command.
     * @param cs The person who sent the command.
     * @param args The arguments that were involved in the command.
     * @return boolean Whether execution was successful or not.
     */

    public boolean onCommand(CommandSender cs, String[] args);
    
    /**
     * The message to send to the player with the help content.
     * @param player Player to send the message to.
     * @return String The help text.
     */

    public String help(CommandSender cs);
    
    /**
     * The permission required to use the subcommand.
     * @return String Permission required to use command.
     */
    
    public String permission();
    
    /**
     * The kit permission that can be used to use the subcommand.
     * @return String Kit permission optionally for command.
     */
    
    public String kitPermission();
    
    /**
     * True for player only, false for console too.
     * @return boolean Can only a player use the command?
     */
    
    public boolean playerOnly();

}
