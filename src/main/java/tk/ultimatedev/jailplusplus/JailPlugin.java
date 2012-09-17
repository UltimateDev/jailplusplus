package tk.ultimatedev.jailplusplus;

import org.bukkit.plugin.java.JavaPlugin;
import tk.ultimatedev.jailplusplus.models.Migrant;
import tk.ultimatedev.jailplusplus.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import tk.ultimatedev.jailplusplus.commands.CommandHandler;

public class JailPlugin extends JavaPlugin {
    private static JailPlugin plugin;

    @Override
    public void onEnable() {
        JailPlugin.plugin = this;

        getDependencies();
        
        this.getCommand("jail").setExecutor(new CommandHandler(this));

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
                URL h2Url = new URL("http://search.maven.org/remotecontent?filepath=com/h2database/h2/1.3.168/h2-1.3.168.jar");
                ReadableByteChannel readableByteChannel = Channels.newChannel(h2Url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream("lib/h2-1.3.168.jar");

                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, 1 << 24);
            } catch (MalformedURLException ex) {
                exceptionHandler.logException(ex);
            } catch (FileNotFoundException ex) {
                exceptionHandler.logException(ex);
            } catch (IOException ex) {
                exceptionHandler.logException(ex);
            }
        }
    }

    public static JailPlugin getPlugin() {
        return plugin;
    }
}
