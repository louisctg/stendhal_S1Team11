package conf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.core.config.ZonesXMLLoader;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import marauroa.server.game.db.DatabaseFactory;

public class NalworCityEntrancePortalTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		StendhalRPWorld.get();
		new DatabaseFactory().initializeDatabase();
	}
	
	@Test
	public void test() throws Exception {
		final ZonesXMLLoader loader = new ZonesXMLLoader(new URI("/data/conf/zones/nalwor.xml"));
		loader.load();
		StendhalRPWorld world = SingletonRepository.getRPWorld();
		StendhalRPZone nalworForestW = (StendhalRPZone) world.getRPZone("0_nalwor_forest_w");
		List<Portal> portals = nalworForestW.getPortals();
		Portal outside_entrance = null;
		Portal outside_exit = null;
		Portal inside_entrance = null;
		Portal inside_exit = null;
		for (Portal p: portals)
		{
			switch ((String) p.getIdentifier())
			{
			  case "outside_entrance":
				outside_entrance = p;
				break;
			  case "outside_exit":
			  	outside_exit = p;
			  	break;
			  case "inside_entrance":
			    inside_entrance = p;
			    break;
			  case "inside_exit": 
			    inside_exit = p;
			    break;
			}
		}
		assertNotNull(outside_entrance);
		assertNotNull(outside_exit);
		assertNotNull(inside_entrance);
		assertNotNull(inside_exit);
		assertEquals(outside_entrance.getX(), outside_exit.getX());
		assertEquals(outside_entrance.getY(), outside_exit.getY() - 1);
		assertEquals(inside_entrance.getX(), inside_exit.getX());
		assertEquals(inside_entrance.getY(), inside_exit.getY() - 1);	
	}

}
