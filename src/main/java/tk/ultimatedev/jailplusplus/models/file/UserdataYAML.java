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

/**
 * @author YoshiGenius
 */
public class UserdataYAML {

    private static String string;

    public UserdataYAML(Player player) {
        UserdataYAML.string = player.getName();
    }

    public UserdataYAML(String string) {
        UserdataYAML.string = string;
    }

    public File getUserdataFile() {
        return FilePaths.getInstance().getUserYAMLFile(string);
    }

    public YamlConfiguration getUserdataConf() {
        return FilePaths.getInstance().getUserYAMLConf(string);
    }

    public void saveUserdataConf() {
        try {
            this.getUserdataConf().save(this.getUserdataFile());
        } catch (Exception e) {
            this.exception(e);
        }
    }

    public boolean isJailed() {
        return getUserdataConf().getBoolean("jailed");
    }

    public void setJailed(boolean jailed) {
        try {
            this.getUserdataConf().set("jailed", jailed);
            this.getUserdataConf().save(this.getUserdataFile());
        } catch (Exception e) {
            this.exception(e);
        }
    }

    public Jail getJail() {
        String s = getUserdataConf().getString("jail");
        if (s.equalsIgnoreCase("")) {
            return null;
        }
        Jail jail = Jail.matchJailYAML(s);
        if (jail == null) {
            return null;
        }
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
        String time = this.getUserdataConf().getString("jailstick.time");
        return time;
    }

    public String getJailStickReason() {
        String reason = this.getUserdataConf().getString("jailstick.reason");
        return reason;
    }

    public int getJailTimeSeconds() {
        int timeleft = this.getUserdataConf().getInt("time");
        return timeleft;
    }

    public String getJailer() {
        String jailer = this.getUserdataConf().getString("jailer");
        return jailer;
    }

//    public void setJailStickJail(Jail jail) {
//        String name = this.getUserdataConf().getString("jailstick.jail.name");
//        String worldname = this.getUserdataConf().getString("jailstick.jail.world");
//        String[] minloc = this.getUserdataConf().getString("jailstick.jail.minloc").split(",");
//        String[] maxloc = this.getUserdataConf().getString("jailstick.jail.maxloc").split(",");
//        World world = Bukkit.getServer().getWorld(worldname);
//        int minx = Integer.valueOf(minloc[0]);
//        int miny = Integer.valueOf(minloc[1]);
//        int minz = Integer.valueOf(minloc[2]);
//        Location min = new Location(world, minx, miny, minz);
//        int maxx = Integer.valueOf(maxloc[0]);
//        int maxy = Integer.valueOf(maxloc[1]);
//        int maxz = Integer.valueOf(maxloc[2]);
//        Location max = new Location(world, maxx, maxy, maxz);
//        Cuboid cuboid = new Cuboid(min, max);
//    }

//    public void setJailStickJail(String name, Cuboid cuboid) {
//        String name = this.getUserdataConf().getString("jailstick.jail.name");
//        String worldname = this.getUserdataConf().getString("jailstick.jail.world");
//        String[] minloc = this.getUserdataConf().getString("jailstick.jail.minloc").split(",");
//        String[] maxloc = this.getUserdataConf().getString("jailstick.jail.maxloc").split(",");
//        World world = Bukkit.getServer().getWorld(worldname);
//        int minx = Integer.valueOf(minloc[0]);
//        int miny = Integer.valueOf(minloc[1]);
//        int minz = Integer.valueOf(minloc[2]);
//        Location min = new Location(world, minx, miny, minz);
//        int maxx = Integer.valueOf(maxloc[0]);
//        int maxy = Integer.valueOf(maxloc[1]);
//        int maxz = Integer.valueOf(maxloc[2]);
//        Location max = new Location(world, maxx, maxy, maxz);
//        Cuboid cuboid = new Cuboid(min, max);
//        Jail jail = new Jail(name, cuboid);
//    }

//    public void setJailStickJail(String name, Location max, Location min) {
//        this.getUserdataConf().set("jailstick.jail.name", name);
//        Cuboid cuboid = new Cuboid(max,min);
//        this.getUserdataConf().set("jailstick.jail.maxloc", cuboid.getMaxX() + "," + cuboid.getMaxY() + "," + cuboid.getMaxZ());
//        this.getUserdataConf().set("jailstick.jail.minloc", cuboid.getMinX() + "," + cuboid.getMinY() + "," + cuboid.getMinZ());
//        this.saveUserdataConf();
//    }

    public void setJailStickTime(String time) {
        this.getUserdataConf().set("jailstick.time", time);
    }

    public void setJailStickReason(String reason) {
        this.getUserdataConf().set("jailstick.reason", reason);
    }

    public void setJailTimeSeconds(int seconds) {
        try {
            this.getUserdataConf().set("time", seconds);
            this.getUserdataConf().save(this.getUserdataFile());
        } catch (Exception e) {
            this.exception(e);
        }
    }

    public void setJailer(String jailer) {
        this.getUserdataConf().set("jailer", jailer);
    }

    public void exception(Exception e) {
        ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
        eh.logException(e);
    }

}