package tk.ultimatedev.jailplusplus.models.file;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.models.Prisoner;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.FilePaths;
import tk.ultimatedev.jailplusplus.util.YamlGetters;

/**
 * @author YoshiGenius
 */
public class JailYAML {

    private String name;

    public JailYAML(String jname) {
        name = jname;
    }

    public JailYAML(Jail jail) {
        name = jail.getName();
    }

    // remove methods

    public void removeJail() {
        MemorySection cs = this.getJailConf();
        cs.set(name, null);
        YamlGetters.getInstance().saveJailsFile();
    }

    // get methods

    public List<Prisoner> getPrisoners() {
        return Prisoner.getJailPrisonersYAML(Jail.getJail(name));
    }

    public YamlConfiguration getJailConf() {
        return FilePaths.getInstance().getJailFileConf();
    }

    public File getJailFile() {
        return FilePaths.getInstance().getJailsFile();
    }

    public String getName() {
        return YamlGetters.getInstance().getJailString(this.name, "name");
    }

    public int getID() {
        return YamlGetters.getInstance().getJailInt(this.name, "id");
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
        World world = Bukkit.getServer().getWorld(YamlGetters.getInstance().getJailString(this.name, "loc.world"));
        return world;
    }

    public int getMinX() {
        return YamlGetters.getInstance().getJailInt(this.name, "loc.min.x");
    }

    public int getMinY() {
        return YamlGetters.getInstance().getJailInt(this.name, "loc.min.y");
    }

    public int getMinZ() {
        return YamlGetters.getInstance().getJailInt(this.name, "loc.min.z");
    }

    public int getMaxX() {
        return YamlGetters.getInstance().getJailInt(this.name, "loc.max.x");
    }

    public int getMaxY() {
        return YamlGetters.getInstance().getJailInt(this.name, "loc.max.y");
    }

    public int getMaxZ() {
        return YamlGetters.getInstance().getJailInt(this.name, "loc.max.z");
    }

    // set methods

    public void setName(String name) {
        YamlGetters.getInstance().setJailsEntry(this.name, "name", name);
    }

    public void setID(int id) {
        YamlGetters.getInstance().setJailsEntry(this.name, "id", id);
        YamlGetters.getInstance().saveJailsFile();
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
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.world", world.getName());
        YamlGetters.getInstance().saveJailsFile();
    }

    public void setWorld(String wname) {
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.world", wname);
        YamlGetters.getInstance().saveJailsFile();
    }

    public void setMinX(int coord) {
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.min.x", coord);
        YamlGetters.getInstance().saveJailsFile();
    }

    public void setMinY(int coord) {
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.min.y", coord);
        YamlGetters.getInstance().saveJailsFile();
    }

    public void setMinZ(int coord) {
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.min.z", coord);
        YamlGetters.getInstance().saveJailsFile();
    }

    public void setMaxX(int coord) {
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.max.x", coord);
        YamlGetters.getInstance().saveJailsFile();
    }

    public void setMaxY(int coord) {
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.max.y", coord);
        YamlGetters.getInstance().saveJailsFile();
    }

    public void setMaxZ(int coord) {
        YamlGetters.getInstance().setJailsEntry(this.name, "loc.max.z", coord);
        YamlGetters.getInstance().saveJailsFile();
    }

}
