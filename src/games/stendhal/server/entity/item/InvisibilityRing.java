package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

/**
 * A ring that protects from XP loss.
 */
public class InvisibilityRing extends Item {
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
		player.setInvisible(true);
		return true;
	}
	
	@Override
	public boolean onUnequipped() {
		return true;
	}
	
	@Override
	public String describe() {					
		return "This ring will make you invisible";
	}	
		
}