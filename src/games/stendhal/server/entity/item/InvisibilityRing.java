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
				player.sendPrivateText("You are invisible now, the players will see a shimmer in the air, and the creatures won't attack you!");
			}
			else
			{
				player.setInvisible(false);
				player.setVisibility(100);
				player.sendPrivateText("You are no longer invisible!");
	
			}		
		}
		
		return super.onEquipped(equipper, slot);
<<<<<<< Upstream, based on branch 'COMP23311/EX2/invisibility-ring' of https://gitlab.cs.man.ac.uk/comp23311_2018/stendhal_S1Team11.git

=======
>>>>>>> 2214dba Merged and solved merge conflicts
	}
	
	@Override
	public boolean onUnequipped() {
<<<<<<< Upstream, based on branch 'COMP23311/EX2/invisibility-ring' of https://gitlab.cs.man.ac.uk/comp23311_2018/stendhal_S1Team11.git

=======
		//set player visible if the item is unequipped
>>>>>>> 2214dba Merged and solved merge conflicts
		if(this.getContainerOwner() instanceof Player)
		{
<<<<<<< Upstream, based on branch 'COMP23311/EX2/invisibility-ring' of https://gitlab.cs.man.ac.uk/comp23311_2018/stendhal_S1Team11.git
		//set player visible if the item is unequipped
=======
>>>>>>> 2214dba Merged and solved merge conflicts
		Player player = (Player)getContainerOwner();
		if (player != null)
		{
			player.setInvisible(false);
			player.setVisibility(100);
			player.sendPrivateText("You are now visible");
		}	
		}
<<<<<<< Upstream, based on branch 'COMP23311/EX2/invisibility-ring' of https://gitlab.cs.man.ac.uk/comp23311_2018/stendhal_S1Team11.git
		return super.onUnequipped();

=======
		}		
		return super.onUnequipped();
		
>>>>>>> 2214dba Merged and solved merge conflicts
	}
	
	@Override
	public String describe() {					
		return "This ring will make you invisible";
	}	
		
}