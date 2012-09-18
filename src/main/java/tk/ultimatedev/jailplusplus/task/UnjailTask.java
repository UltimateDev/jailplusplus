package tk.ultimatedev.jailplusplus.task;

import org.bukkit.Bukkit;
import tk.ultimatedev.jailplusplus.models.Cell;
import tk.ultimatedev.jailplusplus.models.Prisoner;
import tk.ultimatedev.jailplusplus.util.Messenger;

import java.util.List;

public class UnjailTask implements Runnable {
    @Override
    public void run() {
        List<Prisoner> prisoners = Prisoner.getAllPrisoners();

        for (Prisoner prisoner : prisoners) {
            if (Bukkit.getPlayer(prisoner.getPlayer()) != null) {
                if (prisoner.getExpiryTime().getTime() < System.currentTimeMillis()) {
                    Prisoner.removePrisoner(prisoner.getId());

                    Bukkit.getPlayer(prisoner.getPlayer()).teleport(Cell.getCell(prisoner.getCell()).getJail().getCuboid().getWorld().getSpawnLocation());
                    Messenger.sendMessage(Bukkit.getPlayer(prisoner.getPlayer()), "You have served your sentence, and you are now unjailed!");
                }
            }
        }

    }
}
