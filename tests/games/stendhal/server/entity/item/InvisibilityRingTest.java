package games.stendhal.server.entity.item;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class InvisibilityRingTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();

	}
	/**
	 * Tests for describe.
	 */
	@Test
	public void testDescribe() {
		final InvisibilityRing ring = new InvisibilityRing();		
		assertThat(ring.describe(), is("This ring will make you invisible"));

	}

	/**
	 * Tests for onEquipped. 
	 */
	@Test
	public void testOnEquipped() {
		final InvisibilityRing ring = new InvisibilityRing();
		final Player romeo = PlayerTestHelper.createPlayer("romeo");
		romeo.equipToInventoryOnly(ring);
		assertTrue(ring.onEquipped(romeo, "finger"));
		assertTrue(romeo.isInvisibleToCreatures());
		romeo.equip("bag", ring);
		assertTrue(ring.onEquipped(romeo, "bag"));
		assertFalse(romeo.isInvisibleToCreatures());
		romeo.equipToInventoryOnly(ring);
		romeo.drop(ring);
		assertTrue(ring.onEquipped(romeo, "bag"));
		assertFalse(romeo.isInvisibleToCreatures());		
	}
	
}

