package tk.ultimatedev.jailplusplus.models;

import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;

public class Prisoner {
    DBCommon dbCommon;
    Migrant.DatabaseEngine engine;
    boolean saved;
    String tableName;

    String player;
    int cell;
    String sentence;
    String served;
    String reason;
    String jailer;

    ExceptionHandler exceptionHandler;

    public Prisoner(String player, int cell, String sentence, String served, String reason, String jailer) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.player = player;
        this.cell = cell;
        this.sentence = sentence;
        this.served = served;
        this.reason = reason;
        this.jailer = jailer;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    public Prisoner(String player, String sentence, String served, String reason, String jailer) {
        this.dbCommon = new DBCommon();
        this.engine = Migrant.getDatabaseEngine();
        this.tableName = this.dbCommon.getPrefix() + "jails";
        this.saved = false;

        this.player = player;
        this.sentence = sentence;
        this.served = served;
        this.reason = reason;
        this.jailer = jailer;

        this.exceptionHandler = new ExceptionHandler(JailPlugin.getPlugin());
    }

    @Override
    public String toString() {
        return "#" + this.player + ":" + this.sentence + ":" + this.served + ":" + this.reason + ":" + this.jailer;
    }

    /*
    Commented because the player variable cannot be a static member!

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

    */

}
