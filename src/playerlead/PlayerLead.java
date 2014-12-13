package playerlead;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import events.MasterSlaveOnHitListener;
import events.PlayerInteractEntityEventListener;
import events.PlayerMovementListener;

public class PlayerLead extends JavaPlugin {
	/*
	 * gebruik logger.info(someMessage) of logger.warning(someMessage) of logger.severe(someMessage) om info naar de serverconsole te printen.
	 */
	public final Logger logger = Logger.getLogger("Minecraft");
	
	/**
	 * The Special leash that gets created to bind players.
	 */
	private static final ItemStack theLeash = new ItemStack(Material.LEASH);
	
	/**
	 * The max distance a slave may be away from the master.
	 */
	public static final double maxDistance = 5;
	
	public static Server server;
	public HashMap<UUID, UUID> slaveMasters;
	
	public void onEnable(){
		
		ItemMeta a = theLeash.getItemMeta();
		a.setLore(Arrays.asList(new String[]{"Grab your slave now!", "Gain more followers!","",ChatColor.GRAY + "" +  ChatColor.ITALIC+" WE ARE NOT HELD ACCOUNTABLE"}));
		a.setDisplayName(ChatColor.WHITE+ "The Human "+ ChatColor.GRAY +"\"Leash\"");;
		theLeash.setItemMeta(a);
		
		logger.info("onEnable has been invoked!");
		//initialise stuff! :D
		server = getServer();
		server.getPluginManager().registerEvents(new PlayerInteractEntityEventListener(this), this);
		server.getPluginManager().registerEvents(new PlayerMovementListener(this), this);
		server.getPluginManager().registerEvents(new MasterSlaveOnHitListener(this), this);
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
		if (cmd.getName().equalsIgnoreCase("giveleash") && args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be run by a player.");
			} else {
				Player player = server.getPlayer(args[0]);
				if (!sender.isOp() || player == null || !player.isOnline())
					return false;
				
				player.getInventory().addItem(getLasso());
			}
			return true;
		}
		return false;
	}
	
	public boolean checkLasso(Player p){
		//Sorry Jb, ItemStack is niet metaDatable, dus ik gebruik lore i.p.v. metaData.
		return (getLasso().equals(new ItemStack(p.getItemInHand())));
	}
	
	
	public ItemStack getLasso(){
		
		
		return theLeash;
	}
	
	public static void main(String[] args){
		System.out.println("NOOB THIS IS NOT HOW YOU RUN THIS");
	}

}
