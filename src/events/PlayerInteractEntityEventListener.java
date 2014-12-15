package events;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;
import java.util.ArrayList;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Style;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import playerlead.Attributes;
import playerlead.PlayerLead;

public class PlayerInteractEntityEventListener implements Listener {
	
	private PlayerLead plugin;
	
	public PlayerInteractEntityEventListener(PlayerLead pl){
		plugin = pl;
	}
	
	@EventHandler
	public void chest(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		
		if (block.getType().equals(Material.WORKBENCH) /*&& AutoWorkTable.isAutoTable(block)*/) {
			InventoryView inv =player.getOpenInventory();
			/*AutoWorkTable.openTable(inv)*/;
			//fills in the inventory with a recipe
			//somehow check for dupe glitches
			
		}
			
	}
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent e){
		
		Player master = e.getPlayer();
		Entity entity = e.getRightClicked();
		PlayerLead.server.broadcastMessage("before");
		if (plugin.checkLasso(master) && (entity instanceof Player) && !(entity instanceof NPC)){
			try {
				NPC test = (NPC) entity;
			} catch (Exception e2) {
			PlayerLead.server.broadcastMessage("master heeft een lasso, en target is een player");
			addMaster((Player)entity,master);
			}
		}
		
	}
	

	
	public void addMaster(Player slave, Player master){
		if (plugin.slaveMasters.keySet().contains(slave.getUniqueId())){
			master.sendMessage("You cannot steal " + slave.getName() + " since he's already enslaved!");
		} else {
			plugin.slaveMasters.put(slave.getUniqueId(), master.getUniqueId());
			master.sendMessage(slave.getName() + " has been added to your slaves. >:)");
			slave.sendMessage(master.getName() +" has caught you! you are now his slave.");
			spawnHorse(slave,master);
		}
	}
	
	
	public void spawnHorse(Player slave, Player master) {
		Location l  = slave.getLocation();
		l.setY(230);
		Horse spawned = (Horse)slave.getWorld().spawnEntity(l, EntityType.HORSE);
		spawned.setCustomNameVisible(false);
		spawned.setMaxDomestication(99999999);
		spawned.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0,true));
		spawned.teleport(slave);
		spawned.setLeashHolder(master);
		plugin.horsePlayerPair.put(slave, spawned);
	}
	


}
