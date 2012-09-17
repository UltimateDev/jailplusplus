package tk.ultimatedev.jailplusplus.guards;

import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import tk.ultimatedev.jailplusplus.JailPlugin;
import tk.ultimatedev.jailplusplus.models.Jail;

public class NPCGuard extends Guard {
	
	private HumanNPC npc;
	private NPCManager manager;
	private GuardManager guardManager;
	private String name;
	private LivingEntity target;
	private int radius;
	private String message;
	private boolean speaks;
	
	public NPCGuard(JailPlugin plugin, GuardManager manager, Location location, String name,
			Jail jail) {
		super(plugin, manager, location, jail);
		this.guardManager = manager;
		this.manager = plugin.manager;
		this.npc = (HumanNPC) this.manager.spawnHumanNPC(name, location);
		this.path = new GuardPath(this.npc);
		this.guardManager.addGuard(this);
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
		npc.setName(name);
	}
	
	public PlayerInventory getInventory(){
		return npc.getInventory();
	}
	
	public void moveTo(Location location){
		this.location = location;
		npc.walkTo(location);
	}
	
	@Override
	public void teleport(Location location){
		this.location = location;
		npc.moveTo(location);
	}
	
	public void setItem(Material material){
		npc.setItemInHand(material);
	}
	
	public void setItem(Material material, short damage){
		npc.setItemInHand(material, damage);
	}
	
	@Override
	public void kill(){
		this.guardManager.removeGuard(this);
		npc.removeFromWorld();
	}
	
	public void talkWhenClose(int radius, String message){
		this.radius = radius;
		this.message = message;
		this.speaks = true;
	}
	
	public void speak(){
		if(speaks){
			for(Player p : plugin.getServer().getOnlinePlayers()){
				if(p.getLocation().distance(this.location) <= radius){
					p.sendMessage(this.message);
				}
			}
		}
	}
	
	@Override
	public LivingEntity getEntity(){
		return (LivingEntity) npc.getBukkitEntity();
	}
	
	public void stopTalking(){
		speaks = false;
	}
	
	public void lookAt(Entity entity){
		npc.lookAtPoint(entity.getLocation());
	}
	
	public boolean isAttacking(){
		return target == null; 
	}
	
	public void attack(){
		//idk if this will be needed
	}
	
	public void clearTarget(){
		((Creature) npc.getBukkitEntity()).setTarget(null);
		target = null;
	}
	
	public void setTarget(LivingEntity entity){
		((Creature) npc.getBukkitEntity()).setTarget(entity);
		target = entity;
	}

}