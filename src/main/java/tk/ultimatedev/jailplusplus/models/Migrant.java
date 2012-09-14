package tk.ultimatedev.jailplusplus.models;

import org.bukkit.Bukkit;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Log;

import java.sql.*;

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

    public enum DatabaseEngine {
        H2, MYSQL
    }
    
    public Migrant() {
        if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("h2")) {
            engine = DatabaseEngine.H2;
        } else if (JailPlugin.getPlugin().getConfig().getString("database.driver").equalsIgnoreCase("mysql")) {
            engine = DatabaseEngine.MYSQL;
        } else {
            Log.severe("Invalid database driver type! Must be \"h2\" or \"mysql\"! Assuming h2...");
            engine = DatabaseEngine.H2;
        }

        this.host = JailPlugin.getPlugin().getConfig().getString("database.host");
        this.user = JailPlugin.getPlugin().getConfig().getString("database.user");
        this.password = JailPlugin.getPlugin().getConfig().getString("database.password");
        this.port = JailPlugin.getPlugin().getConfig().getInt("database.port");
        this.database = JailPlugin.getPlugin().getConfig().getString("database.database");
        this.prefix = JailPlugin.getPlugin().getConfig().getString("database.prefix");
    }

    public void migrate() {
        switch (engine) {
            case H2:
                Log.info("Running H2 migrations...");

                try {
                    Connection conn = getConnection();

                    Log.info("[Database] Done!");
                } catch (SQLException e) {
                    Log.severe("I was unable to complete the database migration! Here's the error info:");
                    e.printStackTrace();
                    Log.info("Disabling...");
                    Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                }
            case MYSQL:
                Log.info("Running MySQL migrations...");

                try {
                    Connection conn = getConnection();

                    /*
                    * *** JAIL MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[] {this.prefix + "jails"});

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
                                        "x1 INT NOT NULL," +
                                        "x2 INT NOT NULL," +
                                        "x1 INT NOT NULL," +
                                        "x2 INT NOT NULL," +
                                        "world VARCHAR(50)" +
                                ")"
                        );

                    }

                    /*
                    * *** PRISONER MIGRATION ***
                    */
                    this.meta = conn.getMetaData();
                    rs = meta.getTables(null, null, null, new String[] {this.prefix + "prisoners"});

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
                                        "sentence BIGINT," +
                                        "served BIGINT," +
                                        "reason VARCHAR(150)," +
                                        "jailer VARCHAR(20)," +
                                        "inv TEXT" +
                                        ")"
                        );
                    }

                    Log.info("[Database] Done!");
                } catch (SQLException e) {
                    Log.severe("I was unable to complete the database migration! Here's the error info:");
                    e.printStackTrace();
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

                    } catch (SQLException e) {
                        Log.severe("I was unable to complete the database migration! Here's the error info:");
                        e.printStackTrace();
                        Log.info("Disabling...");
                        Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                    }
                }
            default:
                Log.severe("You specified the database engine '" + JailPlugin.getPlugin().getConfig().getString("database.driver") + "', but the only supported databases are 'h2' and 'mysql'! Disabling plugin...");
                Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
        }
    }

    private Connection getConnection() throws SQLException {
        switch (engine) {
            case H2:
                try {
                    Class.forName("org.h2.Driver");

                    return DriverManager.getConnection("jdbc:h2:" + JailPlugin.getPlugin().getDataFolder().getAbsolutePath() + "data.h2", "jpp", "");
                } catch (ClassNotFoundException e) {
                    Log.severe("You specified the database engine 'h2', but I couldn't find the driver \"org.h2.Driver\"! Disabling plugin...");
                    Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                    return null;
                }
            case MYSQL:
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database);
                } catch (ClassNotFoundException e) {
                    Log.severe("You specified the database engine 'mysql', but I couldn't find the driver \"com.mysql.jdbc.Driver\"! Disabling plugin...");
                    Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                    return null;
                }
            default:
                Log.severe("You specified the database engine '" + JailPlugin.getPlugin().getConfig().getString("database.driver") + "', but the only supported databases are 'h2' and 'mysql'! Disabling plugin...");
                Bukkit.getPluginManager().disablePlugin(JailPlugin.getPlugin());
                return null;
        }
    }
}
