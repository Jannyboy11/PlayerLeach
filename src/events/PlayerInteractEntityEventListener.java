package events;

import java.util.Arrays;
import java.util.UUID;
import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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
		
		if (checkLasso(master) && entity instanceof Player){
			//player caught another player! :P
			Player slave = (Player) entity;
			
			//JB IT'S UP TO YOU FROM HERE
			//e.g.: addMaster(slave, master);
			
		}
		
	}
	
	public boolean checkLasso(Player p){
		//Sorry Jb, ItemStack is niet metaDatable, dus ik gebruik lore i.p.v. metaData.
		return (p.getItemInHand().getType() == plugin.getLasso().getType()
				&& p.getItemInHand().getItemMeta().getLore().equals(plugin.getLasso().getItemMeta().getLore()));
	}
	
	public void addMaster(Player slave, Player master){
		if (plugin.slaveMasters.keySet().contains(slave.getUniqueId())){
			plugin.slaveMasters.get(slave.getUniqueId()).add(master.getUniqueId());
		} else {
			plugin.slaveMasters.put(slave.getUniqueId(), Arrays.asList(new UUID[]{master.getUniqueId()}));
		}
	}

}
