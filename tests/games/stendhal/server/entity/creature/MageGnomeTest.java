package games.stendhal.server.entity.creature;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.rule.defaultruleset.DefaultCreature;
import games.stendhal.server.entity.creature.impl.DropItem;

/**
 * Tests for mage gnome
 * 
 * @author Zygimantas Koncius
 *
 */
public class MageGnomeTest {

	@Test
	public void testPotionDropRate() {
		DefaultCreature mageGnome = SingletonRepository.getEntityManager().getDefaultCreature("mage gnome");
		List<DropItem> dropItems = mageGnome.getDropItems();
		DropItem potion = null;
		for(DropItem dropItem: dropItems) {
			if(dropItem.name.equals("potion")) {
				potion = dropItem;
				break;
			}
		}
		assertNotNull(potion);
		assertTrue(potion.probability == 40.0 && potion.min == 2 && potion.max == 4);
	}

}
