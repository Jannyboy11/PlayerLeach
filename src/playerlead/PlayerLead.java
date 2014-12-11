package playerlead;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import events.PlayerInteractEntityEventListener;

public class PlayerLead extends JavaPlugin {
	/*
	 * gebruik logger.info(someMessage) of logger.warning(someMessage) of logger.severe(someMessage) om info naar de serverconsole te printen.
	 */
	public final Logger logger = Logger.getLogger("Minecraft");
	private static final ItemStack theLeash = new ItemStack(Material.LEASH);
	
	public static Server server;
	public HashMap<UUID, UUID> slaveMasters;
	
	public void onEnable(){
		theLeash.getItemMeta().setLore(Arrays.asList(new String[]{"Grab your slave now!", "Gain more followers!"}));
		logger.info("onEnable has been invoked!");
		//initialise stuff! :D
		server = getServer();
		server.getPluginManager().registerEvents(new PlayerInteractEntityEventListener(this), this);
		//UUID is serialisable, dus als je die wilt laden uit een file met een objectinputstream, dan kan dat! :D
		//voor nu maak ik gewoon een nieuwe lege hashmap aan.
		slaveMasters = new HashMap<UUID, UUID>();
		
	}
	
	public void onDisable(){
		logger.info("onDisable has been invoked!");
		//close off stuff! :D
		PlayerInteractEvent.getHandlerList().unregister(this);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("getleash") && args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
			} else {
				Player player = server.getPlayer(args[0]);
				player.getInventory().addItem(getLasso());
			}
			return true;
		}
		return false;
	}
	
	public ItemStack getLasso(){
		
		
		return theLeash;
	}
	
	public static void main(String[] args){
		System.out.println("NOOB THIS IS NOT HOW YOU RUN THIS");
	}

}
