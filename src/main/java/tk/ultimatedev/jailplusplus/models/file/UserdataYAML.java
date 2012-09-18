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
    private static String string;

    public UserdataYAML(Player player) {
        UserdataYAML.string = player.getName();
    }

    public UserdataYAML(String string) {
        UserdataYAML.string = string;
    }

    public String getUserdataString(String subpath) {
        return YamlGetters.getInstance().getPrisonersString(string, subpath);
    }

    public int getUserdataInt(String subpath) {
        return YamlGetters.getInstance().getPrisonersInt(string, subpath);
    }

    public boolean getUserdataBool(String subpath) {
        return YamlGetters.getInstance().getPrisonersBool(string, subpath);
    }

    public File getUserdataFile() {
        return FilePaths.getInstance().getPrisonersFile();
    }

    public YamlConfiguration getUserdataConf() {
        return FilePaths.getInstance().getPrisonersFileConf();
    }

    public void saveUserdataConf() {
        try {
            this.getUserdataConf().save(this.getUserdataFile());
        } catch (Exception e) {
            this.exception(e);
        }
    }

    public boolean isJailed() {
        return this.getUserdataBool("jailed");
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

        Jail jail = Jail.matchJailEntry(s);

        if (jail == null) {
            return null;
        }

        return jail;
    }

    public Jail getJailStickJail() {
        String   name      = this.getUserdataConf().getString("jailstick.jail.name");
        String   worldname = this.getUserdataConf().getString("jailstick.jail.world");
        String[] minloc    = this.getUserdataConf().getString("jailstick.jail.minloc").split(",");
        String[] maxloc    = this.getUserdataConf().getString("jailstick.jail.maxloc").split(",");
        World    world     = Bukkit.getServer().getWorld(worldname);
        int      minx      = Integer.valueOf(minloc[0]);
        int      miny      = Integer.valueOf(minloc[1]);
        int      minz      = Integer.valueOf(minloc[2]);
        Location min       = new Location(world, minx, miny, minz);
        int      maxx      = Integer.valueOf(maxloc[0]);
        int      maxy      = Integer.valueOf(maxloc[1]);
        int      maxz      = Integer.valueOf(maxloc[2]);
        Location max       = new Location(world, maxx, maxy, maxz);
        Cuboid   cuboid    = new Cuboid(min, max);
        Jail     jail      = new Jail(name, cuboid);

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

    public void setJailStickJail(Jail jail) {
        this.getUserdataConf().set("jailstick.jail.name", jail.getName());

        Cuboid cuboid = jail.getCuboid();

        this.getUserdataConf().set("jailstick.jail.maxloc",
                                   cuboid.getMaxX() + "," + cuboid.getMaxY() + "," + cuboid.getMaxZ());
        this.getUserdataConf().set("jailstick.jail.minloc",
                                   cuboid.getMinX() + "," + cuboid.getMinY() + "," + cuboid.getMinZ());
        this.saveUserdataConf();
    }

    public void setJailStickJail(String name, Cuboid cuboid) {
        this.getUserdataConf().set("jailstick.jail.name", name);
        this.getUserdataConf().set("jailstick.jail.maxloc",
                                   cuboid.getMaxX() + "," + cuboid.getMaxY() + "," + cuboid.getMaxZ());
        this.getUserdataConf().set("jailstick.jail.minloc",
                                   cuboid.getMinX() + "," + cuboid.getMinY() + "," + cuboid.getMinZ());
        this.saveUserdataConf();
    }

    public void setJailStickJail(String name, Location max, Location min) {
        this.getUserdataConf().set("jailstick.jail.name", name);

        Cuboid cuboid = new Cuboid(max, min);

        this.getUserdataConf().set("jailstick.jail.maxloc",
                                   cuboid.getMaxX() + "," + cuboid.getMaxY() + "," + cuboid.getMaxZ());
        this.getUserdataConf().set("jailstick.jail.minloc",
                                   cuboid.getMinX() + "," + cuboid.getMinY() + "," + cuboid.getMinZ());
        this.saveUserdataConf();
    }

    public void setJailStickTime(String time) {
        this.getUserdataConf().set("jailstick.time", time);
        this.saveUserdataConf();
    }

    public void setJailStickReason(String reason) {
        this.getUserdataConf().set("jailstick.reason", reason);
        this.saveUserdataConf();
    }

    public void setJailTimeSeconds(int seconds) {
        try {
            this.getUserdataConf().set("time", seconds);
        } catch (Exception e) {
            this.exception(e);
        }

        this.saveUserdataConf();
    }

    public void setJailer(String jailer) {
        this.getUserdataConf().set("jailer", jailer);
        this.saveUserdataConf();
    }

    public void exception(Exception e) {
        ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());

        eh.logException(e);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
