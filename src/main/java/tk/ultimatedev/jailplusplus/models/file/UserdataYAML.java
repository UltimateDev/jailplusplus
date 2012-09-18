package tk.ultimatedev.jailplusplus.models.file;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.FilePaths;
import tk.ultimatedev.jailplusplus.util.YamlGetters;

/**
 * @author YoshiGenius
 */
public class UserdataYAML {
    private String pname;

    public UserdataYAML(Player player) {
        this.pname = player.getName();
    }

    public UserdataYAML(String string) {
        this.pname = string;
    }

    private String getUserdataString(String subpath) {
        return YamlGetters.getInstance().getPrisonersString(pname, subpath);
    }

    private int getUserdataInt(String subpath) {
        return YamlGetters.getInstance().getPrisonersInt(pname, subpath);
    }

    private boolean getUserdataBool(String subpath) {
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

    public Jail getJail() {
        String s = getUserdataString("jail");
        if (!this.isJailed()) return null;
        if (s.equalsIgnoreCase("")) return null;
        Jail jail = Jail.matchJailEntry(s);
        if (jail == null) return null;
        return jail;
    }

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

    public void exception(Exception e) {
        ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
        eh.logException(e);
    }
}
