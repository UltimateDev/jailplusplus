package tk.ultimatedev.jailplusplus.util;

import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.JailPlugin;

import java.io.File;

/**
 * @author YoshiGenius
 */
public class FilePaths {
    private static FilePaths instance;
    
    public String getFolderPath() {
        return "plugins/" + JailPlugin.getPlugin().getDescription().getName() + "/";
    }
    
    public String getUserdataFolderPath() {
        return (getFolderPath() + "userdata/");
    }
    
    public String getJailsFolderPath() {
        return (getFolderPath() + "jails/");
    }
    
    public File getUserdataFolder() {
        return new File(getUserdataFolderPath());
    }
    
    public File getJailsFolder() {
        return new File(getJailsFolderPath());
    }
    
    public File getUserYAMLFile(String playername) {
        return new File(getUserdataFolderPath() + playername + ".yml");
    }
    
    public File getJailYAMLFile(String jailname) {
        return new File(getJailsFolderPath() + jailname + ".yml");
    }
    
    public YamlConfiguration getUserYAMLConf(String playername) {
        File file = new File(getUserdataFolderPath() + playername + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }
    
    public YamlConfiguration getJailYAMLConf(String jailname) {
        File file = new File(getJailsFolderPath() + jailname + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }
    
    public YamlConfiguration loadConf(File f) {
        return YamlConfiguration.loadConfiguration(f);
    }
    
    public static FilePaths getInstance() {
        return instance;
    }
    
}
