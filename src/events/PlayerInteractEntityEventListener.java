package events;

import java.util.Arrays;
import java.util.UUID;
import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import playerlead.PlayerLead;

public class PlayerInteractEntityEventListener implements Listener {
	
	private PlayerLead plugin;
	
	public PlayerInteractEntityEventListener(PlayerLead pl){
		plugin = pl;
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent e){
		
		Player master = e.getPlayer();
		Entity entity = e.getRightClicked();
		PlayerLead.server.broadcastMessage("before");
		if (plugin.checkLasso(master) && entity instanceof Player){
			PlayerLead.server.broadcastMessage("master heeft een lasso, en target is een player");
			addMaster((Player)entity,master);
		}
		
	}
	

	
	public void addMaster(Player slave, Player master){
		if (plugin.slaveMasters.keySet().contains(slave.getUniqueId())){
			master.sendMessage("You cannot steal " + slave.getName() + " since he's already enslaved!");
		} else {
			plugin.slaveMasters.put(slave.getUniqueId(), master.getUniqueId());
			master.sendMessage(slave.getName() + " has been added to your slaves. >:)");
			slave.sendMessage(master.getName() +" has caught you! you are now his slave.");
			PlayerLead.server.broadcastMessage(slave.setLeashHolder(master) +"");
		}
	}

}
