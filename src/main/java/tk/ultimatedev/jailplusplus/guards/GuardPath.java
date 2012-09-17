package tk.ultimatedev.jailplusplus.guards;

import com.topcat.npclib.entity.HumanNPC;
import java.util.ArrayList;
import org.bukkit.Location;

public class GuardPath {
	
	HumanNPC npc;
	ArrayList<Location> points = new ArrayList<Location>();
	Location active;
	
	public GuardPath(HumanNPC npc){
		this.npc = npc;
	}
	
	public void addPoint(Location location){
		points.add(location);
	}
	
	public void removePoint(Location location){
		if(points.contains(location)){
			points.remove(location);
		}
	}
	
	public void setNextActive(){
		if((points.indexOf(active)+1) >= points.size()){
			this.active = points.get(0);
		}else{
			this.active = points.get((points.indexOf(active)+1));
		}
	}
	
	public Location getActive(){
		return active;
	}
	
}
