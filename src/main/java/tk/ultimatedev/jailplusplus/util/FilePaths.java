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
    
    public File getJailsFile() {
        return new File(getFolderPath() + "jails.yml");
    }
    
    public File getCellsFile() {
        return new File(getFolderPath() + "cells.yml");
    }
    
    public File getPrisonersFile() {
        return new File(getFolderPath() + "prisoners.yml");
    }

    public String getPrisonersPath(String playername) {
        return playername + ".";
    }

    public String getJailPath(String jailname) {
        return jailname + ".";
    }
    
    public String getCellPath(String jailname, String cellname) {
        return jailname + "." + cellname + ".";
    }

    public YamlConfiguration getPrisonersFileConf() {
        File file = this.getPrisonersFile();
        return YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getJailFileConf() {
        File file = this.getJailsFile();
        return YamlConfiguration.loadConfiguration(file);
    }
    
    public YamlConfiguration getCellFileConf() {
        File file = this.getCellsFile();
        return YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration loadConf(File f) {
        return YamlConfiguration.loadConfiguration(f);
    }

    public static FilePaths getInstance() {
        return instance;
    }

}
