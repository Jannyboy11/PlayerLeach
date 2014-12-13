package events;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import playerlead.PlayerLead;

public class StopSlaveListener implements Listener {
	private PlayerLead plugin;
	
	public StopSlaveListener(PlayerLead pl){
		plugin = pl;
	}
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent e){
		removeSlaves(e.getPlayer());
	}
	
	@EventHandler
	public void onDeathEvent(PlayerDeathEvent e){
		removeSlaves(e.getEntity() );
	}
	
	@EventHandler
	public void onKickEvent(PlayerKickEvent e){
		removeSlaves(e.getPlayer());
	}
	
	
	public void removeSlaves(Player p) {
		UUID uuid = p.getUniqueId();
		plugin.horsePlayerPair.remove(uuid);
		plugin.slaveMasters.remove(uuid);
		for (Entry<UUID,UUID> e : plugin.slaveMasters.entrySet()) 
			if (e.getValue().equals(uuid))
				plugin.slaveMasters.remove(e.getKey());
	}
}
