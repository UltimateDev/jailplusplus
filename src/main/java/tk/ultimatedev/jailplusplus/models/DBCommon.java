package tk.ultimatedev.jailplusplus.models;

import org.bukkit.Bukkit;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCommon {
    private ExceptionHandler exceptionHandler;

    Migrant.DatabaseEngine engine;
    String host;
    int port;
    String user;
    String password;
    String database;
    String prefix;

    public DBCommon() {
        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());

        this.host = JailPlugin.getPlugin().getConfig().getString("database.host");
        this.user = JailPlugin.getPlugin().getConfig().getString("database.user");
        this.password = JailPlugin.getPlugin().getConfig().getString("database.password");
        this.port = JailPlugin.getPlugin().getConfig().getInt("database.port");
        this.database = JailPlugin.getPlugin().getConfig().getString("database.database");
        this.prefix = JailPlugin.getPlugin().getConfig().getString("database.prefix");
    }

    public enum DBResponse {
        ALREADY_EXISTS, SUCCESS, ALREADY_SAVED, FAILURE
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Connection getConnection(Migrant.DatabaseEngine engine) throws SQLException {
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

    public static Connection getConnection() throws SQLException {
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());

        DBCommon common = new DBCommon();
        return common.getConnection(engine);
    }
}
