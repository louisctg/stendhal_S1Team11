package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.config.ZonesXMLLoader;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.scroll.MarkedScroll;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class WofolTeleportScrollTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ItemTestHelper.generateRPClasses();
		MockStendlRPWorld.get();
		new DatabaseFactory().initializeDatabase();
		final ZonesXMLLoader loader = new ZonesXMLLoader(new URI("/data/conf/zones/semos.xml"));
		loader.load();
	}
	
	//testing if it correctly teleports
	@Test
	public void testOnUsedWofolTeleportScroll() throws Exception {
		MarkedScroll wofolScroll = (MarkedScroll)SingletonRepository.getEntityManager().getItem("wofol city scroll");
		assertNotNull(wofolScroll);
		
		final Player player = PlayerTestHelper.createPlayer("zee");
		assertNotNull(player);
		
		StendhalRPZone home = SingletonRepository.getRPWorld().getZone("0_semos_city");
		StendhalRPZone wofol = SingletonRepository.getRPWorld().getZone("-1_semos_mine_nw");
		home.add(player);
		home.add(wofolScroll);
		
		//Check the player is in 0_semos_city to start
		assertEquals(player.getZone(), home);
		
		//Check the player can't teleport to -1_semos_mine_nw becauase they haven't been there yet
		assertFalse(player.hasVisitedZone(wofol));
		wofolScroll.onUsed(player);
		assertEquals(player.getZone(), home);
		
		//Move player to -1_semos_mine_nw and back to 0_semos_city
		//This which change the player stats to indicate they have been there
		player.teleport(wofol, 0, 0, null, null);
		player.teleport(home, 0, 0, null, null);
		
		//Check that the player stats have changed
		assertTrue(player.hasVisitedZone(wofol));
		
		//Check the player can teleport now and end up in wofol city
		wofolScroll.onUsed(player);
		assertEquals(player.getZone(), wofol);
	}
} 
