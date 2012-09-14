package tk.ultimatedev.jailplusplus.util;

import tk.ultimatedev.jailplusplus.JailPlugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Config {
    File confFile;
    PrintWriter scribe;
//config and crap like that :O
    public Config() {
        this.confFile = new File(JailPlugin.getPlugin().getDataFolder() + "config.yml");
    }

    public void saveDefault() {
        if (!this.confFile.exists()) {
            try {
                if (this.confFile.createNewFile()) {
                    this.writeConfig();
                } else {
                    Log.severe("I couldn't create the configuration file!");
                }
            } catch (IOException e) {
                Log.severe("I couldn't create the configuration file! Create a bug report with the data below:");
                e.printStackTrace();
            }
        }
    }

    private void writeConfig() throws IOException {
        scribe = new PrintWriter(this.confFile);

        scribe.println("# Hi, I'm the Jail++ configuration file.");
        scribe.println("# And I'm a comment! I start with a \"#\", which means I can give you tips on how to configure Jail++.");
        scribe.println();
        scribe.println("# Here you can configure your database. You can use H2 embedded or MySQL!");
        scribe.println("# If you don't have a MySQL database, I would stick with the default settings (H2), which uses an embedded database.");
        scribe.println("database:");
        scribe.println("  driver: h2            # Choose 'h2' or 'mysql'");
        scribe.println("  host: localhost       # Not used for H2. The hostname of the MySQL server.");
        scribe.println("  port: 3306            # Not used for H2. If you don't know what your port is for mysql, it's probably 3306.");
        scribe.println("  user: jailplusplus    # Not used for H2. The user Jail++ will use to connect to the database.");
        scribe.println("  password: example     # Not used for H2. The password of the user set above.");
        scribe.println("  database: minecraft   # Not used for H2. The name of the mysql database to use.");
        scribe.println("  prefix: jpp_          # Not used for H2. You can leave this as default if you don't know what it means.");
        scribe.println();
        scribe.println("# The configuration is done! Don't touch anything in the file or bad things may happen :'(");
        scribe.println("seriously-don't-touch-this: 1");
    }
}
