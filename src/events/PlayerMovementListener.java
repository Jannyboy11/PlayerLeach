package events;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import playerlead.PlayerLead;

public class PlayerMovementListener implements Listener {

	private PlayerLead plugin;

	public PlayerMovementListener(PlayerLead pl){
		plugin = pl;
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		Player mover = e.getPlayer();
		UUID moverUUID = mover.getUniqueId();
		for (Entry<UUID,UUID> entry : plugin.slaveMasters.entrySet()) {
			if (entry.getKey().equals(moverUUID) || entry.getValue().equals(moverUUID)) {
				Player slave = PlayerLead.server.getPlayer(entry.getKey());
				Player master = PlayerLead.server.getPlayer(entry.getValue());
				double[] info = calculateDistanceCoefficient(slave,master);
				if (info[0] > PlayerLead.maxDistance) {
					moveSlave(slave,master,info);
				}
			}
		}

	}
	/**
	 * Calculates the distance from the <code>master</code> and <code>slave</code> using Pythagoras. <br>
	 * calculates the coefficient of Z expressed in X with z/x <br>
	 * and calculates whether the slave delta coordinates is in negative x and z.
	 * @param slave
	 * @param master
	 * @return a double array of length 4, where [0] is the distance between two players <br>
	 * and [1] is the coefficient of Z/X -> Z expressed in X <br>
	 * [2] is -1 if slave is on a lower X then master, otherwise 1 <br>
	 * [3] is -1 if slave is on a lower Z then master, otherwise 1
	 */
	public double[] calculateDistanceCoefficient(Player slave, Player master) {
		double slaveX = slave.getLocation().getX();
		double slaveZ = slave.getLocation().getZ();
		double masterX = master.getLocation().getX();
		double masterZ = master.getLocation().getZ();
		double xDifference = Math.abs(Math.abs(masterX) - Math.abs(slaveX));
		double zDifference = Math.abs(Math.abs(masterZ) - Math.abs(slaveZ));
		double[] result = new double[4];
		result[0] = Math.sqrt(Math.pow(zDifference, 2) + Math.pow(xDifference, 2));
		result[1] = zDifference/xDifference;
		result[2] = slaveX < masterX ? -1 : 1;
		result[3] = slaveZ < masterZ ? -1 : 1;
		return result;
	}
	/**
	 * Moves a {@link Player Player slave} to a distance of {@link PlayerLead.maxDistance} from {@link Player Player master} <br>
	 * this function preserves the angle at which the slave/master were by using the coefficient and <br>
	 * whether the delta x/z were negative as giving in inf[2] and inf[3]
	 * @param slave the slave Player
	 * @param master the master Player
	 * @param inf the info as given by {@link #calculateDistanceCoefficient(Player, Player)}
	 */
	public void moveSlave(Player slave, Player master,double[] inf) {
		double coefficient = inf[1];
		double max = PlayerLead.maxDistance;
		// MAX^2 = X^2 + Z^2
		// Z = coefficient*x
		// MAX^2 = X^2 + (coefficient*X)^2
		//so MAX = (1+coefficient^2) X
		double amountOfX = Math.sqrt(1+Math.pow(coefficient, 2));
		double newDeltaX = max/amountOfX;
		double newDeltaZ = newDeltaX*coefficient;
		double slaveX = master.getLocation().getX() + newDeltaX*inf[2];
		double slaveZ = master.getLocation().getZ() + newDeltaZ*inf[3];
		double slaveY = slave.getLocation().getY();
		
		
		slave.teleport(new Location(slave.getWorld(), slaveX, slaveY, slaveZ, slave.getLocation().getYaw(), slave.getLocation().getPitch()));
	}
}
