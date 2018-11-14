package games.stendhal.server.entity.item;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.constants.Nature;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.creature.Creature;
//import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.nalwor.hell.CaptiveNPC;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class InvisibilityRingTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
		
		final StendhalRPZone zone = new StendhalRPZone("admin_test");

		new CaptiveNPC().configureZone(zone, null);


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
		assertFalse(ring.onEquipped(romeo, "finger"));
		assertTrue(romeo.isInvisibleToCreatures());
		romeo.equip("bag", ring);
		assertFalse(ring.onEquipped(romeo, "bag"));
		assertFalse(romeo.isInvisibleToCreatures());
		romeo.equipToInventoryOnly(ring);
		romeo.drop(ring);
		assertFalse(romeo.isInvisibleToCreatures());		
	}
	
	@Test
	public void testhasValidTargetInvisibleWearingRing() {
		final InvisibilityRing ring = new InvisibilityRing();
		final Player player = PlayerTestHelper.createPlayer("bob");
		player.setPosition(6, 0);
		final MockCreature enemyCreature = new MockCreature();
		final StendhalRPZone zone = new StendhalRPZone("test", 20 , 20);
		zone.add(enemyCreature);
		zone.add(player);
		enemies.add(player);
		//check that the creature can see the player on the correct distance
		assertSame(player, enemyCreature.getNearestEnemy(5));
		assertNull(enemyCreature.getNearestEnemy(4));
		//equip invisibility ring
		//player.equipToInventoryOnly(ring);
		player.equip("finger", ring);
		assertTrue(player.isEquippedItemInSlot("finger", "invisibility ring"));
		ring.onEquipped(player, "finger");
		assertTrue(player.isInvisibleToCreatures());
		//check that the creature can no longer see the player
		assertNull(enemyCreature.getNearestEnemy(5));
		//set creature to be magical with different damage types, check that 
		//if creature is magical it can see the player
		enemyCreature.setDamageTypes(Nature.parse("FIRE"), null);
		assertSame(player, enemyCreature.getNearestEnemy(5));		
		enemyCreature.setDamageTypes(Nature.parse("DARK"), null);
		assertSame(player, enemyCreature.getNearestEnemy(5));
		enemyCreature.setDamageTypes(Nature.parse("ICE"), null);
		assertSame(player, enemyCreature.getNearestEnemy(5));
		enemyCreature.setDamageTypes(Nature.parse("LIGHT"), null);
		assertSame(player, enemyCreature.getNearestEnemy(5));
		enemyCreature.setDamageTypes(Nature.parse("CUT"), Nature.parse("LIGHT"));
		assertSame(player, enemyCreature.getNearestEnemy(5));
		
	}
	@Test
	public void testNPCResponseWithoutInvisibilityRing()
	{
		//Testing for NPC response where invisibility ring is not equipped
		
		//get NPC tomi
		SpeakerNPC npc = SingletonRepository.getNPCList().get("tomi");
		
		Engine en = npc.getEngine();
		final Player player = PlayerTestHelper.createPlayer("bob");
		
		//use engine to get the reply
		en.step(player, "hi");
		assertEquals("help!", getReply(npc));
	

	}
	
	@Test
	public void testNPCResponseWithInvisibilityRing()
	{
		//Testing for NPC response where invisibility ring is equipped
		final InvisibilityRing ring = new InvisibilityRing();
		
		//create NPC 'phil'
		SpeakerNPC npc = new SpeakerNPC("phil");
				
		final Player player = PlayerTestHelper.createPlayer("bob");
		
		//equip invisibility ring for the player
		player.equip("finger", ring);
		ring.onEquipped(player, "finger");
		
		//tell hi to the npc
		npc.listenTo(player, "hi");
		
		//check npc response
		assertEquals("Who is there? Who is speaking?", getReply(npc));
	
		
	}
	
		
	private static List<RPEntity> enemies  = new LinkedList<RPEntity>();
	private static class MockCreature extends Creature {

		@Override
		public List<RPEntity> getEnemyList() {
			return enemies;
		}
	}
	
	
}

