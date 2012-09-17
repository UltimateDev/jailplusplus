package tk.ultimatedev.jailplusplus.guards;

//~--- non-JDK imports --------------------------------------------------------

import net.minecraft.server.Navigation;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.Jail;

public class EntityGuard extends Guard {
    LivingEntity entity;
    Navigation   nav;

    public EntityGuard(JailPlugin plugin, GuardManager manager, Location location, Jail jail, EntityType ctype) {
        super(plugin, manager, location, jail);
        this.entity = (LivingEntity) location.getWorld().spawnEntity(location, ctype);
        manager.addGuard(this);

        // this.getHandle().al();
        // nav = ((EntityLiving) ((CraftEntity) this.entity).getHandle()).al();
        // TODO: I am not sure how to fix this. Lets do it later.
    }

    @Override
    public void setTarget(LivingEntity e) {

        // ((Creature) this.entity).setTarget(e);
        // this.entity.damage(0, e);
        if (e != null) {
            nav.a(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), 0.2F);
            this.target    = e;
            this.targetLoc = e.getLocation();
        } else {
            this.target = null;
            targetLoc   = null;
            nav.a(location.getX(), location.getY(), location.getZ(), 0F);
        }
    }

    public boolean test() {
        return nav.a();
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public void teleport(Location location) {
        this.entity.teleport(location);
    }

    @Override
    public void kill() {
        this.manager.removeGuard(this);
        entity.remove();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
