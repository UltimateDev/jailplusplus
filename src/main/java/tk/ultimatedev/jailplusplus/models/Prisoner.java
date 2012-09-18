package tk.ultimatedev.jailplusplus.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.serialization.InventorySerializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Sushi
 */
public class Prisoner {
    DBCommon dbCommon;
    Migrant.DatabaseEngine engine;
    boolean saved;
    String tableName;

    int id;
    String player;
    int cell;
    int created;
    int sentence;
    int served;
    String reason;
    String jailer;
    String inventory;
    int x;
    int y;
    int z;
    String world;

    ExceptionHandler exceptionHandler;

    private Prisoner(int id, String player, int cell, int created, int sentence, int served, String reason, String jailer, String inv, int x, int y, int z, String world) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "prisoners";
        this.saved = true;

        this.id = id;
        this.player = player;
        this.cell = cell;
        this.created = created;
        this.sentence = sentence;
        this.served = served;
        this.reason = reason;
        this.jailer = jailer;
        this.inventory = inv;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    public Prisoner(String player, int cell, int created, int sentence, int served, String reason, String jailer) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "prisoners";
        this.saved = false;

        this.id = -1;
        this.player = player;
        this.cell = cell;
        this.created = created;
        this.sentence = sentence;
        this.served = served;
        this.reason = reason;
        this.jailer = jailer;
        if (Bukkit.getPlayer(this.player) != null) {
            Player playerObject = Bukkit.getPlayer(this.player);
            this.inventory = InventorySerializer.getString(Bukkit.getPlayer(player).getInventory());

            this.x = (int) playerObject.getLocation().getX();
            this.y = (int) playerObject.getLocation().getY();
            this.z = (int) playerObject.getLocation().getZ();
        } else {
            this.inventory = "";

            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.world = "";
        }

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    @Override
    public String toString() {
        return "#" + this.player + ":" + this.created + ":" + this.sentence + ":" + this.served + ":" + this.reason + ":" + this.jailer;
    }

