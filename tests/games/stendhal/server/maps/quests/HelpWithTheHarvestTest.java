
package games.stendhal.server.maps.quests;

//import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
//import static utilities.SpeakerNPCTestHelper.getReply;

//import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.mapstuff.block.Block;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.ados.forest.SickleingHalfelfNPC;
import marauroa.common.game.RPObject;
//import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class HelpWithTheHarvestTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_ADOS = "0_ados_forest_w2";

	private static final String NPC_Eheneumniranin = "Eheneumniranin";

	
	private SpeakerNPC npc;
	private Engine en;

	private String questSlot;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_ADOS);
	}

	public HelpWithTheHarvestTest() {
		super(ZONE_ADOS, NPC_Eheneumniranin);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final StendhalRPZone testzone = new StendhalRPZone(ZONE_ADOS);
		new SickleingHalfelfNPC().configureZone(testzone, null);

		quest = new HelpWithTheHarvest();
		quest.addToWorld();

		questSlot = quest.getSlotName();
	}

	@Test
	public void testCartPlacing() {
		StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(ZONE_ADOS);
		Entity entityOne = zone.getEntityAt(79, 106);
		Entity entityTwo = zone.getEntityAt(87, 100);
		String cartDescription = "You see a straw cart. Can you manage to push it to Karl's barn?";
		boolean entitiesAreCarts;
		
		if(entityOne != null && entityTwo != null && entityOne.getDescription().equals(cartDescription) && entityTwo.getDescription().equals(cartDescription))
		{
			entitiesAreCarts = true;
		}
		else
		{
			entitiesAreCarts = false;
		}
		
		
		
		assertTrue(entitiesAreCarts);
	}

	@Test
	public void testCartBottom() {
		StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(ZONE_ADOS);
		Block entityOne = (Block) zone.getEntityAt(79, 106);
		
		String cartDescription = "You see a straw cart. Can you manage to push it to Karl's barn?";
		boolean entityIsCart;
		
		if(entityOne != null  && entityOne.getDescription().equals(cartDescription))
		{
			entityIsCart = true;
		}
		else
		{
			entityIsCart = false;
		}
		
		
		
		assertTrue(entityIsCart);
		
		
		entityOne.setPosition(75, 127);
		player.setDirection(Direction.UP);
		RPObject object = (RPObject) player;
		entityOne.onEntered(object, zone);
		int cartX = entityOne.getX();
		int cartY = entityOne.getY();
		
		int playerX = player.getX();
		int playerY = player.getY();
		
		assertEquals(75, playerX);
		assertEquals(75, cartX);
		assertEquals(127, playerY);
		assertEquals(126, cartY);
		
		
	}
	
	@Test
	public void testCartTop() {
		StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(ZONE_ADOS);
		Block entityOne = (Block) zone.getEntityAt(79, 106);
		
		String cartDescription = "You see a straw cart. Can you manage to push it to Karl's barn?";
		boolean entityIsCart;
		
		if(entityOne != null  && entityOne.getDescription().equals(cartDescription))
		{
			entityIsCart = true;
		}
		else
		{
			entityIsCart = false;
		}
	
		
		
		assertTrue(entityIsCart);
		
		
		entityOne.setPosition(74, 0);
		player.setDirection(Direction.DOWN);
		RPObject object = (RPObject) player;
		entityOne.onEntered(object, zone);
		int cartX = entityOne.getX();
		int cartY = entityOne.getY();
		
		int playerX = player.getX();
		int playerY = player.getY();
		
		assertEquals(74, playerX);
		assertEquals(74, cartX);
		assertEquals(0, playerY);
		assertEquals(1, cartY);
			
		
	}

	
	@Test
	public void testCartLeft() {
		StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(ZONE_ADOS);
		Block entityOne = (Block) zone.getEntityAt(79, 106);
		
		String cartDescription = "You see a straw cart. Can you manage to push it to Karl's barn?";
		boolean entityIsCart;
		
		if(entityOne != null  && entityOne.getDescription().equals(cartDescription))
		{
			entityIsCart = true;
		}
		else
		{
			entityIsCart = false;
		}
	
		
		
		assertTrue(entityIsCart);
		
		
		entityOne.setPosition(0, 30);
		player.setDirection(Direction.RIGHT);
		RPObject object = (RPObject) player;
		entityOne.onEntered(object, zone);
		int cartX = entityOne.getX();
		int cartY = entityOne.getY();
		
		int playerX = player.getX();
		int playerY = player.getY();
		
		assertEquals(0, playerX);
		assertEquals(1, cartX);
		assertEquals(30, playerY);
		assertEquals(30, cartY);
			
		
	}
	
	@Test
	public void testCartRight() {
		StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(ZONE_ADOS);
		Block entityOne = (Block) zone.getEntityAt(79, 106);
		
		String cartDescription = "You see a straw cart. Can you manage to push it to Karl's barn?";
		boolean entityIsCart;
		
		if(entityOne != null  && entityOne.getDescription().equals(cartDescription))
		{
			entityIsCart = true;
		}
		else
		{
			entityIsCart = false;
		}
	
		
		
		assertTrue(entityIsCart);
		
		//127,3
		entityOne.setPosition(127, 3);
		player.setDirection(Direction.LEFT);
		RPObject object = (RPObject) player;
		entityOne.onEntered(object, zone);
		int cartX = entityOne.getX();
		int cartY = entityOne.getY();
		
		int playerX = player.getX();
		int playerY = player.getY();
		
		assertEquals(127, playerX);
		assertEquals(126, cartX);
		assertEquals(3, playerY);
		assertEquals(3, cartY);
			
		
	}
	
}
