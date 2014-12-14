package playerlead;

import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftChicken;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class CustomChicken extends net.minecraft.server.v1_8_R1.EntityChicken {

	public CustomChicken(World world) {
		super(world);
		PlayerLead plugin =(PlayerLead) Bukkit.getPluginManager().getPlugin("PlayerLead");
		new CraftChicken((CraftServer) plugin.server, this);
		// TODO Auto-generated constructor stub
	}
	
	public void setSpeed(float amount) {
		super.aA = amount;
		super.aC = amount;
		super.aD = amount;
		super.aE = amount;
	}

	public EntityType getType() {
		return EntityType.CHICKEN;
				
	}

}
