package tk.ultimatedev.jailplusplus;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import tk.ultimatedev.jailplusplus.models.Migrant;
import tk.ultimatedev.jailplusplus.models.Migrant.DatabaseEngine;
import tk.ultimatedev.jailplusplus.util.FilePaths;


/**
 * @author YoshiGenius
 */
public class SettingsManager {
    
    Plugin plugin;
    File jailFile = null;
    File cellFile = null;
    File prisonerFile = null;
    FileConfiguration jailConf = null;
    FileConfiguration cellConf = null;
    FileConfiguration prisonerConf = null;

    public SettingsManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public File getConfigFile() {
        File f = new File("plugins/" + plugin.getName() + "/config.yml");
        return f;
    }

    public File getJailsFile() {
        return FilePaths.getInstance().getJailsFile();
    }
    
    public File getPrisonersFile() {
        return FilePaths.getInstance().getPrisonersFile();
    }
    
    public File getCellsFile() {
        return FilePaths.getInstance().getCellsFile();
    }

    public boolean firstRun() {
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdir()) {
                return false;
            }
        }
        if (Migrant.getDatabaseEngine() != DatabaseEngine.FILE) return true;
        if (!this.setupYAMLFiles()) return false;
        if (!this.getCellsFile().exists()) {
            ExceptionHandler eh = new ExceptionHandler(this.plugin);
            if (!eh.makeFile(this.getCellsFile())) return false;
        }
        if (!this.getJailsFile().exists()) {
            ExceptionHandler eh = new ExceptionHandler(this.plugin);
            if (!eh.makeFile(this.getJailsFile())) return false;
        }
        if (!this.getPrisonersFile().exists()) {
            ExceptionHandler eh = new ExceptionHandler(this.plugin);
            if (!eh.makeFile(this.getPrisonersFile())) return false;
        }
        return true;
    }

    public boolean setupYAMLFiles() {
        JailPlugin j = new JailPlugin();
        return j.setupConfigs();
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
    
    public FileConfiguration getJailsConfig() {
        JailPlugin j = new JailPlugin();
        if (this.jailConf == null) {
            j.setupConfigs();
        }
        return this.jailConf;
    }
    
    public FileConfiguration getPrisonersConfig() {
        JailPlugin j = new JailPlugin();
        if (this.prisonerConf == null) {
            j.setupConfigs();
        }
        return this.prisonerConf;
    }
    
    public FileConfiguration getCellsConfig() {
        JailPlugin j = new JailPlugin();
        if (this.cellConf == null) {
            j.setupConfigs();
        }
        return this.cellConf;
    }
    
}
