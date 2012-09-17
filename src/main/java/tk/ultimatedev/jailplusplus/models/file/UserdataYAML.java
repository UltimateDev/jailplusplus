package tk.ultimatedev.jailplusplus.models.file;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.util.FilePaths;

/**
 * @author YoshiGenius
 */
public class UserdataYAML {
    
    private static Player player;
    
    public UserdataYAML(Player player) {
        UserdataYAML.player = player;
    }
    
    public File getUserdataFile() {
        return FilePaths.getInstance().getUserYAMLFile(player.getName());
    }
    
    public YamlConfiguration getUserdataConf() {
        return FilePaths.getInstance().getUserYAMLConf(player.getName());
    }

}