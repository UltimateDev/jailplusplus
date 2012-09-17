package tk.ultimatedev.jailplusplus.guards;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.Jail;

public abstract class Guard {
	
	Jail jail;
	JailPlugin plugin;
	Location location;
	LivingEntity target;
	Location targetLoc;
	GuardPath path;
	GuardManager manager;
	
	public Guard(JailPlugin plugin, GuardManager manager, Location location, Jail jail){
		this.jail = jail;
		this.plugin = plugin;
		this.location = location;
		this.manager = manager;
	}
	
	public Location getLocation(){
		return this.location;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Jail getJail(){
		return this.jail;
	}
	
	public Location getTargetLoc(){
		return this.targetLoc;
	}
	
	public void setTargetLoc(Location location){
		this.targetLoc = location;
	}
	
	public LivingEntity getTarget(){
		return this.target;
	}
	
	public void setTarget(LivingEntity target){
		this.target = target;
	}
	
	public GuardPath getPath(){
		return this.path;
	}
	
	public void addToPath(Location location){
		this.path.addPoint(location);
	}
	
	public void removeFromPath(Location location){
		this.path.removePoint(location);
	}

	public LivingEntity getEntity() {
		return null;
	}

	public void teleport(Location location) {
		
	}

	public void kill() {
		this.manager.removeGuard(this);
	}
}