package tk.ultimatedev.jailplusplus.util;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.JailPlugin;

/**
 * @author YoshiGenius
 */
public class FilePaths {
    private static FilePaths instance;

    public String getFolderPath() {
        return this.getFolder().getPath();
    }

    public File getFolder() {
        return JailPlugin.getPlugin().getDataFolder();
    }

    public File getJailsFile() {
        return new File(this.getFolder(), "jails.yml");
    }

    public File getCellsFile() {
        return new File(this.getFolder(), "cells.yml");
    }

    public File getPrisonersFile() {
        return new File(this.getFolder(), "prisoners.yml");
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
    
    public String getCellPath(int jid, int cid) {
        return jid + "." + cid + ".";
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
