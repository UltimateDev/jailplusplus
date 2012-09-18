package tk.ultimatedev.jailplusplus.models;

import org.bukkit.Bukkit;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.SettingsManager;
import tk.ultimatedev.jailplusplus.util.Log;

import java.sql.*;

/**
 * @author Sushi
 */
public class Migrant {
    DatabaseEngine engine;
    String host;
    int port;
    String user;
    String password;
    String database;
    String prefix;

    Connection conn;
    Statement st;
    ResultSet rs;
    DatabaseMetaData meta;

    ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());

    public enum DatabaseEngine {
        H2, MYSQL, FILE
    }

    public Migrant() {
        if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("h2")) {
            this.engine = DatabaseEngine.H2;
        } else if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("mysql")) {
            this.engine = DatabaseEngine.MYSQL;
        } else if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("file")) {
            this.engine = DatabaseEngine.FILE;
        } else {
            Log.severe("Invalid database driver type! Must be \"h2\", \"mysql\", or \"file\"! Assuming h2...");
            this.engine = DatabaseEngine.H2;
        }

        this.host = JailPlugin.getPlugin().getConfig().getString("database.host");
        this.user = JailPlugin.getPlugin().getConfig().getString("database.user");
        this.password = JailPlugin.getPlugin().getConfig().getString("database.password");
        this.port = JailPlugin.getPlugin().getConfig().getInt("database.port");
        this.database = JailPlugin.getPlugin().getConfig().getString("database.database");
        this.prefix = JailPlugin.getPlugin().getConfig().getString("database.prefix");
    }

    public static DatabaseEngine getDatabaseEngine() {
        if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("h2")) {
            return DatabaseEngine.H2;
        } else if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("mysql")) {
            return DatabaseEngine.MYSQL;
        } else if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("file")) {
            return DatabaseEngine.FILE;
        } else {
            Log.severe("Invalid database driver type! Must be \"h2\", \"mysql\", or \"file\"! Assuming h2...");
            return DatabaseEngine.H2;
        }
    }

    public void migrate() {
        switch (engine) {
            case H2:
                Log.info("Running H2 migrations...");

                try {
                    Connection conn = getConnection();

                    /*
                    * *** JAIL MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[]{this.prefix + "jails"});

                    if (rs.next()) {
                        Log.info("[Database] Found jails table.....");
                    } else {
                        Log.info("[Database] Attempting to create table `" + this.prefix + "jails`.....");

                        st = conn.createStatement();
                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS " + this.prefix + "jails" +
                                        "(" +
                                        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                        "name VARCHAR(32) NOT NULL UNIQUE," +
                                        "x1 INT NOT NULL," +
                                        "x2 INT NOT NULL," +
                                        "y1 INT NOT NULL," +
                                        "y2 INT NOT NULL," +
                                        "z1 INT NOT NULL," +
                                        "z2 INT NOT NULL," +
                                        "world VARCHAR(50)" +
                                        ")"
                        );

                    }

                    /*
                    * *** PRISONER MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[]{this.prefix + "prisoners"});

                    if (rs.next()) {
                        Log.info("[Database] Found prisoners table.....");
                    } else {
                        Log.info("[Database] Attempting to create table `" + this.prefix + "prisoners`.....");

                        st = conn.createStatement();
                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS " + this.prefix + "prisoners" +
                                        "(" +
                                        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                        "name VARCHAR(32) NOT NULL UNIQUE," +
                                        "cell INT NOT NULL," +
                                        "created BIGINT," +
                                        "expires BIGINT," +
                                        "reason VARCHAR(150)," +
                                        "jailer VARCHAR(20)," +
                                        "inv CLOB," +
                                        "x INT," +
                                        "y INT," +
                                        "z INT," +
                                        "world INT" +
                                        ")"
                        );
                    }

                    /*
                    * *** CELLS MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[]{this.prefix + "cells"});

                    if (rs.next()) {
                        Log.info("[Database] Found cells table.....");
                    } else {
                        Log.info("[Database] Attempting to create table `" + this.prefix + "cells`.....");

                        st = conn.createStatement();
                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS " + this.prefix + "cells" +
                                        "(" +
                                        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                        "jail INT NOT NULL," +
                                        "x1 INT NOT NULL," +
                                        "x2 INT NOT NULL," +
                                        "y1 INT NOT NULL," +
                                        "y2 INT NOT NULL," +
                                        "z1 INT NOT NULL," +
                                        "z2 INT NOT NULL," +
                                        ")"
                        );
                    }

                    Log.info("[Database] Done!");
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    Log.info("Disabling...");
                    Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }

                    } catch (SQLException ex) {
                        exceptionHandler.logException(ex);
                        Log.info("Disabling...");
                        Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                    }
                }

                break;
            case MYSQL:
                Log.info("Running MySQL migrations...");

                try {
                    Connection conn = getConnection();

                    /*
                    * *** JAIL MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[]{this.prefix + "jails"});

                    if (rs.next()) {
                        Log.info("[Database] Found jails table.....");
                    } else {
                        Log.info("[Database] Attempting to create table `" + this.prefix + "jails`.....");

                        st = conn.createStatement();
                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS " + this.prefix + "prisoners" +
                                        "(" +
                                        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                        "name VARCHAR(32) NOT NULL UNIQUE," +
                                        "cell INT NOT NULL," +
                                        "created BIGINT," +
                                        "sentence BIGINT," +
                                        "served BIGINT," +
                                        "reason VARCHAR(150)," +
                                        "jailer VARCHAR(20)," +
                                        "inv CLOB," +
                                        "x INT," +
                                        "y INT," +
                                        "z INT," +
                                        "world INT" +
                                        ")"
                        );

                    }

                    /*
                    * *** PRISONER MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[]{this.prefix + "prisoners"});

                    if (rs.next()) {
                        Log.info("[Database] Found prisoners table.....");
                    } else {
                        Log.info("[Database] Attempting to create table `" + this.prefix + "prisoners`.....");

                        st = conn.createStatement();
                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS " + this.prefix + "prisoners" +
                                        "(" +
                                        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                        "name VARCHAR(32) NOT NULL UNIQUE," +
                                        "cell INT NOT NULL," +
                                        "created BIGINT," +
                                        "expires BIGINT," +
                                        "reason VARCHAR(150)," +
                                        "jailer VARCHAR(20)," +
                                        "inv TEXT" +
                                        ")"
                        );
                    }

                    /*
                    * *** CELLS MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[]{this.prefix + "cells"});

                    if (rs.next()) {
                        Log.info("[Database] Found cells table.....");
                    } else {
                        Log.info("[Database] Attempting to create table `" + this.prefix + "cells`.....");

                        st = conn.createStatement();
                        st.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS " + this.prefix + "cells" +
                                        "(" +
                                        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                        "jail INT NOT NULL," +
                                        "x1 INT NOT NULL," +
                                        "x2 INT NOT NULL," +
                                        "y1 INT NOT NULL," +
                                        "y2 INT NOT NULL," +
                                        "z1 INT NOT NULL," +
                                        "z2 INT NOT NULL," +
                                        ")"
                        );
                    }

                    Log.info("[Database] Done!");
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    Log.info("Disabling...");
                    Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }

                    } catch (SQLException ex) {
                        exceptionHandler.logException(ex);
                        Log.info("Disabling...");
                        Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                    }
                }

                break;
            case FILE:
                Log.info("So you chose FlatFile, eh? Very well then.");
                SettingsManager settings = new SettingsManager(JailPlugin.getPlugin());
                settings.firstRun();
                // File f = new File(folderpath + "jails/Steve.yml");
                break;
            default:
                Log.severe("You specified the database engine '" + JailPlugin.getPlugin().getConfig().getString("database.driver") + "', but the only supported databases are 'h2', 'mysql', and 'file'! Disabling plugin...");
                Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());

                break;
        }
    }

    private Connection getConnection() throws SQLException {
        switch (engine) {
            case H2:
                try {
                    Class.forName("org.h2.Driver");

                    return DriverManager.getConnection("jdbc:h2:" + JailPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/jpp_data", "jpp", "");
                } catch (ClassNotFoundException ex) {
                    exceptionHandler.logException(ex);
                    Log.severe("You specified the database engine 'h2', but I couldn't find the driver \"org.h2.Driver\"! Disabling plugin...");
                    Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                    return null;
                }
            case MYSQL:
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database);
                } catch (ClassNotFoundException ex) {
                    exceptionHandler.logException(ex);
                    Log.severe("You specified the database engine 'mysql', but I couldn't find the driver \"com.mysql.jdbc.Driver\"! Disabling plugin...");
                    Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                    return null;
                }
            default:
                Log.severe("You specified the database engine '" + JailPlugin.getPlugin().getConfig().getString("database.driver") + "', but the only supported databases are 'h2', 'mysql', and 'file'! Disabling plugin...");
                Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                return null;
        }
    }
}
