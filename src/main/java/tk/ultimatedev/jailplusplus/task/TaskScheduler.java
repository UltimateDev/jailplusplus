package tk.ultimatedev.jailplusplus.task;

//~--- non-JDK imports --------------------------------------------------------

import org.bukkit.Bukkit;

import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.util.Log;

public class TaskScheduler {
    public void schedule() {
        Log.info("Registering unjail task...");
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(JailPlugin.getPlugin(), new UnjailTask(), 600L, 600L);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
