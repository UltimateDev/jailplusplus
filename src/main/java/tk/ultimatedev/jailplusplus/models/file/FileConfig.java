package tk.ultimatedev.jailplusplus.models.file;

import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;

import java.io.File;

/**
 * @author YoshiGenius
 */
public class FileConfig extends YamlConfiguration {

    protected File file;

    public FileConfig(File file) {
        super();
        this.options().pathSeparator();
        this.file = file;
        this.reload();
    }

    public File getFile() {
        return file;
    }

    private void reload() {
        try {
            this.load(file);
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
        }
    }

    public void save() {
        try {
            this.save(file);
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
        }
    }
}
