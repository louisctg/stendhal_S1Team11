package games.stendhal.server.entity.item;

import java.util.Map;

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
}