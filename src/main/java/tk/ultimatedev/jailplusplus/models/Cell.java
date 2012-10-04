package tk.ultimatedev.jailplusplus.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.file.CellYAML;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.FilePaths;
import tk.ultimatedev.jailplusplus.util.YamlGetters;

/**
 * @author Sushi
 */
public class Cell {
    DBCommon dbCommon;
    Migrant.DatabaseEngine engine;
    boolean saved;
    String tableName;

    int id;
    int jail;
    int x1;
    int x2;
    int y1;
    int y2;
    int z1;
    int z2;

    ExceptionHandler exceptionHandler;

    public Cell(int jail, int x1, int x2, int y1, int y2, int z1, int z2) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.id = -1;
        this.jail = jail;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    private Cell(int id, int jail, int x1, int x2, int y1, int y2, int z1, int z2) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = true;

        this.id = id;
        this.jail = jail;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    public Cell(int jail, Cuboid area) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.id = -1;
        this.jail = jail;
        this.x1 = area.getMinX();
        this.x2 = area.getMaxX();
        this.y1 = area.getMinY();
        this.y2 = area.getMaxY();
        this.z1 = area.getMinZ();
        this.z2 = area.getMaxZ();

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    private Cell(int id, int jail, Cuboid area) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = true;

        this.id = id;
        this.jail = jail;
        this.x1 = area.getMinX();
        this.x2 = area.getMaxX();
        this.y1 = area.getMinY();
        this.y2 = area.getMaxY();
        this.z1 = area.getMinZ();
        this.z2 = area.getMaxZ();

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    public Cell(Jail jail, int x1, int x2, int y1, int y2, int z1, int z2) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.id = -1;
        this.jail = jail.getID();
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    private Cell(int id, Jail jail, int x1, int x2, int y1, int y2, int z1, int z2) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = true;

        this.id = id;
        this.jail = jail.getID();
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    public Cell(Jail jail, Cuboid area) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.id = -1;
        this.jail = jail.getID();
        this.x1 = area.getMinX();
        this.x2 = area.getMaxX();
        this.y1 = area.getMinY();
        this.y2 = area.getMaxY();
        this.z1 = area.getMinZ();
        this.z2 = area.getMaxZ();

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    private Cell(int id, Jail jail, Cuboid area) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = true;

