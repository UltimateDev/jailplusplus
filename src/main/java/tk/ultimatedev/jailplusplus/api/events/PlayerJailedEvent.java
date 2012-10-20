package tk.ultimatedev.jailplusplus.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import tk.ultimatedev.jailplusplus.models.Cell;
import tk.ultimatedev.jailplusplus.models.Jail;

/**
 * @author YoshiGenius
 */
public class PlayerJailedEvent extends PlayerEvent implements Cancellable {

    private static HandlerList handlers;
    private static boolean cancel;
    private Jail jail;
    private Cell cell;
    private String jailer;
    private String reason;
    private int created;
    private int sentence;

    public PlayerJailedEvent(Player player, Cell cell, String jailer, String reason, int created, int sentence) {
        super(player);
        this.cell = cell;
        this.jail = cell.getJail();
        this.jailer = jailer;
        this.reason = reason;
        this.created = created;
        this.sentence = sentence;
    }

    public Cell getCell() {
        return this.cell;
    }

    public Jail getJail() {
        return this.jail;
    }

    public String getJailer() {
        return this.jailer;
    }

    public String getReason() {
        return this.reason;
    }

    public int getCreated() {
        return this.created;
    }

    public int getSentence() {
        return this.sentence;
    }

    public HandlerList getHandlers() {
        return PlayerJailedEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return PlayerJailedEvent.handlers;
    }

    public boolean isCancelled() {
        return PlayerJailedEvent.cancel;
    }

    public void setCancelled(boolean cancelled) {
        PlayerJailedEvent.cancel = cancelled;
    }

}
