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
		super("invisibility ring", "ring", "invisibility-ring", null);
		
	}
	@Override
	public boolean onEquipped(RPEntity equipper, String slot) {

		// this.prevEntity = equipper;
		// this.prevSlot   = slot;
		
		//check where the item is being equipped, make player invisible if on the finger, visible othervise
		if(equipper instanceof Player)

		{
			//cast an entity into a player object
			Player player = (Player) equipper;
			//check where the item is being equipped, make player invisible if on the finger, visible othervise
			if(slot =="finger")
			{
				
				player.setInvisible(true);
				player.setVisibility(10);
			
			}
			else
			{
				player.setInvisible(false);
				player.setVisibility(100);
			}	
		}
		
		return super.onEquipped(equipper, slot);

	}
	
	@Override
	public boolean onUnequipped() {

		
		if(this.getContainerOwner() instanceof Player)
		{
			//set player visible if the item is unequipped
			Player player = (Player)getContainerOwner();
			if (player != null)
			{
				player.setInvisible(false);
				player.setVisibility(100);
			}	
		}
	
		return super.onUnequipped();


	}
	
	@Override
	public String describe() {					
		return "This ring will make you invisible";
	}	
		
}