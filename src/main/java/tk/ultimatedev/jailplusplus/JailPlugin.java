package tk.ultimatedev.jailplusplus;

import org.bukkit.plugin.java.JavaPlugin;
import tk.ultimatedev.jailplusplus.models.Migrant;
import tk.ultimatedev.jailplusplus.util.Config;
import tk.ultimatedev.jailplusplus.util.Log;

public class JailPlugin extends JavaPlugin {
    private static JailPlugin plugin;

    @Override
    public void onEnable() {
        JailPlugin.plugin = this;

        Config config = new Config();
        config.saveDefault();

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
