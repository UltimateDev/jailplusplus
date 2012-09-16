package tk.ultimatedev.jailplusplus.models;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.SettingsManager;
import tk.ultimatedev.jailplusplus.util.Cuboid;

public class Jail {
    
    SettingsManager sm = new SettingsManager(JailPlugin.getPlugin());
    
    /**
     * Gets the cuboid that the Jail's area is.
     * @param name Name of the jail to get a cuboid for.
     * @return Cuboid The cuboid that the jail is in.
     */
    
    public Cuboid getCuboid(String name) {
        File f = sm.getJailFile(name);
        if (!f.exists()) return null;
        YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
        String worldname = c.getString("loc.world");
        World w = Bukkit.getServer().getWorld(worldname);
        if (w == null) return null;
        int minx = c.getInt("loc.min.x");
        int miny = c.getInt("loc.min.y");
        int minz = c.getInt("loc.min.z");
        int maxx = c.getInt("loc.max.x");
        int maxy = c.getInt("loc.max.y");
        int maxz = c.getInt("loc.max.z");
        Location min = new Location(w, minx, miny, minz);
        Location max = new Location(w, maxx, maxy, maxz);
        Cuboid cuboid = new Cuboid(min,max);
        return cuboid;
    }
    
}
