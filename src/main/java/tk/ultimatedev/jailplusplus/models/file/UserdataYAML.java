package tk.ultimatedev.jailplusplus.models.file;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.Cell;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.models.Prisoner;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.FilePaths;
import tk.ultimatedev.jailplusplus.util.YamlGetters;

/**
 * @author YoshiGenius
 */
public class UserdataYAML {
    private String pname;
    
    public UserdataYAML(Prisoner prisoner) {
        this.pname = prisoner.getPlayer();
    }

    public UserdataYAML(Player player) {
        this.pname = player.getName();
    }

    public UserdataYAML(String string) {
        this.pname = string;
    }
    
    public UserdataYAML(int id) {
        String string = null;
        for (Prisoner p : Prisoner.getAllPrisoners()) {
            if (p.getId() == id) {
                string = p.getPlayer();
            }
        }
        this.pname = string;
    }
    
    public void removePrisoner() {
        MemorySection cs = this.getUserdataConf();
        Prisoner prisoner = Prisoner.getPrisoner(pname);
        String ipath = FilePaths.getInstance().getPrisonersPath(pname);
        String cpath = "";
        if (ipath.endsWith(pname + ".")) {
            cpath = ipath.replace(pname + ".", pname + "");
        }
        cs.set(cpath, null);
        YamlGetters.getInstance().savePrisonersFile();
    }

    public String getUserdataString(String subpath) {
        return YamlGetters.getInstance().getPrisonersString(pname, subpath);
    }

    public int getUserdataInt(String subpath) {
        return YamlGetters.getInstance().getPrisonersInt(pname, subpath);
    }

    public boolean getUserdataBool(String subpath) {
        return YamlGetters.getInstance().getPrisonersBool(pname, subpath);
    }
    
    public void setUserdataEntry(String subpath, Object obj) {
        YamlGetters.getInstance().setPrisonersEntry(pname, subpath, obj);
        YamlGetters.getInstance().savePrisonersFile();
    }

    public File getUserdataFile() {
        return FilePaths.getInstance().getPrisonersFile();
    }

    public YamlConfiguration getUserdataConf() {
        return FilePaths.getInstance().getPrisonersFileConf();
    }

    public boolean isJailed() {
        return this.getUserdataBool("jailed");
    }

    public void setJailed(boolean jailed) {
        this.setUserdataEntry("jailed", jailed);
    }
    
    // getters
    
    public String getName() {
        return this.getUserdataString("name");
    }
    
    public int getID() {
        return this.getUserdataInt("id");
    }
    
    public int getCreatedTime() {
        return this.getUserdataInt("created");
    }
    
    public int getSentence() {
        return this.getUserdataInt("sentence");
    }
    
    public int getServed() {
        return this.getUserdataInt("served");
    }
    
    public String getReason() {
        return this.getUserdataString("reason");
    }
    
    public String getInventory() {
        return this.getUserdataString("inventory");
    }

    public Jail getJail() {
        String s = getUserdataString("jail");
        if (!this.isJailed()) return null;
        if (s.equalsIgnoreCase("")) return null;
        Jail jail = Jail.matchJailEntry(s);
        if (jail == null) return null;
        return jail;
    }
    
    public Cell getCell() {
        int cid = this.getUserdataInt("cell.id");
        Cell cell = Cell.getCell(cid);
        return cell;
    }
    
    public World getLastLocW() {
        World world = Bukkit.getServer().getWorld(this.getUserdataString("loc.world"));
        if (world == null) return null;
        return world;
    }
    
    public int getLastLocX() {
        return this.getUserdataInt("loc.x");
    }
    
    public int getLastLocY() {
        return this.getUserdataInt("loc.y");
    }
    
    public int getLastLocZ() {
        return this.getUserdataInt("loc.z");
    }
    
    public Location getLastLoc() {
        int x = this.getLastLocX();
        int y = this.getLastLocY();
        int z = this.getLastLocZ();
        World world = this.getLastLocW();
        Location loc = new Location(world, x, y, z);
        return loc;
    }
    
    // jailstick

    public Jail getJailStickJail() {
        String name = this.getUserdataConf().getString("jailstick.jail.name");
        String worldname = this.getUserdataConf().getString("jailstick.jail.world");
        String[] minloc = this.getUserdataConf().getString("jailstick.jail.minloc").split(",");
        String[] maxloc = this.getUserdataConf().getString("jailstick.jail.maxloc").split(",");
        World world = Bukkit.getServer().getWorld(worldname);
        int minx = Integer.valueOf(minloc[0]);
        int miny = Integer.valueOf(minloc[1]);
        int minz = Integer.valueOf(minloc[2]);
        Location min = new Location(world, minx, miny, minz);
        int maxx = Integer.valueOf(maxloc[0]);
        int maxy = Integer.valueOf(maxloc[1]);
        int maxz = Integer.valueOf(maxloc[2]);
        Location max = new Location(world, maxx, maxy, maxz);
        Cuboid cuboid = new Cuboid(min, max);
        Jail jail = new Jail(name, cuboid);
        return jail;
    }

