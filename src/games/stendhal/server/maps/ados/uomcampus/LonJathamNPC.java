package games.stendhal.server.maps.ados.uomcampus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;

public class LonJathamNPC implements ZoneConfigurator {

		/**
		 * Configure a zone.
		 *
		 * @param	zone		The zone to be configured.
		 * @param	attributes	Configuration attributes.
		 */
		@Override
		public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
			buildschool(zone);

		}
		
		private void buildCampus(final StendhalRPZone zone) {
			final SpeakerNPC LonJatham = new SpeakerNPC("Lon Jatham"); 
		}
}