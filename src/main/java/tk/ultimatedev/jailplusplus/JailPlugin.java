package tk.ultimatedev.jailplusplus;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;
import tk.ultimatedev.jailplusplus.models.Migrant;
import tk.ultimatedev.jailplusplus.util.Log;

public class JailPlugin extends JavaPlugin {
    private static JailPlugin plugin;

    @Override
    public void onEnable() {
        JailPlugin.plugin = this;

        File f = new File(this.getDataFolder(), "config.yml");
        if (!f.exists()) {
            this.saveDefaultConfig();
        }
        this.reloadConfig();

        Migrant migrant = new Migrant();
        migrant.migrate();

        Log.info("Successfully enabled version " + this.getDescription().getVersion() + "!");
    }

    @Override
    public void onDisable() {
        Log.info("Successfully disabled version " + this.getDescription().getVersion() + "!");
    }

    public static JailPlugin getPlugin() {
        return plugin;
    }
}
