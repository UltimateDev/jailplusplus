package tk.ultimatedev.jailplusplus;

//~--- non-JDK imports --------------------------------------------------------

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tk.ultimatedev.jailplusplus.commands.CommandHandler;
import tk.ultimatedev.jailplusplus.handlers.JailStickHandler;
import tk.ultimatedev.jailplusplus.models.Migrant;
import tk.ultimatedev.jailplusplus.task.TaskScheduler;
import tk.ultimatedev.jailplusplus.util.Log;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class JailPlugin extends JavaPlugin {
    private static JailPlugin plugin;

    @Override
    public void onEnable() {

        // - Assign plugin - \\
        JailPlugin.plugin = this;

        // - Download H2 - \\
        getDependencies();

        // - Start UpdateChecker - \\
//      checkForUpdates();
        // - Load Listeners - \\
        this.loadListeners();

//      String stringurl = "http://dev.bukkit.org/server-mods/jailplusplus.rss";
//      UpdateChecker uc = new UpdateChecker(this, stringurl);
//      if (uc.updateNeeded()) {
//          if (this.getConfig().getString("update.stream").equalsIgnoreCase("")) {
//              Log.info("A new version is available: " + uc.getVersion());
//              Log.info("Get it from: " + uc.getLink());
//          }
//      }
        // - Command - \\
        this.getCommand("jail").setExecutor(new CommandHandler(this));

        // - Setting up config - \\
        File f = new File(this.getDataFolder(), "config.yml");

        if (!f.exists()) {
            this.saveDefaultConfig();
        }

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
                FileOutputStream    fileOutputStream    = new FileOutputStream("lib/h2-1.3.168.jar");

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

    public void checkForUpdates() {
        String        stringurl = "http://dev.bukkit.org/server-mods/jailplusplus.rss";
        UpdateChecker uc        = new UpdateChecker(this, stringurl);

        if (uc.updateNeeded()) {
            if (this.getConfig().getString("update.stream").equalsIgnoreCase("release")
                    && uc.getVersion().startsWith("r")) {
                Log.info("A new version is available: " + uc.getVersion());
                Log.info("Get it from: " + uc.getLink());
            } else if (this.getConfig().getString("update.stream").equalsIgnoreCase("beta")
                       && (uc.getVersion().startsWith("b") || uc.getVersion().startsWith("r"))) {
                Log.info("A new version is available: " + uc.getVersion());
                Log.info("Get it from: " + uc.getLink());
            } else if (this.getConfig().getString("update.stream").equalsIgnoreCase("alpha")
                       && (uc.getVersion().startsWith("b") || uc.getVersion().startsWith("r")
                           || uc.getVersion().startsWith("a"))) {
                Log.info("A new version is available: " + uc.getVersion());
                Log.info("Get it from: " + uc.getLink());
            } else if (this.getConfig().getString("update.stream").equalsIgnoreCase("dev")
                       && (uc.getVersion().startsWith("b") || uc.getVersion().startsWith("r")
                           || uc.getVersion().startsWith("a") || uc.getVersion().startsWith("d"))) {
                Log.info("A new version is available: " + uc.getVersion());
                Log.info("Get it from: " + uc.getLink());
            } else {
                Log.info("Not a valid stream. Telling you anyway.");
                Log.info("A new version is available: " + uc.getVersion());
                Log.info("Get it from: " + uc.getLink());
            }
        }
    }

    public void loadListeners() {
        PluginManager pm = this.getServer().getPluginManager();
        
        pm.registerEvents(new JailStickHandler(), this);
    }
}
