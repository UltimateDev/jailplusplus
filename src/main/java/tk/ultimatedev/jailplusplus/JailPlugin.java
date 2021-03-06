package tk.ultimatedev.jailplusplus;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.ultimatedev.jailplusplus.commands.CommandHandler;
import tk.ultimatedev.jailplusplus.listener.*;
import tk.ultimatedev.jailplusplus.models.Migrant;
import tk.ultimatedev.jailplusplus.task.TaskScheduler;
import tk.ultimatedev.jailplusplus.util.FilePaths;
import tk.ultimatedev.jailplusplus.util.Log;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import tk.ultimatedev.jailplusplus.commands.JailCmd;

public class JailPlugin extends JavaPlugin {
    private static JailPlugin plugin;

    @Override
    public void onEnable() {
        // - Assign plugin - \\
        JailPlugin.plugin = this;

        // - Download H2 - \\
        getDependencies();

        SettingsManager sm = new SettingsManager(this);
        sm.firstRun();

        // - Load Listeners - \\
        this.loadListeners();

        // - Command - \\
        this.getCommand("jail").setExecutor(new JailCmd());
        this.getCommand("jpp").setExecutor(new CommandHandler(this));

        this.reloadConfig();

        // - Databases - \\
        Migrant migrant = new Migrant();

        migrant.migrate();

        // - Tasks - \\
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.schedule();

        // - Plugin Metrics - \\
        try {
            Metrics metrics = new Metrics(this);

            metrics.start();
        } catch (IOException e) {
            Log.severe("I was unable to submit plugin metrics info! :(");
        }

        // - Enabled! - \\
        Log.info("Successfully enabled version " + this.getDescription().getVersion() + "!");
    }

    @Override
    public void onDisable() {
        Log.info("Successfully disabled version " + this.getDescription().getVersion() + "!");
    }

    public void getDependencies() {
        File libFolder = new File("lib");

        if (!libFolder.exists()) {
            if (!libFolder.mkdirs()) {
                Log.severe("I couldn't create the JailPlusPlus libraries folder!");

                return;
            }
        }

        File h2 = new File("lib/h2-1.3.168.jar");

        if (!h2.exists()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler(this);

            Log.info("I need to download dependencies for Jail++! This should only take a moment...");
            Log.info("==========[ DOWNLOADING H2 DATABASE ]==========");

            try {
                URL h2Url =
                        new URL("http://search.maven.org/remotecontent?filepath=com/h2database/h2/1.3.168/h2-1.3.168.jar");
                ReadableByteChannel readableByteChannel = Channels.newChannel(h2Url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream("lib/h2-1.3.168.jar");

                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, 1 << 24);
            } catch (MalformedURLException ex) {
                exceptionHandler.logException(ex);
            } catch (FileNotFoundException ex) {
                exceptionHandler.logException(ex);
            } catch (IOException ex) {
                exceptionHandler.logException(ex);
            } catch (Exception ex) {
                exceptionHandler.logException(ex);
            }
        }
    }

    public static JailPlugin getPlugin() {
        return plugin;
    }

    public void loadListeners() {
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new JailStickListener(), this);
        pm.registerEvents(new BadassJoinListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new PlayerEscapeListener(), this);
        pm.registerEvents(new PlayerPenaltyListener(), this);
        pm.registerEvents(new WandListener(), this);
    }

    private boolean reloadCellConfig() {
        SettingsManager sm = new SettingsManager(this);
        if (sm.cellFile == null) {
            sm.cellFile = FilePaths.getInstance().getJailsFile();
        }
        sm.cellConf = FilePaths.getInstance().getJailFileConf();

        InputStream stream = this.getResource("cells.yml");
        if (stream != null) {
            YamlConfiguration yconf = YamlConfiguration.loadConfiguration(stream);
            sm.cellConf.setDefaults(yconf);
            return true;
        }
        return false;
    }

    private boolean reloadPrisonerConfig() {
        SettingsManager sm = new SettingsManager(this);
        if (sm.prisonerFile == null) {
            sm.prisonerFile = FilePaths.getInstance().getJailsFile();
        }
        sm.prisonerConf = FilePaths.getInstance().getJailFileConf();

        InputStream jailstream = this.getResource("prisoners.yml");
        if (jailstream != null) {
            YamlConfiguration yconf = YamlConfiguration.loadConfiguration(jailstream);
            sm.prisonerConf.setDefaults(yconf);
            return true;
        }
        return false;
    }

    private boolean reloadJailConfig() {
        SettingsManager sm = new SettingsManager(this);
        if (sm.jailFile == null) {
            sm.jailFile = FilePaths.getInstance().getJailsFile();
        }
        sm.jailConf = FilePaths.getInstance().getJailFileConf();

        InputStream stream = this.getResource("jails.yml");
        if (stream != null) {
            YamlConfiguration yconf = YamlConfiguration.loadConfiguration(stream);
            sm.jailConf.setDefaults(yconf);
            return true;
        }
        return false;
    }

    public boolean setupConfigs() {
        return this.reloadCellConfig() && this.reloadJailConfig() && this.reloadPrisonerConfig();
    }
}
