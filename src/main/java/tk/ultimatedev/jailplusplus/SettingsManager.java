package tk.ultimatedev.jailplusplus;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import tk.ultimatedev.jailplusplus.util.Config;

/**
 * @author YoshiGenius
 */
public class SettingsManager {
    
    Plugin plugin;
    final Logger log = plugin.getLogger();
    
    public SettingsManager(Plugin plugin) {
        this.plugin = plugin;
    }
    
    public File getConfigFile() {
        File f = new File("plugins/" + plugin.getName() + "/config.yml");
        return f;
    }
    
    public File getJailsDir() {
        File f = new File("plugins/" + plugin.getName() + "/jails/");
        return f;
    }
    
    public File getJailFile(String name) {
        File f = new File(this.getJailsDir().getPath() + "/" + name + ".yml");
        return f;
    }
    
    public void firstRun() {
        Config c = new Config();
        c.saveDefault();
    }
    
    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(this.getConfigFile());
    }
    
    public void setConfigOption(String path, Object setto) {
        try {
            YamlConfiguration c = this.getConfig();
            c.set(path, setto);
            c.save(this.getConfigFile());
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(this.plugin);
            eh.logException(e);
        }
    }

}