        this.id = id;
        this.jail = jail.getID();
        this.x1 = area.getMinX();
        this.x2 = area.getMaxX();
        this.y1 = area.getMinY();
        this.y2 = area.getMaxY();
        this.z1 = area.getMinZ();
        this.z2 = area.getMaxZ();

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }


    @Override
    public String toString() {
        return "#" + this.jail + ":" + this.x1 + ":" + this.x2 + ":" + this.y1 + ":" + this.y2 + ":" + this.z1 + ":" + this.z2;
    }

    public DBCommon.DBResponse save() throws NullPointerException {
        if (!this.saved) {
            if (this.jail != 0 && this.x1 != 0 && this.x2 != 0 && this.y1 != 0 && this.y2 != 0 && this.z1 != 0 && this.z2 != 0) {
                DBCommon common = new DBCommon();
                Connection conn = null;
                PreparedStatement pst = null;

                switch (this.engine) {
                    case H2:
                        try {
                            conn = common.getConnection(this.engine);

                            pst = conn.prepareStatement("INSERT INTO " + this.tableName + " (jail, x1, x2, y1, y2, z1, z2) VALUES (?, ?, ?, ?, ?, ?, ?)");
                            pst.setInt(1, this.jail);
                            pst.setInt(2, this.x1);
                            pst.setInt(3, this.x2);
                            pst.setInt(4, this.y1);
                            pst.setInt(5, this.y2);
                            pst.setInt(6, this.z1);
                            pst.setInt(7, this.z2);

                            pst.executeUpdate();

                            this.saved = true;
                            return DBCommon.DBResponse.SUCCESS;
                        } catch (SQLException ex) {
                            exceptionHandler.logException(ex);
                            return DBCommon.DBResponse.FAILURE;
                        } finally {
                            try {
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

                            pst = conn.prepareStatement("INSERT INTO " + this.tableName + " (jail, x1, x2, y1, y2, z1, z2) VALUES (?, ?, ?, ?, ?, ?, ?)");
                            pst.setInt(1, this.jail);
                            pst.setInt(2, this.x1);
                            pst.setInt(3, this.x2);
                            pst.setInt(4, this.y1);
                            pst.setInt(5, this.y2);
                            pst.setInt(6, this.z1);
                            pst.setInt(7, this.z2);

                            pst.executeUpdate();

                            this.saved = true;
                            return DBCommon.DBResponse.SUCCESS;
                        } catch (SQLException ex) {
                            exceptionHandler.logException(ex);
                            return DBCommon.DBResponse.FAILURE;
                        } finally {
                            try {
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
                        CellYAML data = new CellYAML(this.jail, this.id);
                        data.setName(String.valueOf(this.jail) + String.valueOf(this.id));
                        data.setID(this.id);
                        World w = Bukkit.getServer().getWorld(Jail.getJail(this.jail).world);
                        Cuboid cuboid = new Cuboid(new Location(w, this.x1, this.y1, this.z1), new Location(w, this.x2, this.y2, this.z2));
                        data.setCuboid(cuboid);
                        YamlGetters.getInstance().saveCellsFile();
                }
            } else {
                throw new NullPointerException("A database save of null data was attempted.");
            }
        } else {
            return DBCommon.DBResponse.ALREADY_SAVED;
        }
        return null;
    }

    public static List<Cell> getAllCells() {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        List<Cell> cells = new ArrayList<Cell>();
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "cells";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    st = conn.createStatement();
                    rs = st.executeQuery("SELECT * FROM " + tableName);

                    while (rs.next()) {
                        cells.add(new Cell(rs.getInt("id"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2")));
                    }

                    return cells;
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
                        cells.add(new Cell(rs.getInt("id"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2")));
                    }

                    return cells;
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
                YamlConfiguration cellconf = FilePaths.getInstance().getCellFileConf();
                List<String> allcells = cellconf.getStringList("cells");
                for (String jname : allcells) {
                    String[] jailcell = jname.split(";");
                    String jailname = jailcell[0];
                    int cellid = Integer.valueOf(jailcell[1]);
                    CellYAML data = new CellYAML(jailname, cellid);
                    data.getCuboid();
                    Cell cell = new Cell(cellid, data.getJail(), data.getCuboid());
                    cells.add(cell);
                }
                return cells;
        }
        return null;
    }

    public static Cell getCell(int id) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "cells";

        switch (engine) {
            case H2:
                try {
                    conn = DBCommon.getConnection();

                    pst = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?");
                    pst.setInt(1, id);

                    rs = pst.executeQuery();

                    if (rs.next()) {
                        return new Cell(rs.getInt("id"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"));
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
                        return new Cell(rs.getInt("id"), rs.getInt("x1"), rs.getInt("x2"), rs.getInt("y1"), rs.getInt("y2"), rs.getInt("z1"), rs.getInt("z2"));
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
                for (Cell cell : Cell.getAllCells()) {
                    if (cell.getID() == id) {
                        return cell;
                    }
                }
                return null;
        }
        return null;
    }

    public static boolean removeCell(int id) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
        Migrant.DatabaseEngine engine = Migrant.getDatabaseEngine();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String tableName = (new DBCommon()).getPrefix() + "cells";

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
                Cell cell = Cell.getCell(id);
                CellYAML data = new CellYAML(cell);
                data.removeCell();
        }
        return true;
    }

    public int getID() {
        return this.id;
    }

    public int getJailID() {
        return this.jail;
    }

    public Jail getJail() {
        return Jail.getJail(this.id);
    }

    public World getWorld() {
        World world = Bukkit.getServer().getWorld(this.getJail().world);
        return world;
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

    public Location getTeleportLocation() {
        if (this.getWorld() == null) return null;
        Cuboid cuboid = new Cuboid(new Location(this.getWorld(), this.getX1(), this.getY1(), this.getZ1()), new Location(this.getWorld(), this.getX2(), this.getY2(), this.getZ2()));
        Location center = cuboid.getCenter();
        int maxy = cuboid.getMaxY();
        int addy = maxy - center.getBlockY();
        Location topcenter = cuboid.getCenter().add(0, addy, 0);
        int miny = cuboid.getMinY();
        int suby = center.getBlockY() - miny;
        Location bottomcenter = cuboid.getCenter().add(0, suby, 0);
        Location tploc = topcenter;
        boolean solid = true;
        while (solid) {
            if (tploc.getBlock().getLocation() == bottomcenter) {
                break;
            }
            if (tploc.getBlock().getType() != Material.AIR) {
                tploc.add(0, -1, 0);
            } else {
                break;
            }
        }
        return tploc;
    }

}
