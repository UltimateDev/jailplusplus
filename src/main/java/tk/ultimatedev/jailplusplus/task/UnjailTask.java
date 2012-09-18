package tk.ultimatedev.jailplusplus.task;

public class UnjailTask implements Runnable {
    @Override
    public void run() {
        // TODO: Update this task

        /*
        List<Prisoner> prisoners = Prisoner.getAllPrisoners();

        for (Prisoner prisoner : prisoners) {
            if (Bukkit.getPlayer(prisoner.getPlayer()) != null) {
                if (prisoner.getExpiryTime().getTime() < System.currentTimeMillis()) {
                    Prisoner.removePrisoner(prisoner.getId());

                    if (prisoner.getX() == -1 && prisoner.getY() == -1 && prisoner.getZ() == -1) {
                        Bukkit.getPlayer(prisoner.getPlayer()).teleport(Cell.getCell(prisoner.getCell()).getJail().getCuboid().getWorld().getSpawnLocation());
                    } else {
                        Bukkit.getPlayer(prisoner.getPlayer()).teleport(new Location(Bukkit.getWorld(prisoner.getWorld()), (double) prisoner.getX(), (double) prisoner.getY(), (double) prisoner.getZ()));
                    }

                    Messenger.sendMessage(Bukkit.getPlayer(prisoner.getPlayer()), "You have served your sentence, and you are now unjailed!");
                }
            }
        }
        */

    }
}
