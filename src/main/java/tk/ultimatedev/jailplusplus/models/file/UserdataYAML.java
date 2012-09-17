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
    
    private static Player player;
    
    public UserdataYAML(Player player) {
        UserdataYAML.player = player;
    }
    
    public File getUserdataFile() {
        return FilePaths.getInstance().getUserYAMLFile(player.getName());
    }
    
    public YamlConfiguration getUserdataConf() {
        return FilePaths.getInstance().getUserYAMLConf(player.getName());
    }
    
    public boolean isJailed() {
        return getUserdataConf().getBoolean("jailed");
    }
    
    public void setJailed(boolean jailed) {
        try {
            this.getUserdataConf().set("jailed", jailed);
            this.getUserdataConf().save(this.getUserdataFile());
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
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
        String name = getUserdataConf().getString("jailstick.jail.name");
        String worldname = getUserdataConf().getString("jailstick.jail.world");
        String[] minloc = getUserdataConf().getString("jailstick.jail.minloc").split(",");
        String[] maxloc = getUserdataConf().getString("jailstick.jail.maxloc").split(",");
        World world = Bukkit.getServer().getWorld(worldname);
        int minx = Integer.valueOf(minloc[0]);
        int miny = Integer.valueOf(minloc[1]);
        int minz = Integer.valueOf(minloc[2]);
        int minyaw = Integer.valueOf(minloc[3]);
        int minp = Integer.valueOf(minloc[4]);
        Location min = new Location(world, minx, miny, minz, minyaw, minp);
        int maxx = Integer.valueOf(maxloc[0]);
        int maxy = Integer.valueOf(maxloc[1]);
        int maxz = Integer.valueOf(maxloc[2]);
        int maxyaw = Integer.valueOf(maxloc[3]);
        int maxp = Integer.valueOf(maxloc[4]);
        Location max = new Location(world, maxx, maxy, maxz, maxyaw, maxp);
        Cuboid cuboid = new Cuboid(min, max);
        Jail jail = new Jail(name, cuboid);
        return jail;
    }
    
    public String getJailStickTime() {
        String time = getUserdataConf().getString("jailstick.time");
        return time;
    }
    
    public String getJailStickReason() {
        String reason = getUserdataConf().getString("jailstick.reason");
        return reason;
    }
    
    public int getJailTimeSeconds() {
        int timeleft = getUserdataConf().getInt("time");
        return timeleft;
    }

}