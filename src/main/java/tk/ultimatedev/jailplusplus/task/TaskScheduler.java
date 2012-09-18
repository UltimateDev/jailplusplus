package tk.ultimatedev.jailplusplus.task;

import org.bukkit.Bukkit;
import tk.ultimatedev.jailplusplus.JailPlugin;

public class TaskScheduler {
    public void schedule() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(JailPlugin.getPlugin(), new UnjailTask(), 600L, 600L);
    }
}
