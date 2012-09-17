package tk.ultimatedev.jailplusplus.models;

import tk.ultimatedev.jailplusplus.util.Cuboid;

public class Cell {
    DBCommon dbCommon;
    Migrant.DatabaseEngine engine;
    boolean saved;
    String tableName;

    private Cell(int id, String jail, int x1, int x2, int y1, int y2, int z1, int z2) {

    }

    private Cell(int id, String jail, Cuboid area) {

    }

    public Cell(String jail, int x1, int x2, int y1, int y2, int z1, int z2) {

    }

    public Cell(String jail, Cuboid area) {

    }
}
