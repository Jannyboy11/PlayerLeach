package playerlead;

import net.minecraft.server.v1_8_R1.Entity;

import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;

public class CustomChicken extends CraftEntity {

	public CustomChicken(CraftServer server, Entity entity) {
		super(server, entity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EntityType getType() {
		return EntityType.CHICKEN;
				
	}

}
