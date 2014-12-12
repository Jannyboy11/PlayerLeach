package events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import playerlead.PlayerLead;

public class PlayerMoveEventListener implements Listener{
	
	public PlayerLead plugin;
	
	public PlayerMoveEventListener(PlayerLead pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		//jb it's up to you :P
		
		
	}
	

}
