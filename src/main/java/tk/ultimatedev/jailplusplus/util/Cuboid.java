package tk.ultimatedev.jailplusplus.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * A data type for working with Cuboid shapes.
 */
public class Cuboid {
    private Location a, b;

    /**
     * Create a Cuboid from two locations, which are the
     * corners of the Cuboid.
     *
     * @param a Corner 1 of the cuboid.
     * @param b Corner 2 of the cuboid.
     */
    public Cuboid(Location a, Location b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Sets corner 1 of the cuboid.
     *
     * @param a The value that corner 1 is to be set
     *          to.
     */
    public void setA(Location a) {
        this.a = a;
    }

    /**
     * Sets corner 2 of the cuboid.
     *
     * @param b The value that corner 2 is to be set
     *          to.
     */
    public void setB(Location b) {
        this.b = b;
    }

    /**
     * Gets corner 1 of the cuboid.
     *
     * @return Corner 1 of the cuboid.
     */
    public Location getA() {
        return this.a;
    }

    /**
     * Gets corner 2 of the cuboid.
     *
     * @return Corner 2 of the cuboid.
     */
    public Location getB() {
        return this.b;
    }

    /**
     * Gets the world that the cuboid is in.
     *
     * @return The world that the cuboid is in.
     */
    public World getWorld() {
        return this.a.getWorld();
    }

    /**
     * Gets the max X value of the cuboid.
     *
     * @return The max X value of the cuboid.
     */
    public int getMaxX() {
        return Math.max(a.getBlockX(), b.getBlockX());
    }

    /**
     * Gets the min X value of the cuboid.
     *
     * @return The min X value of the cuboid.
     */
    public int getMinX() {
        return Math.min(a.getBlockX(), b.getBlockX());
    }

    /**
     * Gets the max Y value of the cuboid.
     *
     * @return The max Y value of the cuboid.
     */
    public int getMaxY() {
        return Math.max(a.getBlockY(), b.getBlockY());
    }

    /**
     * Gets the min Y value of the cuboid.
     *
     * @return The min Y value of the cuboid.
     */
    public int getMinY() {
        return Math.min(a.getBlockY(), b.getBlockY());
    }

    /**
     * Gets the max Z value of the cuboid.
     *
     * @return The max Z value of the cuboid.
     */
    public int getMaxZ() {
        return Math.max(a.getBlockZ(), b.getBlockZ());
    }

    /**
     * Gets the min Z value of the cuboid.
     *
     * @return The min Z value of the cuboid.
     */
    public int getMinZ() {
        return Math.min(a.getBlockZ(), b.getBlockZ());
    }

    /**
     * Gets the center of the cuboid.
     *
     * @return The center of the cuboid.
     */
    public Location getCenter() {
        return new Location(this.a.getWorld(), (this.a.getBlockX() + this.b.getBlockX()) / 2D,
                (this.a.getBlockY() + this.b.getBlockY()) / 2D,
                (this.a.getBlockZ() + this.b.getBlockZ()) / 2D);
    }

    /**
     * Get the block at the center of the cuboid.
     *
     * @return The block at the center of the cuboid.
     */
    public Block getCenterBlock() {
        return getWorld().getBlockAt(getCenter());
    }

    /**
     * Check if a certain location is in a cuboid.
     *
     * @param location The location that is to be
     *                 checked for being inside of
     *                 the cuboid.
     * @return A boolean that is true if the location
     *         is inside the cuboid or false if the location
     *         is not inside the cuboid.
     */
    public boolean isInCuboid(Location location) {
        return (location.getWorld() == this.getWorld()) && (location.getBlockX() >= this.getMinX())
                && (location.getBlockX() <= this.getMaxX()) && (location.getBlockY() >= this.getMinY())
                && (location.getBlockY() <= this.getMaxY()) && (location.getBlockZ() >= this.getMinZ())
                && (location.getBlockZ() <= this.getMaxZ());
    }

    /**
     * Check if a certain player is in a cuboid.
     *
     * @param player The player that is to be checked
     *               for being inside of the cuboid.
     * @return A boolean that is true if the player
     *         is inside the cuboid or false if the player
     *         is not inside the cuboid.
     */
    public boolean isInCuboid(Player player) {
        return (player.getWorld() == this.getWorld()) && (player.getLocation().getBlockX() >= this.getMinX())
                && (player.getLocation().getBlockX() <= this.getMaxX())
                && (player.getLocation().getBlockY() >= this.getMinY())
                && (player.getLocation().getBlockY() <= this.getMaxY())
                && (player.getLocation().getBlockZ() >= this.getMinZ())
                && (player.getLocation().getBlockZ() <= this.getMaxZ());
    }

    /**
     * Check if a certain entity is in a cuboid.
     *
     * @param entity The entity that is to be checked
     *               for being inside of the cuboid.
     * @return A boolean that is true if the entity
     *         is inside the cuboid or false if the entity
     *         is not inside the cuboid.
     */
    public boolean isInCuboid(Entity entity) {
        return (entity.getWorld() == this.getWorld()) && (entity.getLocation().getBlockX() >= this.getMinX())
                && (entity.getLocation().getBlockX() <= this.getMaxX())
                && (entity.getLocation().getBlockY() >= this.getMinY())
                && (entity.getLocation().getBlockY() <= this.getMaxY())
                && (entity.getLocation().getBlockZ() >= this.getMinZ())
                && (entity.getLocation().getBlockZ() <= this.getMaxZ());
    }

    /**
     * Check if a certain block is in a cuboid.
     *
     * @param block The block that is to be checked
     *              for being inside of the cuboid.
     * @return A boolean that is true if the block
     *         is inside the cuboid or false if the block
     *         is not inside the cuboid.
     */
    public boolean isInCuboid(Block block) {
        return (block.getWorld() == this.getWorld()) && (block.getLocation().getBlockX() >= this.getMinX())
                && (block.getLocation().getBlockX() <= this.getMaxX())
                && (block.getLocation().getBlockY() >= this.getMinY())
                && (block.getLocation().getBlockY() <= this.getMaxY())
                && (block.getLocation().getBlockZ() >= this.getMinZ())
                && (block.getLocation().getBlockZ() <= this.getMaxZ());
    }
}
