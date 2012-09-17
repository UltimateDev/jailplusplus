package tk.ultimatedev.jailplusplus.models;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.models.file.UserdataYAML;
import tk.ultimatedev.jailplusplus.util.FilePaths;

public class Prisoner {
    Migrant.DatabaseEngine engine;
    static Player player;

    public Prisoner(Player player) {
        Prisoner.player = player;
        this.engine = Migrant.getDatabaseEngine();
    }
    
    public static void matchPrisoner(String name) {
        Player matched = Bukkit.getServer().getPlayer(name);
        if (matched == null) {
            return;
        }
        player = matched;
        Prisoner.matchPrisoner();
    }
    
    private static Prisoner matchPrisoner() {
        File userdatafile = FilePaths.getInstance().getUserYAMLFile(player.getName());
        // YamlConfiguration userdataconf = FilePaths.getInstance().getUserYAMLConf(player.getName());
        if (!userdatafile.exists()) {
            return null;
        }
        Prisoner prisoner = new Prisoner(player);
        return prisoner;
    }
    
    public UserdataYAML getPrisonerYAML() {
        UserdataYAML data = new UserdataYAML(player);
        return data;
    }

}
