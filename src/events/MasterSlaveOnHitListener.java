package events;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import playerlead.PlayerLead;

public class MasterSlaveOnHitListener implements Listener {
	
	private PlayerLead plugin;
	
	public MasterSlaveOnHitListener(PlayerLead pl){
		plugin = pl;
	}
	
	@EventHandler
	public void onAttackEvent(EntityDamageByEntityEvent e){ 
		Entity attackerE = e.getDamager();
		Entity defenderE= e.getEntity();
		if (!(attackerE instanceof Player) || !(defenderE instanceof Player)) return;
		Player attacker = (Player) attackerE;
		Player defender = (Player) defenderE;
		UUID atkID = attacker.getUniqueId();
		UUID defID = defender.getUniqueId();
		for (Entry<UUID,UUID> en : plugin.slaveMasters.entrySet()) {
			if (atkID.equals(en.getKey()) && defID.equals(en.getValue())) {
				e.setCancelled(true);
				attacker.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,40,1));
				attacker.damage(1);
			} else if (atkID.equals(en.getValue()) && defID.equals(en.getKey()) && plugin.checkLasso(attacker)) {
					defender.damage(800);
					defender.sendMessage("bad slave!");
			}
				
		}
	}
}
