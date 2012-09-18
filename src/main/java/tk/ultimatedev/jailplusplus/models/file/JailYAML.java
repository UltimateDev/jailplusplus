package tk.ultimatedev.jailplusplus.models.file;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.ultimatedev.jailplusplus.models.Jail;
import tk.ultimatedev.jailplusplus.models.Prisoner;
import tk.ultimatedev.jailplusplus.util.Cuboid;
import tk.ultimatedev.jailplusplus.util.FilePaths;

/**
 * @author YoshiGenius
 */
public class JailYAML {

    private static Jail jail;

    public JailYAML(Jail jail) {
        JailYAML.jail = jail;
    }

    public JailYAML(String name) {
        JailYAML.jail = Jail.matchJailYAML(name);
    }

    public JailYAML(File file) {
        YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
        Jail.matchJailYAML(c.getString("name"));
    }

    public JailYAML(YamlConfiguration yamlconf) {
        Jail.matchJailYAML(yamlconf.getString("name"));
    }

    public YamlConfiguration getJailConf() {
        return FilePaths.getInstance().getJailYAMLConf(jail.getName());
    }

    public File getJailFile() {
        return FilePaths.getInstance().getJailYAMLFile(jail.getName());
    }

    public String getName() {
        return this.getJailConf().getString("name");
    }

    public int getID() {
        return this.getJailConf().getInt("id");
    }

    public List<Prisoner> getPrisoners() {

        return null;
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
        return Bukkit.getServer().getWorld(getJailConf().getString("loc.world"));
    }

    public int getMinX() {
        return getJailConf().getInt("loc.min.x");
    }

    public int getMinY() {
        return getJailConf().getInt("loc.min.y");
    }

    public int getMinZ() {
        return getJailConf().getInt("loc.max.z");
    }

    public int getMaxX() {
        return getJailConf().getInt("loc.min.x");
    }

    public int getMaxY() {
        return getJailConf().getInt("loc.max.y");
    }

    public int getMaxZ() {
        return getJailConf().getInt("loc.max.z");
    }

}
