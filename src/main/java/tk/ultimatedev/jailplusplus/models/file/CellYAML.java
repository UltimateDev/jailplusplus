package tk.ultimatedev.jailplusplus.models.file;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.models.Cell;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.models.Prisoner;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.FilePaths;
import tk.ultimatedev.jailplusplus.util.YamlGetters;

/**
 * @author YoshiGenius
 */
public class CellYAML {

    private int cellid;
    private int jailid;

    public CellYAML(Cell cell) {
        this.cellid = cell.getID();
        this.jailid = cell.getJailID();
    }

    public CellYAML(int jailID, int cellID) {
        this.jailid = jailID;
        this.cellid = cellID;
    }

    public CellYAML(Jail jail, Cell cell) {
        this.jailid = jail.getID();
        this.cellid = cell.getID();
    }

    public CellYAML(String jailname, int cellid) {
        Jail j = Jail.getJail(jailname);
        this.jailid = j.getID();
        this.cellid = cellid;
    }

    // remove methods

    public void removeCell() {
        MemorySection cs = this.getCellConf();
        Cell cell = Cell.getCell(cellid);
        int jid = cell.getJailID();
        int cid = cell.getID();
        String ipath = FilePaths.getInstance().getCellPath(jid, cid);
        String cpath = "";
        if (ipath.endsWith(cid + ".")) {
            cpath = ipath.replace(cid + ".", cid + "");
        }
        cs.set(cpath, null);
        YamlGetters.getInstance().saveCellsFile();
    }

    // get methods

    public List<Prisoner> getPrisoners() {
        return Prisoner.getCellPrisonersYAML(this.cellid);
    }

    public Jail getJail() {
        return Jail.getJail(this.jailid);
    }

    public YamlConfiguration getCellConf() {
        return FilePaths.getInstance().getCellFileConf();
    }

    public File getCellFile() {
        return FilePaths.getInstance().getCellsFile();
    }

    public String getName() {
        return YamlGetters.getInstance().getCellString(this.jailid, this.cellid, "name");
    }

    public int getID() {
        return YamlGetters.getInstance().getCellInt(this.jailid, this.cellid, "id");
    }

    public Cuboid getCuboid() {
        Cuboid cuboid = new Cuboid(this.getMinLoc(), this.getMaxLoc());
        return cuboid;
    }

    public Location getMinLoc() {
        Location loc = new Location(this.getWorld(), this.getMinX(), this.getMinY(), this.getMinZ());
        return loc;
    }

    public Location getMaxLoc() {
        Location loc = new Location(this.getWorld(), this.getMaxX(), this.getMaxY(), this.getMaxZ());
        return loc;
    }

    public World getWorld() {
        World world = Bukkit.getServer().getWorld(YamlGetters.getInstance().getCellString(this.jailid, this.cellid, "loc.world"));
        return world;
    }

    public int getMinX() {
        return YamlGetters.getInstance().getCellInt(this.jailid, this.cellid, "loc.min.x");
    }

    public int getMinY() {
        return YamlGetters.getInstance().getCellInt(this.jailid, this.cellid, "loc.min.y");
    }

    public int getMinZ() {
        return YamlGetters.getInstance().getCellInt(this.jailid, this.cellid, "loc.min.z");
    }

    public int getMaxX() {
        return YamlGetters.getInstance().getCellInt(this.jailid, this.cellid, "loc.max.x");
    }

    public int getMaxY() {
        return YamlGetters.getInstance().getCellInt(this.jailid, this.cellid, "loc.max.y");
    }

    public int getMaxZ() {
        return YamlGetters.getInstance().getCellInt(this.jailid, this.cellid, "loc.max.z");
    }

    // set methods

    public void setName(String name) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "name", name);
    }

    public void setID(int id) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "id", id);
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setCuboid(Cuboid cuboid) {
        Location min = cuboid.getA();
        Location max = cuboid.getB();
        this.setMaxLoc(max);
        this.setMinLoc(min);
    }

    public void setMinLoc(Location location) {
        this.setWorld(location.getWorld());
        this.setMinX(location.getBlockX());
        this.setMinY(location.getBlockY());
        this.setMinZ(location.getBlockZ());
    }

    public void setMaxLoc(Location location) {
        this.setWorld(location.getWorld());
        this.setMaxX(location.getBlockX());
        this.setMaxY(location.getBlockY());
        this.setMaxZ(location.getBlockZ());
    }

    public void setWorld(World world) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.world", world.getName());
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setWorld(String wname) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.world", wname);
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setMinX(int coord) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.min.x", coord);
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setMinY(int coord) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.min.y", coord);
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setMinZ(int coord) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.min.z", coord);
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setMaxX(int coord) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.max.x", coord);
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setMaxY(int coord) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.max.y", coord);
        YamlGetters.getInstance().saveCellsFile();
    }

    public void setMaxZ(int coord) {
        YamlGetters.getInstance().setCellsEntry(this.jailid, this.cellid, "loc.max.z", coord);
        YamlGetters.getInstance().saveCellsFile();
    }
}
