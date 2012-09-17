package tk.ultimatedev.jailplusplus.models.file;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.util.FilePaths;

/**
 * @author YoshiGenius
 */
public class JailYAML {
    
    private static Jail jail;
    
    public JailYAML(Jail jail) {
        JailYAML.jail = jail;
    }
    
    public YamlConfiguration getJailConf() {
        return FilePaths.getInstance().getJailYAMLConf(jail.getName());
    }
    
    public File getJailFile() {
        return FilePaths.getInstance().getJailYAMLFile(jail.getName());
    }

}
