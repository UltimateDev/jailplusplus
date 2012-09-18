package tk.ultimatedev.jailplusplus;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import tk.ultimatedev.jailplusplus.util.FilePaths;


/**
 * @author YoshiGenius
 */
public class SettingsManager {
    
    Plugin plugin;

    public SettingsManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public File getConfigFile() {
        File f = new File("plugins/" + plugin.getName() + "/config.yml");

        return f;
    }

    public File getJailsDir() {
        return FilePaths.getInstance().getFolder();
    }

    public File getJailFile(String name) {
        return FilePaths.getInstance().getJailsFile();
    }

    public File getUserdataDir() {
        return FilePaths.getInstance().getFolder();
    }

    public File getUserdataFile(String name) {
        return FilePaths.getInstance().getPrisonersFile();
    }

    public boolean firstRun() {
        JailPlugin.getPlugin().saveDefaultConfig();

        if (!this.getJailsDir().exists()) {
            if (this.getJailsDir().mkdir()) {
                return false;
            }
        }

        return true;
    }

    public boolean setupYAMLFiles() {
        return false;
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
