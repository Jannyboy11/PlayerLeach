package playerlead;


import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R1.BiomeBase;
import net.minecraft.server.v1_8_R1.BiomeMeta;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityTypes;
import net.minecraft.server.v1_8_R1.GroupDataEntity;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;


public enum CustomEntityType {
		CCHICKEN("CChicken", 101, EntityType.CHICKEN, net.minecraft.server.v1_8_R1.EntityChicken.class, CustomChicken.class);
		
		
	    private int id;
	    private EntityType entityType;
	    private Class<? extends EntityInsentient> nmsClass;
	    private Class<? extends EntityInsentient> customClass;
	    private String name;
	 
	    private CustomEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
	        this.name = name;
	        this.id = id;
	        this.entityType = entityType;
	        this.nmsClass = nmsClass;
	        this.customClass = customClass;
	    }
	 
	    public String getName(){
	        return this.name;
	    }
	 
	    public int getID(){
	        return this.id;
	    }
	 
	    public EntityType getEntityType(){
	        return this.entityType;
	    }
	 
	    public Class<? extends EntityInsentient> getNMSClass(){
	        return this.nmsClass;
	    }
	 
	    public Class<? extends EntityInsentient> getCustomClass(){
	        return this.customClass;
	    }
	 
	
	
	private EntityInsentient createEntity(World world){
		try{
			return this.getCustomClass().getConstructor(World.class).newInstance(world);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Entity spawnEntity(Location location){
		World world = ((CraftWorld) location.getWorld()).getHandle();
		EntityInsentient entity = this.createEntity(world);
		entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		entity.prepare(null, (GroupDataEntity) null);
		world.addEntity(entity, SpawnReason.CUSTOM);
		entity.p(entity);
		return (Entity)entity;
	}
}
