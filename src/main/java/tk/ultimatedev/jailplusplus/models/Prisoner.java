package tk.ultimatedev.jailplusplus.models;

public class Prisoner {
    Migrant.DatabaseEngine engine;

    public Prisoner() {
        this.engine = Migrant.getDatabaseEngine();
    }


}
