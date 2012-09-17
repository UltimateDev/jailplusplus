package tk.ultimatedev.jailplusplus.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.ultimatedev.jailplusplus.ExceptionHandler;
import tk.ultimatedev.jailplusplus.JailPlugin;

import java.util.HashMap;

/**
 * @author YoshiGenius
 */
public class Time {
    
    private int id;
    private HashMap<Player, Integer> remainingtime = new HashMap<Player, Integer>();
    
    public int toTicks(String time) {
        try {
            int seconds = 0;
            String[] t0 = time.split("y");
            int years = Integer.parseInt(t0[0]);
            seconds = seconds + (years * 31449600);
            String[] t1 = time.split("mo");
            int months = Integer.parseInt(t1[0]);
            seconds = seconds + (months * 2419200);
            String time1 = t1[1];
            String[] t2 = time1.split("w");
            int weeks = Integer.parseInt(t2[0]);
            seconds = seconds + (weeks * 604800);
            String time2 = t2[1];
            String[] t3 = time2.split("d");
            int days = Integer.parseInt(t3[0]);
            seconds = seconds + (days * 86400);
            String time3 = t3[1];
            String[] t4 = time3.split("h");
            int hours = Integer.parseInt(t4[0]);
            seconds = seconds + (hours * 3600);
            String time4 = t4[1];
            String[] t5 = time4.split("m");
            int minutes = Integer.parseInt(t5[0]);
            seconds = seconds + (minutes * 60);
            String time5 = t5[1];
            String[] t6 = time5.split("s");
            int xseconds = Integer.parseInt(t6[0]);
            seconds = seconds + xseconds;
            int ticks = seconds * 20;
            return ticks;
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
            return 0;
        }
    }
    
    public int toSeconds(String time) {
        try {
            int seconds = 0;
            String[] t0 = time.split("y");
            int years = Integer.parseInt(t0[0]);
            seconds = seconds + (years * 31449600);
            String[] t1 = time.split("mo");
            int months = Integer.parseInt(t1[0]);
            seconds = seconds + (months * 2419200);
            String time1 = t1[1];
            String[] t2 = time1.split("w");
            int weeks = Integer.parseInt(t2[0]);
            seconds = seconds + (weeks * 604800);
            String time2 = t2[1];
            String[] t3 = time2.split("d");
            int days = Integer.parseInt(t3[0]);
            seconds = seconds + (days * 86400);
            String time3 = t3[1];
            String[] t4 = time3.split("h");
            int hours = Integer.parseInt(t4[0]);
            seconds = seconds + (hours * 3600);
            String time4 = t4[1];
            String[] t5 = time4.split("m");
            int minutes = Integer.parseInt(t5[0]);
            seconds = seconds + (minutes * 60);
            String time5 = t5[1];
            String[] t6 = time5.split("s");
            int xseconds = Integer.parseInt(t6[0]);
            seconds = seconds + xseconds;
            return seconds;
        } catch (Exception e) {
            ExceptionHandler eh = new ExceptionHandler(JailPlugin.getPlugin());
            eh.logException(e);
            return 0;
        }
    }
    
    public void startCountdownToEndJailTime(int time, final String playername) {
        this.id = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(JailPlugin.getPlugin(), new Runnable() {
            public void run() {
                Messenger.broadcastMessage(ChatColor.GOLD + playername + " has finished their sentence in jail!", true);
            }
        }, (time*20), 20);
    }
    /*
    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        Player p = evt.getPlayer();
        Prisoner prisoner = new Prisoner(p);
        UserdataYAML data = prisoner.getPrisonerYAML();
        BukkitScheduler bs = Bukkit.getServer().getScheduler();
        if (!data.isJailed()) {
            return;
        }
        if (bs.isCurrentlyRunning(this.id)) {
            bs.cancelTask(this.id);
            
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        Player p = evt.getPlayer();
        Prisoner prisoner = new Prisoner(p);
        UserdataYAML data = prisoner.getPrisonerYAML();
        if (!data.isJailed()) {
            return;
        }
        int time = data.getJailTimeSeconds();
        this.startCountdownToEndJailTime(time, p.getName());
    }
    */

}
