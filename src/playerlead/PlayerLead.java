package playerlead;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import events.PlayerInteractEntityEventListener;

public class PlayerLead extends JavaPlugin {
	/*
	 * gebruik logger.info(someMessage) of logger.warning(someMessage) of logger.severe(someMessage) om info naar de serverconsole te printen.
	 */
	public final Logger logger = Logger.getLogger("Minecraft");
	public static Server server;
	public HashMap<UUID, List<UUID>> slaveMasters;
	
	public void onEnable(){
		logger.info("onEnable has been invoked!");
		//initialise stuff! :D
		server = getServer();
		server.getPluginManager().registerEvents(new PlayerInteractEntityEventListener(this), this);
		//UUID is serialisable, dus als je die wilt laden uit een file met een objectinputstream, dan kan dat! :D
		//voor nu maak ik gewoon een nieuwe lege hashmap aan.
		slaveMasters = new HashMap<UUID, List<UUID>>();
		
	}
	
	public void onDisable(){
		logger.info("onDisable has been invoked!");
		//close off stuff! :D
		PlayerInteractEvent.getHandlerList().unregister(this);
		
	}
	
	public ItemStack getLasso(){
		ItemStack lasso = new ItemStack(Material.LEASH);
		lasso.getItemMeta().setLore(Arrays.asList(new String[]{"Grab your slave now!", "Gain more followers!"}));
		return lasso;
	}

}
