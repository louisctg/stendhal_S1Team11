package games.stendhal.server.maps.Wofol.house4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.wofol.house4.TraderNPC;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class TraderNPCWofolTest extends ZonePlayerAndNPCTestImpl{
	private static final String ZONE_NAME = "int_wofol_house4";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}

	public TraderNPCWofolTest() {
		setNpcNames("Wrvil");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new TraderNPC(), ZONE_NAME);
	}

	/**
	 * Tests for buy wofol city sroll.
	 */
	@Test
	public void testBuyWofolScroll() {
		final SpeakerNPC npc = getNPC("Wrvil");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi"));
		assertEquals("Welcome to the Kobold City of Wofol. I hope you come in peace.", getReply(npc));

		assertTrue(en.step(player, "job"));
		assertEquals("I run a buying and selling #trade with kobolds - or whoever else passes by. I am one of the few Kobolds who can speak with non-Kobolds.", getReply(npc));

		assertTrue(en.step(player, "offer"));
		assertEquals("Please look at the each blackboard on the wall to see what I buy and sell at the moment.", getReply(npc));

		assertTrue(en.step(player, "buy someunknownthing"));
		assertEquals("Sorry, I don't sell someunknownthings.", getReply(npc));
		
		assertTrue(en.step(player, "buy wofol city scroll"));
		assertEquals("A wofol city scroll will cost 400. Do you want to buy it?", getReply(npc));
		assertTrue(en.step(player, "no"));
		assertEquals("Ok, how else may I help you?", getReply(npc));
		
		// add money to be able to buy a scroll
		assertTrue(equipWithMoney(player, 400));
		
		assertTrue(en.step(player, "buy wofol city scroll"));
		assertEquals("A wofol city scroll will cost 400. Do you want to buy it?", getReply(npc));
		assertFalse(player.isEquipped("wofol city scroll"));
		assertTrue(en.step(player, "yes"));
		assertEquals("Congratulations! Here is your wofol city scroll!", getReply(npc));
		assertTrue(player.isEquipped("wofol city scroll"));
	}
}
