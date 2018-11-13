package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

/**
 * A ring that makes you invisible.
 */
public class InvisibilityRing extends SlotActivatedItem {
	public InvisibilityRing(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}
	/**
	 * Copy constructor.
	 *
	 * @param item copied item
	 */
	public InvisibilityRing(final InvisibilityRing item) {
		super(item);
	}
	/**
	 * Create an InvisibilityRing.
	 */
	public InvisibilityRing() {
		super("invisibilty ring", "ring", "invisibilty-ring", null);
		
	}
	@Override
	public boolean onEquipped(RPEntity equipper, String slot) {

		// this.prevEntity = equipper;
		// this.prevSlot   = slot;
		Player player = (Player) equipper;
		if(slot =="finger")
		{
			
			player.setInvisible(true);
			player.setVisibility(10);
			player.sendPrivateText("You are invisible now, the players will see a shimmer in the air, and the creatures won't attack you!");
		}
		else
		{
			player.setInvisible(false);
			player.setVisibility(100);
			player.sendPrivateText("You are no longer invisible!");

		}		
		return true;
	}
	
	@Override
	public boolean onUnequipped() {
		Player player = (Player) this.getContainerOwner();
		player.setInvisible(false);
		player.sendPrivateText("You are now visible");
		return true;
	}
	
	@Override
	public String describe() {					
		return "This ring will make you invisible";
	}	
		
}