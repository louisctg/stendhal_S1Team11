package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.scroll.MarkedScroll;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class WofolTeleportScrollTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ItemTestHelper.generateRPClasses();
		MockStendlRPWorld.get();
	}
	
	//testing if it correctly teleports
	@Test
	public void testOnUsedWofolTeleportScroll() {
		MarkedScroll wofolScroll = (MarkedScroll)SingletonRepository.getEntityManager().getItem("wofol city scroll");
		assertNotNull(wofolScroll);
		final Player player = PlayerTestHelper.createPlayer("zee");
		assertNotNull(player);
		
		wofolScroll.onUsed(player);
		assertEquals(player.getZone(), "-1_semos_mine_nw");
	}
} 
