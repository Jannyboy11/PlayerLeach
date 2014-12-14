package playerlead;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.UUID;

import net.minecraft.server.v1_8_R1.EntityTypes;
import net.minecraft.server.v1_8_R1.GroupDataEntity;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import playerlead.Attributes.Attribute;
import playerlead.Attributes.AttributeType;
import events.MasterSlaveOnHitListener;
import events.PlayerInteractEntityEventListener;
import events.PlayerMovementListener;
import events.StopSlaveListener;

public class PlayerLead extends JavaPlugin {
	/*
	 * gebruik logger.info(someMessage) of logger.warning(someMessage) of logger.severe(someMessage) om info naar de serverconsole te printen.
	 */
	public final Logger logger = Logger.getLogger("Minecraft");
	
	/**
	 * The Special leash that gets created to bind players.
	 */
	private static ItemStack theLeash = new ItemStack(Material.LEASH);
	
	/**
	 * The max distance a slave may be away from the master.
	 */
	public static final double maxDistance = 5;
	
	public static Server server;
	public HashMap<UUID, UUID> slaveMasters;
	public HashMap<Player,Horse> horsePlayerPair;
	
	public void onEnable(){
		load();
		horsePlayerPair = new HashMap<Player,Horse>();
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
		server.getPluginManager().registerEvents(new StopSlaveListener(this), this);
		
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
				@SuppressWarnings("deprecation")
				Player player = server.getPlayer(args[0]);
				if (!sender.isOp() || player == null || !player.isOnline())
					return false;
				Location l  = player.getLocation();
				CraftWorld world = (CraftWorld)player.getWorld();
				World w = world.getHandle();
				CustomChicken entity = new CustomChicken(w);
				
				entity.setPositionRotation(l.getX(), l.getY()+1, l.getZ(), 0, 90);
				entity.prepare(null, (GroupDataEntity) null);
				w.addEntity(entity,SpawnReason.CUSTOM);
				entity.p(entity);
				player.getInventory().addItem(getLasso());
			}
			return true;
		}
		return false;
	}

	public static void load() {
		  try {

			  Method a = EntityTypes.class.getDeclaredMethod("a", new Class<?>[]{Class.class, String.class, int.class});
        
		    a.setAccessible(true); 
		    a.invoke(null, CustomChicken.class, "CCHICKEN", 121);

		} catch (Exception e) {
		//Insert handling code here
		}
		}
	
	public boolean checkLasso(Player p){
		//Sorry Jb, ItemStack is niet metaDatable, dus ik gebruik lore i.p.v. metaData.


		return (getLasso().getItemMeta().getLore().equals(p.getItemInHand().getItemMeta().getLore()));
	}
	
	
	public ItemStack getLasso(){
		Attributes test = new Attributes(theLeash);
		 test.add(Attribute.newBuilder().name("Damage")
				 .type(AttributeType.GENERIC_ATTACK_DAMAGE).amount(-20).build());
		
		return test.getStack();
	}
	
	public static void main(String[] args){
		System.out.println("NOOB THIS IS NOT HOW YOU RUN THIS");
	}

}