    public String getJailStickTime() {
        String time = this.getUserdataString("jailstick.time");
        return time;
    }

    public String getJailStickReason() {
        String reason = this.getUserdataString("jailstick.reason");
        return reason;
    }

    public int getJailTimeSeconds() {
        int timeleft = this.getUserdataInt("time");
        return timeleft;
    }

    public String getJailer() {
        String jailer = this.getUserdataString("jailer");
        return jailer;
    }
    
    // setters
    
    public void setCreatedTime(int time) {
        this.setUserdataEntry("created", time);
    }
    
    public void setSentence(int sentence) {
        this.setUserdataEntry("sentence", sentence);
    }
    
    public void setServed(int served) {
        this.setUserdataEntry("served", served);
    }
    
    public void setReason(String reason) {
        this.setUserdataEntry("reason", reason);
    }
    
    public void setInventory(String inventory) {
        this.setUserdataEntry("inventory", inventory);
    }

    public void setJailStickJail(Jail jail) {
        this.setUserdataEntry("jailstick.jail.name", jail.getName());
        Cuboid cuboid = jail.getCuboid();
        this.setUserdataEntry("jailstick.jail.maxloc", cuboid.getMaxX() + "," + cuboid.getMaxY() + "," + cuboid.getMaxZ());
        this.setUserdataEntry("jailstick.jail.minloc", cuboid.getMinX() + "," + cuboid.getMinY() + "," + cuboid.getMinZ());
    }

    public void setJailStickJail(String name, Cuboid cuboid) {
        this.setUserdataEntry("jailstick.jail.name", name);
        this.setUserdataEntry("jailstick.jail.maxloc", cuboid.getMaxX() + "," + cuboid.getMaxY() + "," + cuboid.getMaxZ());
        this.setUserdataEntry("jailstick.jail.minloc", cuboid.getMinX() + "," + cuboid.getMinY() + "," + cuboid.getMinZ());
    }

    public void setJailStickJail(String name, Location max, Location min) {
        this.setUserdataEntry("jailstick.jail.name", name);
        Cuboid cuboid = new Cuboid(max, min);
        this.setUserdataEntry("jailstick.jail.maxloc", cuboid.getMaxX() + "," + cuboid.getMaxY() + "," + cuboid.getMaxZ());
        this.setUserdataEntry("jailstick.jail.minloc", cuboid.getMinX() + "," + cuboid.getMinY() + "," + cuboid.getMinZ());
    }

    public void setJailStickTime(String time) {
        this.setUserdataEntry("jailstick.time", time);
    }

    public void setJailStickReason(String reason) {
        this.setUserdataEntry("jailstick.reason", reason);
    }
    
    public void setJailTimeSeconds(int seconds) {
        this.setUserdataEntry("time", seconds);
    }

    public void setJailer(String jailer) {
        this.setUserdataEntry("jailer", jailer);
    }
    
    public void setJailedIn(Cell cell) {
        this.setUserdataEntry("cell.id", cell.getID());
        this.setUserdataEntry("cell.min.x", cell.getX1());
        this.setUserdataEntry("cell.min.y", cell.getY1());
        this.setUserdataEntry("cell.min.z", cell.getZ1());
        this.setUserdataEntry("cell.max.x", cell.getX2());
        this.setUserdataEntry("cell.max.y", cell.getY2());
        this.setUserdataEntry("cell.max.z", cell.getZ2());
        Jail jail = cell.getJail();
        JailYAML data = jail.getYamlConf();
        this.setUserdataEntry("jail.id", data.getID());
        this.setUserdataEntry("jail.name", data.getName());
        Cuboid cuboid = data.getCuboid();
        Location min = cuboid.getA();
        Location max = cuboid.getB();
        this.setUserdataEntry("jail.min.x", min.getX());
        this.setUserdataEntry("jail.min.y", min.getY());
        this.setUserdataEntry("jail.min.z", min.getZ());
        this.setUserdataEntry("jail.max.x", max.getX());
        this.setUserdataEntry("jail.max.y", max.getY());
        this.setUserdataEntry("jail.max.z", max.getZ());
    }
    
    public void setLastLoc(Location loc) {
        this.setLastLocW(loc.getWorld());
        this.setLastLocX(loc.getBlockX());
        this.setLastLocY(loc.getBlockY());
        this.setLastLocZ(loc.getBlockZ());
    }
    
    public void setLastLocW(World w) {
        this.setUserdataEntry("loc.world", w.getName());
    }
    
    public void setLastLocX(int coord) {
        this.setUserdataEntry("loc.x", coord);
    }
    
    public void setLastLocY(int coord) {
        this.setUserdataEntry("loc.y", coord);
    }
    
    public void setLastLocZ(int coord) {
        this.setUserdataEntry("loc.z", coord);
    }

    public void exception(Exception e) {
        ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
        eh.logException(e);
    }
}
