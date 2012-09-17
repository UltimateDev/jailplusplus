package tk.ultimatedev.jailplusplus.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.models.file.UserdataYAML;
import tk.ultimatedev.jailplusplus.util.FilePaths;

import java.io.File;

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
        return new Prisoner(player);
    }
    
    public UserdataYAML getPrisonerYAML() {
        return new UserdataYAML(player);
    }

}
