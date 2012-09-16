package tk.ultimatedev.jailplusplus.commands;

import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.JailPlugin;

/**
 * @author YoshiGenius
 */
public interface SubCommand {
    
    JailPlugin jp = new JailPlugin();
    
    /**
     * Executes a command.
     * @param player The player who sent the command.
     * @param args The arguments that were involved in the command.
     * @return boolean Whether execution was successful or not.
     */

    public boolean onCommand(Player player, String[] args);
    
    /**
     * The message to send to the player with the help content.
     * @param player Player to send the message to.
     * @return String The help text.
     */

    public String help(Player player);
    
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

}
