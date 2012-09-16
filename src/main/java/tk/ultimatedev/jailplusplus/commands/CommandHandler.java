package tk.ultimatedev.jailplusplus.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * @author YoshiGenius
 */
public class CommandHandler implements CommandExecutor {

        private Plugin plugin;
    private HashMap<String, SubCommand> commands;

    public CommandHandler(Plugin plugin)
    {
        this.plugin = plugin;
        commands = new HashMap<String, SubCommand>();
        loadCommands();
    }

    private void loadCommands()
    {
        // help is automatically included.
        // commands.put("list", new JailList());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd1, String commandLabel, String[] args){
        String cmd = cmd1.getName();
        PluginDescriptionFile pdfFile = plugin.getDescription();
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            return true;
        }
        if(cmd.equalsIgnoreCase("jail")){ 
            if (!player.hasPermission("jail.main")) {player.sendMessage("You don't have permission!"); return true;}
            if(args == null || args.length < 1){
                player.sendMessage(ChatColor.GOLD +""+ ChatColor.BOLD +"Jail++ "+ChatColor.RESET+  ChatColor.YELLOW +" Version: "+ pdfFile.getVersion() );
                player.sendMessage(ChatColor.GOLD +"Type /jail help for help" );

                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                help(player);
                return true;
            }
            String sub = args[0];

            Vector<String> l  = new Vector<String>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = (String[]) l.toArray(new String[0]);
            try {
                if (player.hasPermission(commands.get(sub).permission()) || player.hasPermission(commands.get(sub).kitPermission()) || player.hasPermission("jail.*") || player.hasPermission("jail.admin")) {
                    commands.get(sub).onCommand( player,  args);
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have access to that command.");
                }
            } catch(Exception e) {e.printStackTrace(); player.sendMessage(ChatColor.RED+"An error occured while executing the command. Ask an admin to check the console"); player.sendMessage(ChatColor.BLUE + "Type /jail help for help" );
}
            return true;
        }
        return false;
    }
    
    public void help(Player p){
        p.sendMessage("/jail <command> <args>");
        for(SubCommand v: commands.values()){
            p.sendMessage(ChatColor.AQUA + v.help(p));
        }
    }

}
