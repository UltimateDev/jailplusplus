package tk.ultimatedev.jailplusplus.util.file;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author YoshiGenius
 */
public class FilePaths {
    private static FilePaths instance;
    
    public String getFolderPath() {
        return "plugins/JailPlusPlus/";
    }
    
    public String getUserdataFolderPath() {
        return (getFolderPath() + "userdata/");
    }
    
    public String getJailsFolderPath() {
        return (getFolderPath() + "jails/");
    }
    
    public File getUserYAMLFile(String playername) {
        File file = new File(getUserdataFolderPath() + playername + ".yml");
        return file;
    }
    
    public File getJailYAMLFile(String jailname) {
        File file = new File(getJailsFolderPath() + jailname + ".yml");
        return file;
    }
    
    public YamlConfiguration getUserYAMLConf(String playername) {
        File file = new File(getUserdataFolderPath() + playername + ".yml");
        YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
        return c;
    }
    
    public YamlConfiguration getJailYAMLConf(String jailname) {
        File file = new File(getJailsFolderPath() + jailname + ".yml");
        YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
        return c;
    }
    
    public YamlConfiguration loadConf(File f) {
        YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
        return c;
    }
    
    public static FilePaths getInstance() {
        return instance;
    }
    
}
