package tk.ultimatedev.jailplusplus.guards;

//~--- non-JDK imports --------------------------------------------------------

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import tk.ultimatedev.jailplusplus.JailPlugin;

public class GuardScheduler implements Runnable {
    GuardManager manager;
    JailPlugin   plugin;

    public GuardScheduler(JailPlugin plugin, GuardManager manager) {
        this.manager = manager;
        this.plugin  = plugin;
    }

    public void run() {
        for (Guard g : manager.getGuards()) {
            if ((g.getTarget() != null) && (g.getTargetLoc() != null)) {
                Block block = g.getLocation().getBlock();

                for (BlockFace face : BlockFace.values()) {
                    Block b = block.getRelative(face, 1);

                    if ((b.getX() == g.getTargetLoc().getBlockX()) && (b.getZ() == g.getTargetLoc().getBlockZ())) {
                        g.setTarget(null);
                        g.setTargetLoc(null);
                        plugin.getServer().broadcastMessage("end");

                        break;
                    }
                }

                if ((g.getTarget() != null) && g.getTarget().isDead()) {
                    g.setTarget(null);
                } else if (g.getTargetLoc() != null) {
                    g.setTargetLoc(g.getTarget().getLocation().getBlock().getLocation());
                }
            } else if (g.getPath() != null) {
                if ((g.getLocation().getX() == g.getPath().getActive().getX())
                        && (g.getLocation().getY() == g.getPath().getActive().getY())
                        && (g.getLocation().getZ() == g.getPath().getActive().getZ())) {
                    g.getPath().setNextActive();
                }
            } else if ((g.getPath() == null) && (g.getTarget() == null) && (g instanceof EntityGuard)) {
                g.teleport(g.getLocation());
                g.getEntity().setHealth(20);
            }

            g.setLocation(g.getEntity().getLocation().getBlock().getLocation());
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
