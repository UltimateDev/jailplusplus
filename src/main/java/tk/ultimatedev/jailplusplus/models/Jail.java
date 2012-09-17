package tk.ultimatedev.jailplusplus.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.file.JailYAML;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.FilePaths;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Jail {
    DBCommon dbCommon;
    Migrant.DatabaseEngine engine;
    boolean saved;
    String tableName;

    int id;
    String name;
    int x1;
    int x2;
    int y1;
    int y2;
    int z1;
    int z2;
    String world;

    ExceptionHandler exceptionHandler;
    File file;

    public Jail(String name, int x1, int x2, int y1, int y2, int z1, int z2, String world) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.name = name;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
        this.world = world;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        // this.file = FilePaths.getInstance().getJailYAMLFile(this.name);

        // Set to null to prevent NPE:
        this.file = null;
    }

    public Jail(String name, Cuboid area) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.name = name;
        this.x1 = area.getMinX();
        this.x2 = area.getMaxX();
        this.y1 = area.getMinY();
        this.y2 = area.getMaxY();
        this.z1 = area.getMinZ();
        this.z2 = area.getMaxZ();
        this.world = area.getWorld().getName();

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        // this.file = FilePaths.getInstance().getJailYAMLFile(this.name);

        // Set to null to prevent NPE:
        this.file = null;
    }

    @Override
    public String toString() {
        return "#" + this.name + ":" + this.x1 + ":" + this.x2 + ":" + this.y1 + ":" + this.y2 + ":" + this.z1 + ":" + this.z2 + ":" + this.world;
    }

    public DBCommon.DBResponse save() throws NullPointerException {
        if (!this.saved) {
            if (this.name != null && this.x1 != 0 && this.x2 != 0 && this.y1 != 0 && this.y2 != 0 && this.z1 != 0 && this.z2 != 0) {
                DBCommon common = new DBCommon();
                Connection conn = null;
                PreparedStatement pst = null;
                ResultSet rs = null;

                switch (this.engine) {
                    case H2:
                        try {
                            conn = common.getConnection(this.engine);

                            pst = conn.prepareStatement("SELECT * FROM " + this.tableName + " WHERE name=?");
                            pst.setString(1, this.name);

                            rs = pst.executeQuery();

                            if (rs.next()) {
                                return DBCommon.DBResponse.ALREADY_EXISTS;
                            } else {
                                pst = conn.prepareStatement("INSERT INTO " + this.tableName + " (name, x1, x2, y1, y2, z1, z2, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                                pst.setString(1, this.name);
                                pst.setInt(2, this.x1);
                                pst.setInt(3, this.x2);
                                pst.setInt(4, this.y1);
                                pst.setInt(5, this.y2);
                                pst.setInt(6, this.z1);
                                pst.setInt(7, this.z2);
                                pst.setString(8, this.world);

                                pst.executeUpdate();
                                return DBCommon.DBResponse.SUCCESS;
                            }
                        } catch (SQLException ex) {
                            exceptionHandler.logException(ex);
                            return DBCommon.DBResponse.FAILURE;
                        } finally {
                            try {
                                if (rs != null) {
                                    rs.close();
                                }

                                if (pst != null) {
                                    pst.close();
                                }

                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException ignored) {
                            }
                        }

                        // break; Apparently this is unreachable
                    case MYSQL:
                        try {
                            conn = common.getConnection(this.engine);

                            pst = conn.prepareStatement("SELECT * FROM " + this.tableName + " WHERE name=?");
                            pst.setString(1, this.name);

                            rs = pst.executeQuery();

                            if (rs.next()) {
                                return DBCommon.DBResponse.ALREADY_EXISTS;
                            } else {
                                pst = conn.prepareStatement("INSERT INTO " + this.tableName + " (name, x1, x2, y1, y2, z1, z2, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                                pst.setString(1, this.name);
                                pst.setInt(2, this.x1);
                                pst.setInt(3, this.x2);
                                pst.setInt(4, this.y1);
                                pst.setInt(5, this.y2);
                                pst.setInt(6, this.z1);
                                pst.setInt(7, this.z2);
                                pst.setString(8, this.world);

                                pst.executeUpdate();
                                return DBCommon.DBResponse.SUCCESS;
                            }
                        } catch (SQLException ex) {
                            exceptionHandler.logException(ex);
                            return DBCommon.DBResponse.FAILURE;
                        } finally {
                            try {
                                if (rs != null) {
                                    rs.close();
                                }

                                if (pst != null) {
                                    pst.close();
                                }

                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException ignored) {
                            }
                        }

                        // break; Apparently this is unreachable
                    case FILE:
                        try {
                            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);
                            yamlConfiguration.save(this.file);
                            return DBCommon.DBResponse.SUCCESS;
                        } catch (Exception e) {
                            exceptionHandler.logException(e);
                            return DBCommon.DBResponse.FAILURE;
                        }
                        // break; Apparently this is unreachable
                }
            } else {
                throw new NullPointerException("A database save of null data was attempted.");
            }
        } else {
            return DBCommon.DBResponse.ALREADY_SAVED;
        }
        return null;
    }

    public static List<Jail> getAllJails() {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        List<Jail> jails = new ArrayList<Jail>();
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "jails";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    st = conn.createStatement();
                    rs = st.executeQuery("SELECT * FROM " + tableName);

                    while (rs.next()) {
                        jails.add(new Jail(rs.getString("name"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"), rs.getString("world")));
                    }

                    return jails;
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return null;
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
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    st = conn.createStatement();
                    rs = st.executeQuery("SELECT * FROM " + tableName);

                    while (rs.next()) {
                        jails.add(new Jail(rs.getString("name"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"), rs.getString("world")));
                    }

                    return jails;
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return null;
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
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case FILE:
                File folder = FilePaths.getInstance().getJailsFolder();
                for (File f : folder.listFiles()) {
                    String name = f.getName();
                    if (name.endsWith(".yml")) {
                        name = name.replace(".yml", "");
                    }
                    JailYAML jailconf = new JailYAML(name);
                    Location min = new Location(jailconf.getWorld(), jailconf.getMinX(), jailconf.getMinY(), jailconf.getMinZ());
                    Location max = new Location(jailconf.getWorld(), jailconf.getMaxX(), jailconf.getMaxY(), jailconf.getMaxZ());
                    Cuboid cuboid = new Cuboid(min, max);
                    Jail jail = new Jail(name, cuboid);
                    jails.add(jail);
                }
                return jails;
            // TODO: YAML getting code
            // break; Apparently this is unreachable
        }
        return null;
    }

    public static Jail getJail(String name) {

        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "jails";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name=?");
                    pst.setString(1, name);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Jail(rs.getString("name"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"), rs.getString("world"));
                    } else {
                        return null;
                    }
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return null;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name=?");
                    pst.setString(1, name);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Jail(rs.getString("name"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"), rs.getString("world"));
                    } else {
                        return null;
                    }
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return null;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case FILE:
                for (Jail jail : Jail.getAllJails()) {
                    JailYAML jailconf = new JailYAML(jail);
                    if (jailconf.getName().equalsIgnoreCase(name)) {
                        return jail;
                    }
                }
                return null;
            // break; Apparently this is unreachable
        }
        return null;
    }

    public static Jail getJail(int id) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "jails";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?");
                    pst.setInt(1, id);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Jail(rs.getString("name"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"), rs.getString("world"));
                    } else {
                        return null;
                    }
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return null;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?");
                    pst.setInt(1, id);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Jail(rs.getString("name"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"), rs.getString("world"));
                    } else {
                        return null;
                    }
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return null;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case FILE:
                for (Jail jail : Jail.getAllJails()) {
                    JailYAML jailconf = new JailYAML(jail);
                    if (jailconf.getID() == id) {
                        return jail;
                    }
                }
                // TODO: YAML getting code
                return null;
            // break; Apparently this is unreachable
        }
        return null;
    }

    public static boolean removeJail(String name) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "jails";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    pst.setString(1, name);

                    rs = pst.executeQuery();

                    while (rs.next()) {
                        rs.deleteRow();
                    }

                    return true;
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return false;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    pst.setString(1, name);

                    rs = pst.executeQuery();

                    while (rs.next()) {
                        rs.deleteRow();
                    }

                    return true;
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return false;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case FILE:

                return false;
            // break; Apparently this is unreachable
        }
        return true;
    }

    public static boolean removeJail(int id) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "jails";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    pst.setInt(1, id);

                    rs = pst.executeQuery();

                    while (rs.next()) {
                        rs.deleteRow();
                    }

                    return true;
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return false;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    pst.setInt(1, id);

                    rs = pst.executeQuery();

                    while (rs.next()) {
                        rs.deleteRow();
                    }

                    return true;
                } catch (SQLException ex) {
                    exceptionHandler.logException(ex);
                    return false;
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pst != null) {
                            pst.close();
                        }

                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ignored) {

                    }
                }

                // break; Apparently this is unreachable
            case FILE:
                File folder = FilePaths.getInstance().getJailsFolder();
                for (File f : folder.listFiles()) {
                    YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
                    int jailid = c.getInt("id");
                    if (jailid == id) {
                        if (!f.delete()) {
                            f.deleteOnExit();
                        }
                    }
                }
                return false;
            // break; Apparently this is unreachable
        }
        return true;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getX1() {
        return this.x1;
    }

    public int getX2() {
        return this.x2;
    }

    public int getY1() {
        return this.y1;
    }

    public int getY2() {
        return this.y2;
    }

    public int getZ1() {
        return this.z1;
    }

    public int getZ2() {
        return this.z2;
    }

    public Cuboid getCuboid() {
        return new Cuboid(new Location(Bukkit.getWorld(this.world), this.x1, this.y1, this.z1), new Location(Bukkit.getWorld(this.world), this.x2, this.y2, this.z2));
    }

    public JailYAML getYamlConf() {
        return new JailYAML(this);
    }


    public static Jail matchJailYAML(String name) {
        File folder = FilePaths.getInstance().getJailsFolder();

        // TODO: Don't dereference this
        for (File f : folder.listFiles()) {
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            if (c.getString("name").equalsIgnoreCase(name)) {
                JailYAML jy = new JailYAML(f);
                return new Jail(name, jy.getCuboid());
            }
        }
        return null;
    }

}