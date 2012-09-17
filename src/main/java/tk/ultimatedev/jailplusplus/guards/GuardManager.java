package tk.ultimatedev.jailplusplus.guards;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

public class GuardManager {
    private ArrayList<Guard> guards = new ArrayList<Guard>();

    public GuardManager() {}

    public ArrayList<Guard> getGuards() {
        return this.guards;
    }

    public void addGuard(Guard guard) {
        this.guards.add(guard);
    }

    public void removeGuard(Guard guard) {
        this.guards.remove(guard);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