    public DBCommon.DBResponse save() throws NullPointerException {
        if (!this.saved) {
            if (this.player != null && this.created != 0 && this.sentence != 0 && this.served != 0 && this.reason != null && this.jailer != null && this.x != 0 && this.y != 0 && this.z != 0 && this.world != null) {
                DBCommon common = new DBCommon();
                Connection conn = null;
                PreparedStatement pst = null;
                ResultSet rs = null;

                switch (this.engine) {
                    case H2:
                        try {
                            conn = common.getConnection(this.engine);

                            pst = conn.prepareStatement("SELECT * FROM " + this.tableName + " WHERE name=?");
                            pst.setString(1, this.player);

                            rs = pst.executeQuery();

                            if (rs.next()) {
                                return DBCommon.DBResponse.ALREADY_EXISTS;
                            } else {
                                pst = conn.prepareStatement("INSERT INTO " + this.tableName + " (name, cell, created, sentence, served, reason, jailer, inv, x, y, z, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                pst.setString(1, this.player);
                                pst.setInt(2, this.cell);
                                pst.setInt(3, this.created);
                                pst.setInt(4, this.sentence);
                                pst.setInt(5, this.served);
                                pst.setString(6, this.reason);
                                pst.setString(7, this.jailer);
                                pst.setString(8, this.inventory);
                                pst.setInt(9, this.x);
                                pst.setInt(10, this.y);
                                pst.setInt(11, this.z);
                                pst.setString(12, this.world);

                                pst.executeUpdate();

                                this.saved = true;
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
                    case MYSQL:
                        try {
                            conn = common.getConnection(this.engine);

                            pst = conn.prepareStatement("SELECT * FROM " + this.tableName + " WHERE name=?");
                            pst.setString(1, this.player);

                            rs = pst.executeQuery();

                            if (rs.next()) {
                                return DBCommon.DBResponse.ALREADY_EXISTS;
                            } else {
                                pst = conn.prepareStatement("INSERT INTO " + this.tableName + " (name, cell, created, sentence, served, reason, jailer, inv, x, y, z, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                pst.setString(1, this.player);
                                pst.setInt(2, this.cell);
                                pst.setInt(3, this.created);
                                pst.setInt(4, this.sentence);
                                pst.setInt(5, this.served);
                                pst.setString(6, this.reason);
                                pst.setString(7, this.jailer);
                                pst.setString(8, this.inventory);
                                pst.setInt(9, this.x);
                                pst.setInt(10, this.y);
                                pst.setInt(11, this.z);
                                pst.setString(12, this.world);

                                pst.executeUpdate();

                                this.saved = true;
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
                    case FILE:
                        // TODO: Add YAML adding code
                        break;
                }
            } else {
                throw new NullPointerException("A database save of null data was attempted.");
            }
        } else {
            return DBCommon.DBResponse.ALREADY_SAVED;
        }

        return null;
    }

    public static List<Prisoner> getAllPrisoners() {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        List<Prisoner> prisoners = new ArrayList<Prisoner>();
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "prisoners";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    st = conn.createStatement();
                    rs = st.executeQuery("SELECT * FROM " + tableName);

                    while (rs.next()) {
                        prisoners.add(new Prisoner(rs.getInt("id"), rs.getString("name"), rs.getInt("cell"), rs.getInt("created"), rs.getInt("sentence"), rs.getInt("served"), rs.getString("reason"), rs.getString("jailer"), rs.getString("inv"), rs.getInt("x"), rs.getInt("x"), rs.getInt("x"), rs.getString("world")));
                    }

                    return prisoners;
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
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    st = conn.createStatement();
                    rs = st.executeQuery("SELECT * FROM " + tableName);

                    while (rs.next()) {
                        prisoners.add(new Prisoner(rs.getInt("id"), rs.getString("name"), rs.getInt("cell"), rs.getInt("created"), rs.getInt("sentence"), rs.getInt("served"), rs.getString("reason"), rs.getString("jailer"), rs.getString("inv"), rs.getInt("x"), rs.getInt("x"), rs.getInt("x"), rs.getString("world")));
                    }

                    return prisoners;
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
            case FILE:
                // TODO: YAML getting code
        }

        return null;
    }

    public static Prisoner getPrisoner(String name) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "prisoners";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name=?");
                    pst.setString(1, name);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Prisoner(rs.getInt("id"), rs.getString("name"), rs.getInt("cell"), rs.getInt("created"), rs.getInt("sentence"), rs.getInt("served"), rs.getString("reason"), rs.getString("jailer"), rs.getString("inv"), rs.getInt("x"), rs.getInt("x"), rs.getInt("x"), rs.getString("world"));
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
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE name=?");
                    pst.setString(1, name);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Prisoner(rs.getInt("id"), rs.getString("name"), rs.getInt("cell"), rs.getInt("created"), rs.getInt("sentence"), rs.getInt("served"), rs.getString("reason"), rs.getString("jailer"), rs.getString("inv"), rs.getInt("x"), rs.getInt("x"), rs.getInt("x"), rs.getString("world"));
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
            case FILE:
                // TODO: YAML getting code
        }
        return null;
    }

    public static Prisoner getPrisoner(int id) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "prisoners";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?");
                    pst.setInt(1, id);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Prisoner(rs.getInt("id"), rs.getString("name"), rs.getInt("cell"), rs.getInt("created"), rs.getInt("sentence"), rs.getInt("served"), rs.getString("reason"), rs.getString("jailer"), rs.getString("inv"), rs.getInt("x"), rs.getInt("x"), rs.getInt("x"), rs.getString("world"));
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
            case MYSQL:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?");
                    pst.setInt(1, id);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Prisoner(rs.getInt("id"), rs.getString("name"), rs.getInt("cell"), rs.getInt("created"), rs.getInt("sentence"), rs.getInt("served"), rs.getString("reason"), rs.getString("jailer"), rs.getString("inv"), rs.getInt("x"), rs.getInt("x"), rs.getInt("x"), rs.getString("world"));
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
            case FILE:
                // TODO: YAML getting code
        }
        return null;
    }

    public static boolean removePrisoner(String name) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "prisoners";

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
            case FILE:
                // TODO: File deletion code
                return false;
        }
        return true;
    }

    public static boolean removePrisoner(int id) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "prisoners";

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
            case FILE:
                // TODO: File deletion code
                return false;
        }
        return true;
    }

    public int getId() {
        return this.id;
    }

    public String getPlayer() {
        return this.player;
    }

    public int getCell() {
        return this.cell;
    }

    public Date getCreationTime() {
        return new Date((long) this.created * 1000);
    }

    public Date getSentenceTime() {
        return new Date((long) this.sentence * 1000);
    }

    public Date getServedTime() {
        return new Date((long) this.served * 1000);
    }

    public String getReason() {
        return this.reason;
    }

    public String getJailer() {
        return this.jailer;
    }

    public Inventory getInventory() {
        return InventorySerializer.getInventory(this.inventory);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public String getWorld() {
        return this.world;
    }
}
